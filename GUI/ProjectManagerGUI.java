package GUI;

import java.awt.*;
import javax.swing.*;

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
        taskDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { handleTaskAction(); }
        });
        projectDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { handleProjectAction(); }
        });
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { if (backListener != null) backListener.onBack(); }
        });
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

    // --- Task/Project Action Dialogs ---
    private void showUpdateTaskStatusDialog() {
        if (currentManager == null || taskDb == null) return;
        String taskId = JOptionPane.showInputDialog(this, "Enter Task ID to update:");
        if (taskId == null || taskId.isEmpty()) return;
        Models.Task t = taskDb.getById(taskId);
        if (t == null) {
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

    private void showAddNewTaskDialog() {
        if (currentManager == null || projectDb == null || taskDb == null) return;
        String projectId = JOptionPane.showInputDialog(this, "Enter Project ID to add task to:");
        if (projectId == null || projectId.isEmpty()) return;
        Models.Project p = projectDb.getById(projectId);
        if (p == null || !currentManager.getManagedProjectIds().contains(projectId)) {
            JOptionPane.showMessageDialog(this, "Invalid Project ID.");
            return;
        }
        String taskId = JOptionPane.showInputDialog(this, "Enter new Task ID:");
        if (taskId == null || taskId.isEmpty() || taskDb.getById(taskId) != null) {
            JOptionPane.showMessageDialog(this, "Invalid or duplicate Task ID.");
            return;
        }
        String desc = JOptionPane.showInputDialog(this, "Enter description link (optional):");
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        String status = (String) JOptionPane.showInputDialog(this, "Select status:", "Task Status", JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);
        if (status == null) return;
        Models.Task newTask = new Models.Task(taskId, desc, "", status, null, projectId);
        p.addTask(newTask, taskDb);
        projectDb.update(p);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task added to project.");
    }

    private void showAssignTaskDialog() {
        if (currentManager == null || employeeDb == null || taskDb == null) return;
        String taskId = JOptionPane.showInputDialog(this, "Enter Task ID to assign:");
        if (taskId == null || taskId.isEmpty()) return;
        Models.Task t = taskDb.getById(taskId);
        if (t == null) {
            JOptionPane.showMessageDialog(this, "Invalid Task ID.");
            return;
        }
        String empId = JOptionPane.showInputDialog(this, "Enter Employee ID to assign to:");
        if (empId == null || empId.isEmpty()) return;
        Models.Employee e = employeeDb.getById(empId);
        if (e == null || !currentManager.getManagedEmployeeIds().contains(empId)) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID.");
            return;
        }
        t.setAssignedUserID(empId);
        if (!e.getAssignedTaskIds().contains(taskId)) e.getAssignedTaskIds().add(taskId);
        taskDb.update(t);
        employeeDb.update(e);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task assigned to employee.");
    }

    private void showAddNewProjectDialog() {
        if (currentManager == null || projectDb == null) return;
        String projectId = JOptionPane.showInputDialog(this, "Enter new Project ID:");
        if (projectId == null || projectId.isEmpty() || projectDb.getById(projectId) != null) {
            JOptionPane.showMessageDialog(this, "Invalid or duplicate Project ID.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter project name:");
        if (name == null || name.trim().isEmpty()) return;
        String desc = JOptionPane.showInputDialog(this, "Enter description link (optional):");
        String startDate = JOptionPane.showInputDialog(this, "Enter start date (yyyy-MM-dd):");
        String endDate = JOptionPane.showInputDialog(this, "Enter end date (yyyy-MM-dd):");
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        String status = (String) JOptionPane.showInputDialog(this, "Select status:", "Project Status", JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);
        if (status == null) return;
        Models.Project newProj = new Models.Project(projectId, name, desc, startDate, endDate, status, currentManager.getID());
        currentManager.addManagedProjectId(projectId);
        projectDb.add(newProj);
        managerDb.update(currentManager);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project added.");
    }

    private void showUpdateProjectStatusDialog() {
        if (currentManager == null || projectDb == null) return;
        String projectId = JOptionPane.showInputDialog(this, "Enter Project ID to update:");
        if (projectId == null || projectId.isEmpty()) return;
        Models.Project p = projectDb.getById(projectId);
        if (p == null || !currentManager.getManagedProjectIds().contains(projectId)) {
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

    private void showRemoveProjectDialog() {
        if (currentManager == null || projectDb == null) return;
        String projectId = JOptionPane.showInputDialog(this, "Enter Project ID to remove:");
        if (projectId == null || projectId.isEmpty()) return;
        if (!currentManager.getManagedProjectIds().contains(projectId)) {
            JOptionPane.showMessageDialog(this, "You do not manage this project.");
            return;
        }
        currentManager.removeManagedProjectId(projectId);
        projectDb.delete(projectId);
        managerDb.update(currentManager);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project removed.");
    }

    // --- Enhanced event handlers ---
    private void handleTaskAction() {
        String selectedTask = (String) taskDropdown.getSelectedItem();
        if (selectedTask != null) {
            switch (selectedTask) {
                case "Update Task Status":
                    showUpdateTaskStatusDialog();
                    break;
                case "Add New Task":
                    showAddNewTaskDialog();
                    break;
                case "Assign Task":
                    showAssignTaskDialog();
                    break;
            }
        }
    }

    private void handleProjectAction() {
        String selectedProject = (String) projectDropdown.getSelectedItem();
        if (selectedProject != null) {
            switch (selectedProject) {
                case "Add New Project":
                    showAddNewProjectDialog();
                    break;
                case "Update Project Status":
                    showUpdateProjectStatusDialog();
                    break;
                case "Remove Project":
                    showRemoveProjectDialog();
                    break;
            }
        }
    }
}