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
        // Find all projects where the employee has assigned tasks
        java.util.List<String> assignedTaskIds = currentEmployee.getAssignedTaskIds();
        java.util.Set<String> projectIdsWithTasks = new java.util.HashSet<>();
        for (String taskId : assignedTaskIds) {
            Models.Task t = taskDb.getById(taskId);
            if (t != null && t.getProjectID() != null && !t.getProjectID().isEmpty()) {
                projectIdsWithTasks.add(t.getProjectID());
            }
        }
        projectDropdownModel.removeAllElements();
        if (projectIdsWithTasks.isEmpty()) {
            projectDropdownModel.addElement("No projects");
            selectedProjectId = null;
            taskArea.setText("No tasks");
            return;
        }
        projectDropdownModel.addElement("All Projects");
        for (String projectId : projectIdsWithTasks) {
            Models.Project p = projectDb.getById(projectId);
            if (p != null) {
                projectDropdownModel.addElement(projectId + " - " + p.getName());
            }
        }
        // Select the first project by default if none selected
        if (selectedProjectId == null && projectDropdownModel.getSize() > 0) {
            String first = (String) projectDropdownModel.getElementAt(0);
            if (!first.equals("No projects"))
                selectedProjectId = first.equals("All Projects") ? "ALL" : first.split(" - ")[0];
        }
        if (selectedProjectId != null) {
            if (selectedProjectId.equals("ALL")) {
                projectDropdown.setSelectedItem("All Projects");
            } else {
                Models.Project p = projectDb.getById(selectedProjectId);
                if (p != null)
                    projectDropdown.setSelectedItem(selectedProjectId + " - " + p.getName());
            }
        }
        updateTaskAreaForSelectedProject();
    }

    private void updateTaskAreaForSelectedProject() {
        if (selectedProjectId == null || selectedProjectId.isEmpty() || (selectedProjectId.equals("ALL") && currentEmployee == null)) {
            taskArea.setText("No tasks");
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean hasEmployeeTasks = false;
        if (selectedProjectId.equals("ALL")) {
            // Show all tasks assigned to the employee from all projects
            for (String taskId : currentEmployee.getAssignedTaskIds()) {
                Models.Task t = taskDb.getById(taskId);
                if (t != null) {
                    hasEmployeeTasks = true;
                    Models.Project p = projectDb.getById(t.getProjectID());
                    String projectName = (p != null) ? p.getName() : "[Unknown Project]";
                    sb.append("- Project: ").append(projectName)
                      .append(" | Task ID: ").append(t.getID())
                      .append(", Status: ").append(t.getStatus())
                      .append(", Due: ").append(t.getDueDate())
                      .append(t.isOverdue() ? " (OVERDUE)" : "")
                      .append("\n");
                }
            }
        } else {
            Models.Project selectedProject = projectDb.getById(selectedProjectId);
            if (selectedProject == null) {
                taskArea.setText("No tasks");
                return;
            }
            java.util.List<String> taskIds = selectedProject.getTaskIDs();
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
        }
        if (!hasEmployeeTasks) sb.append("No tasks");
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
