package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ProjectManagerGUI extends JPanel {
    private JTextArea projectArea, employeeArea;
    private JLabel project, employee;
    private JComboBox<String> taskDropdown, projectDropdown;
    private JButton backBtn;
    private BackListener backListener;

    // --- Backend references and current manager ---
    private Models.Manager currentManager;
    private Database.ManagerDatabase managerDb;
    private Database.EmployeeDatabase employeeDb;
    private Database.ProjectDatabase projectDb;
    private Database.TaskDatabase taskDb;

    public interface BackListener {
        void onBack();
    }

    public void setBackListener(BackListener listener) {
        this.backListener = listener;
    }

    public ProjectManagerGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Current Projects Label
        project = new JLabel("Current Projects:");
        project.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(project, gbc);

        // Projects Text Area inside ScrollPane
        projectArea = new JTextArea(5, 40);
        projectArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane projectScrollPane = new JScrollPane(projectArea);
        gbc.gridy = 1;
        add(projectScrollPane, gbc);

        // Task Dropdown
        String[] taskOptions = {"Update Task Status", "Add New Task", "Assign Task"};
        taskDropdown = new JComboBox<>(taskOptions);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(taskDropdown, gbc);

        // Current Employees Label
        employee = new JLabel("Current Employees:");
        employee.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(employee, gbc);

        // Employees Text Area inside ScrollPane
        employeeArea = new JTextArea(5, 40);
        employeeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane employeeScrollPane = new JScrollPane(employeeArea);
        gbc.gridy = 4;
        add(employeeScrollPane, gbc);

        // Project Dropdown
        String[] projectOptions = {"Add New Project", "Update Project Status", "Remove Project"};
        projectDropdown = new JComboBox<>(projectOptions);
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(projectDropdown, gbc);

        // Back Button
        backBtn = new JButton("Back");
        gbc.gridy = 6;
        gbc.gridx = 0;
        add(backBtn, gbc);

        // Add event handling
        taskDropdown.addActionListener(e -> handleTaskAction());
        projectDropdown.addActionListener(e -> handleProjectAction());
        backBtn.addActionListener(e -> { if (backListener != null) backListener.onBack(); });
    }

    public void setManagerAndDatabases(Models.Manager mgr, Database.ManagerDatabase mgrDb, Database.EmployeeDatabase empDb, Database.ProjectDatabase projDb, Database.TaskDatabase tDb) {
        this.currentManager = mgr;
        this.managerDb = mgrDb;
        this.employeeDb = empDb;
        this.projectDb = projDb;
        this.taskDb = tDb;
        refreshDisplay();
    }

    // --- Display managed projects and employees ---
    private void refreshDisplay() {
        if (currentManager == null || projectDb == null || employeeDb == null || taskDb == null) {
            projectArea.setText("No manager loaded.");
            employeeArea.setText("");
            return;
        }
        StringBuilder projSb = new StringBuilder();
        for (String projId : currentManager.getManagedProjectIds()) {
            Models.Project p = projectDb.getById(projId);
            if (p != null) {
                projSb.append("- Project: ").append(p.getName())
                      .append(" (Status: ").append(p.getStatus()).append(")\n");
                for (String taskId : p.getTaskIDs()) {
                    Models.Task t = taskDb.getById(taskId);
                    if (t != null) {
                        projSb.append("    Task: ").append(t.getID())
                              .append(", Status: ").append(t.getStatus())
                              .append(", Assigned: ").append(t.getAssignedUserID()).append("\n");
                    }
                }
            }
        }
        projectArea.setText(projSb.toString());

        StringBuilder empSb = new StringBuilder();
        for (String empId : currentManager.getManagedEmployeeIds()) {
            Models.Employee e = employeeDb.getById(empId);
            if (e != null) {
                empSb.append("- Employee: ").append(e.getName())
                      .append(" (ID: ").append(e.getID()).append(")\n");
                for (String taskId : e.getAssignedTaskIds()) {
                    Models.Task t = taskDb.getById(taskId);
                    if (t != null) {
                        empSb.append("    Task: ").append(t.getID())
                              .append(", Status: ").append(t.getStatus()).append("\n");
                    }
                }
            }
        }
        employeeArea.setText(empSb.toString());
    }

    // --- Handle task and project actions ---
    private void handleTaskAction() {
        String selectedTask = (String) taskDropdown.getSelectedItem();
        if (selectedTask != null) {
            switch (selectedTask) {
                case "Update Task Status":
                    // Logic to update task status
                    JOptionPane.showMessageDialog(this, "Update Task Status selected.");
                    break;
                case "Add New Task":
                    // Logic to add new task
                    JOptionPane.showMessageDialog(this, "Add New Task selected.");
                    break;
                case "Assign Task":
                    // Logic to assign task
                    JOptionPane.showMessageDialog(this, "Assign Task selected.");
                    break;
            }
        }
    }

    private void handleProjectAction() {
        String selectedProject = (String) projectDropdown.getSelectedItem();
        if (selectedProject != null) {
            switch (selectedProject) {
                case "Add New Project":
                    // Logic to add new project
                    JOptionPane.showMessageDialog(this, "Add New Project selected.");
                    break;
                case "Update Project Status":
                    // Logic to update project status
                    JOptionPane.showMessageDialog(this, "Update Project Status selected.");
                    break;
                case "Remove Project":
                    // Logic to remove project
                    JOptionPane.showMessageDialog(this, "Remove Project selected.");
                    break;
            }
        }
    }
}