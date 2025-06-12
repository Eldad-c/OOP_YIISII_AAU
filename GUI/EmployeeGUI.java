package GUI;

import javax.swing.*;
import java.awt.*;

public class EmployeeGUI extends JPanel {
    private JTextArea taskArea, projectArea;
    private JButton updateTaskBtn, updateProjectBtn, backBtn;
    private BackListener backListener;

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

        // --- Assigned Tasks ---
        JLabel taskLabel = new JLabel("Assigned Tasks:");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(taskLabel, gbc);
        taskArea = new JTextArea(8, 40);
        taskArea.setEditable(false);
        gbc.gridy = 1;
        add(new JScrollPane(taskArea), gbc);

        // --- Assigned Projects ---
        JLabel projectLabel = new JLabel("Assigned Projects:");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        add(projectLabel, gbc);
        projectArea = new JTextArea(6, 40);
        projectArea.setEditable(false);
        gbc.gridy = 3;
        add(new JScrollPane(projectArea), gbc);

        // --- Buttons ---
        updateTaskBtn = new JButton("Update Task Status");
        updateProjectBtn = new JButton("Update Project Status");
        backBtn = new JButton("Back");
        JPanel btnPanel = new JPanel();
        btnPanel.add(updateTaskBtn); btnPanel.add(updateProjectBtn); btnPanel.add(backBtn);
        gbc.gridy = 4;
        add(btnPanel, gbc);

        // --- Event Listeners ---
        updateTaskBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { showUpdateTaskDialog(); }
        });
        updateProjectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { showUpdateProjectDialog(); }
        });
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { if (backListener != null) backListener.onBack(); }
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
            projectArea.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Assigned Tasks:\n");
        for (Models.Task t : currentEmployee.getAssignedTasks(taskDb)) {
            sb.append("- Task ID: ").append(t.getID())
              .append(", Status: ").append(t.getStatus())
              .append(", Due: ").append(t.getDueDate())
              .append(t.isOverdue() ? " (OVERDUE)" : "")
              .append("\n");
        }
        taskArea.setText(sb.toString());

        StringBuilder psb = new StringBuilder();
        psb.append("Assigned Projects:\n");
        for (String projectId : currentEmployee.getProjectIds()) {
            Models.Project p = projectDb.getById(projectId);
            if (p != null) {
                psb.append("- Project: ").append(p.getName())
                  .append(" (Status: ").append(p.getStatus()).append(")\n");
            }
        }
        projectArea.setText(psb.toString());
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

    // --- Update Project Status Action ---
    private void showUpdateProjectDialog() {
        if (currentEmployee == null || projectDb == null) return;
        String projectId = JOptionPane.showInputDialog(this, "Enter Project ID to update:");
        if (projectId == null || projectId.isEmpty()) return;
        Models.Project p = projectDb.getById(projectId);
        if (p == null || !currentEmployee.getProjectIds().contains(projectId)) {
            JOptionPane.showMessageDialog(this, "Invalid Project ID.");
            return;
        }
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:", "Update Project Status", JOptionPane.PLAIN_MESSAGE, null, statusOptions, p.getStatus());
        if (newStatus == null || newStatus.isEmpty()) return;
        p.changeStatus(newStatus);
        projectDb.update(p);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project status updated.");
    }
}
