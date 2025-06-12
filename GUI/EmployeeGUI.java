package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeGUI extends JPanel {
    private JTextArea taskArea;
    private JButton updateTaskBtn, backBtn;
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
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Assigned Tasks Label
        JLabel taskLabel = new JLabel("Assigned Tasks:");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        add(taskLabel, gbc);

        // Minimize space between label and textarea
        gbc.insets = new Insets(5, 15, 15, 15);

        // Task Area inside ScrollPane
        taskArea = new JTextArea(8, 50);
        taskArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        taskArea.setEditable(true);
        JScrollPane taskScrollPane = new JScrollPane(taskArea);
        gbc.gridy = 1;
        add(taskScrollPane, gbc);

        // Restore default spacing for buttons
        gbc.insets = new Insets(15, 15, 15, 15);

        // Update Task Button
        updateTaskBtn = new JButton("Update Task Status");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(updateTaskBtn, gbc);

        // Back Button
        backBtn = new JButton("Back");
        gbc.gridx = 1;
        add(backBtn, gbc);

        // Event Listener
        updateTaskBtn.addActionListener(e -> showUpdateTaskDialog());
        backBtn.addActionListener(e -> { if (backListener != null) backListener.onBack(); });
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
        sb.append("\nProjects:\n");
        for (String projectId : currentEmployee.getProjectIds()) {
            Models.Project p = projectDb.getById(projectId);
            if (p != null) {
                sb.append("- Project: ").append(p.getName())
                  .append(" (Status: ").append(p.getStatus()).append(")\n");
            }
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
        String newStatus = JOptionPane.showInputDialog(this, "Enter new status for Task " + taskId + ":");
        if (newStatus == null || newStatus.isEmpty()) return;
        t.changeStatus(newStatus);
        taskDb.update(t);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task status updated.");
    }
}
