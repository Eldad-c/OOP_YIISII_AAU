package GUI;

import javax.swing.*;
import java.awt.*;

public class EmployeeGUI extends JPanel {
    private JTextArea taskArea;
    private JButton updateTaskBtn, backBtn;
    private BackListener backListener;
    private JComboBox<String> projectDropdown;
    private DefaultComboBoxModel<String> projectDropdownModel;
    private String selectedProjectId = null;

    // --- Backend references and current user ---
    private Models.Employee currentEmployee;
    private Database.EmployeeDatabase employeeDb;
    private Database.TaskDatabase taskDb;
    private Database.ProjectDatabase projectDb;

    public interface BackListener {
        void onBack();
    }

    public void setBackListener(BackListener listener) {
        this.backListener = listener;
    }

    public EmployeeGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // --- Assigned Projects Dropdown ---
        JLabel projectLabel = new JLabel("Assigned Projects:");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(projectLabel, gbc);
        projectDropdownModel = new DefaultComboBoxModel<>();
        projectDropdown = new JComboBox<>(projectDropdownModel);
        gbc.gridy = 1;
        add(projectDropdown, gbc);

        // --- Assigned Tasks ---
        JLabel taskLabel = new JLabel("Tasks for Selected Project:");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        add(taskLabel, gbc);
        taskArea = new JTextArea(8, 40);
        taskArea.setEditable(false);
        gbc.gridy = 3;
        add(new JScrollPane(taskArea), gbc);

        // --- Buttons ---
        updateTaskBtn = new JButton("Update Task Status");
        backBtn = new JButton("Back");
        JPanel btnPanel = new JPanel();
        btnPanel.add(updateTaskBtn); btnPanel.add(backBtn);
        gbc.gridy = 4;
        add(btnPanel, gbc);

        // --- Event Listeners ---
        updateTaskBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { showUpdateTaskDialog(); }
        });
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { if (backListener != null) backListener.onBack(); }
        });
        projectDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String selected = (String) projectDropdown.getSelectedItem();
                if (selected != null && !selected.equals("No projects")) {
                    selectedProjectId = selected.split(" - ")[0];
                } else {
                    selectedProjectId = null;
                }
                updateTaskAreaForSelectedProject();
            }
        });
    }

    public void setEmployeeAndDatabases(Models.Employee emp, Database.EmployeeDatabase empDb, Database.TaskDatabase tDb, Database.ProjectDatabase pDb) {
        this.currentEmployee = emp;
        this.employeeDb = empDb;
        this.taskDb = tDb;
        this.projectDb = pDb;
        refreshDisplay();
    }

    // --- Display assigned tasks and projects ---
    private void refreshDisplay() {
        if (currentEmployee == null || taskDb == null || projectDb == null) {
            taskArea.setText("No employee loaded.");
            projectDropdownModel.removeAllElements();
            projectDropdownModel.addElement("No projects");
            return;
        }
        // Populate project dropdown with only the employee's projects
        projectDropdownModel.removeAllElements();
        java.util.List<String> projectIds = currentEmployee.getProjectIds();
        if (projectIds.isEmpty()) {
            projectDropdownModel.addElement("No projects");
            selectedProjectId = null;
            taskArea.setText("No tasks");
            return;
        }
        for (String projectId : projectIds) {
            Models.Project p = projectDb.getById(projectId);
            if (p != null) {
                projectDropdownModel.addElement(projectId + " - " + p.getName());
            }
        }
        // Select the first project by default if none selected
        if (selectedProjectId == null && projectDropdownModel.getSize() > 0) {
            String first = (String) projectDropdownModel.getElementAt(0);
            if (!first.equals("No projects"))
                selectedProjectId = first.split(" - ")[0];
        }
        if (selectedProjectId != null) {
            projectDropdown.setSelectedItem(selectedProjectId + " - " + projectDb.getById(selectedProjectId).getName());
        }
        updateTaskAreaForSelectedProject();
    }

    private void updateTaskAreaForSelectedProject() {
        if (selectedProjectId == null || selectedProjectId.isEmpty() || projectDb.getById(selectedProjectId) == null) {
            taskArea.setText("No tasks");
            return;
        }
        Models.Project selectedProject = projectDb.getById(selectedProjectId);
        java.util.List<String> taskIds = selectedProject.getTaskIDs();
        StringBuilder sb = new StringBuilder();
        if (taskIds.isEmpty()) {
            sb.append("No tasks");
        } else {
            boolean hasEmployeeTasks = false;
            for (String taskId : taskIds) {
                Models.Task t = taskDb.getById(taskId);
                if (t != null && t.getAssignedUserID() != null && t.getAssignedUserID().equals(currentEmployee.getID())) {
                    hasEmployeeTasks = true;
                    sb.append("- Task ID: ").append(t.getID())
                      .append(", Status: ").append(t.getStatus())
                      .append(", Due: ").append(t.getDueDate())
                      .append(t.isOverdue() ? " (OVERDUE)" : "")
                      .append("\n");
                }
            }
            if (!hasEmployeeTasks) sb.append("No tasks");
        }
        taskArea.setText(sb.toString());
    }

    // --- Update Task Status Action ---
    private void showUpdateTaskDialog() {
        if (currentEmployee == null || taskDb == null) return;
        String taskId = JOptionPane.showInputDialog(this, "Enter Task ID to update:");
        if (taskId == null || taskId.isEmpty()) return;
        Models.Task t = taskDb.getById(taskId);
        if (t == null || !currentEmployee.getAssignedTaskIds().contains(taskId)) {
            JOptionPane.showMessageDialog(this, "Invalid Task ID.");
            return;
        }
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:", "Update Task Status", JOptionPane.PLAIN_MESSAGE, null, statusOptions, t.getStatus());
        if (newStatus == null || newStatus.isEmpty()) return;
        t.changeStatus(newStatus);
        taskDb.update(t);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task status updated.");
    }
}
