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

    // --- Display all projects and employees ---
    private void refreshDisplay() {
        if (projectDb == null || employeeDb == null || taskDb == null) {
            projectArea.setText("No data loaded.");
            employeeArea.setText("");
            return;
        }
        // Show ALL projects in the organization
        java.util.List<Models.Project> allProjects = projectDb.getAll();
        StringBuilder projSb = new StringBuilder();
        for (Models.Project p : allProjects) {
            projSb.append("- Project: ").append(p.getName())
                  .append(" (ID: ").append(p.getID()).append(", Status: ").append(p.getStatus()).append(")\n");
            for (String taskId : p.getTaskIDs()) {
                Models.Task t = taskDb.getById(taskId);
                if (t != null) {
                    projSb.append("    Task: ").append(t.getID())
                          .append(", Status: ").append(t.getStatus())
                          .append(", Assigned: ").append(t.getAssignedUserID() == null ? "[Unassigned]" : t.getAssignedUserID()).append("\n");
                }
            }
        }
        if (allProjects.isEmpty()) projSb.append("No projects available.\n");
        projectArea.setText(projSb.toString());

        // Show all employees
        java.util.List<Models.Employee> allEmployees = employeeDb.getAll();
        StringBuilder empSb = new StringBuilder();
        for (Models.Employee e : allEmployees) {
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
        if (allEmployees.isEmpty()) empSb.append("No employees available.\n");
        employeeArea.setText(empSb.toString());
    }

    // --- Task/Project Action Dialogs ---
    private void showUpdateTaskStatusDialog() {
        if (taskDb == null) return;
        // Select task from all tasks
        java.util.List<Models.Task> allTasks = taskDb.getAll();
        if (allTasks.isEmpty()) { JOptionPane.showMessageDialog(this, "No tasks available."); return; }
        String[] taskOptions = allTasks.stream().map(t -> t.getID() + " - " + (t.getDescriptionLink() != null ? t.getDescriptionLink() : "No Description")).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(this, "Select Task:", "Update Task Status", JOptionPane.PLAIN_MESSAGE, null, taskOptions, taskOptions[0]);
        if (selected == null) return;
        String taskId = selected.split(" - ")[0];
        Models.Task t = taskDb.getById(taskId);
        if (t == null) { JOptionPane.showMessageDialog(this, "Invalid Task ID."); return; }
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:", "Update Task Status", JOptionPane.PLAIN_MESSAGE, null, statusOptions, t.getStatus());
        if (newStatus == null || newStatus.isEmpty()) return;
        t.changeStatus(newStatus);
        taskDb.update(t);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task status updated.");
    }

    private void showAddNewTaskDialog() {
        if (projectDb == null || taskDb == null) return;
        // Select project from all projects
        java.util.List<Models.Project> allProjects = projectDb.getAll();
        if (allProjects.isEmpty()) { JOptionPane.showMessageDialog(this, "No projects available."); return; }
        String[] projectOptions = allProjects.stream().map(p -> p.getID() + " - " + p.getName()).toArray(String[]::new);
        String selectedProj = (String) JOptionPane.showInputDialog(this, "Select Project:", "Add Task", JOptionPane.PLAIN_MESSAGE, null, projectOptions, projectOptions[0]);
        if (selectedProj == null) return;
        String projectId = selectedProj.split(" - ")[0];
        Models.Project p = projectDb.getById(projectId);
        if (p == null) { JOptionPane.showMessageDialog(this, "Invalid Project ID."); return; }
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
        if (employeeDb == null || taskDb == null) return;
        // Select task from all tasks
        java.util.List<Models.Task> allTasks = taskDb.getAll();
        if (allTasks.isEmpty()) { JOptionPane.showMessageDialog(this, "No tasks available."); return; }
        String[] taskOptions = allTasks.stream().map(t -> t.getID() + " - " + (t.getDescriptionLink() != null ? t.getDescriptionLink() : "No Description")).toArray(String[]::new);
        String selectedTask = (String) JOptionPane.showInputDialog(this, "Select Task:", "Assign Task", JOptionPane.PLAIN_MESSAGE, null, taskOptions, taskOptions[0]);
        if (selectedTask == null) return;
        String taskId = selectedTask.split(" - ")[0];
        Models.Task t = taskDb.getById(taskId);
        if (t == null) { JOptionPane.showMessageDialog(this, "Invalid Task ID."); return; }
        // Select employee from all employees
        java.util.List<Models.Employee> allEmployees = employeeDb.getAll();
        if (allEmployees.isEmpty()) { JOptionPane.showMessageDialog(this, "No employees available."); return; }
        String[] empOptions = allEmployees.stream().map(e -> e.getID() + " - " + e.getName()).toArray(String[]::new);
        String selectedEmp = (String) JOptionPane.showInputDialog(this, "Select Employee:", "Assign Task", JOptionPane.PLAIN_MESSAGE, null, empOptions, empOptions[0]);
        if (selectedEmp == null) return;
        String empId = selectedEmp.split(" - ")[0];
        Models.Employee e = employeeDb.getById(empId);
        if (e == null) { JOptionPane.showMessageDialog(this, "Invalid Employee ID."); return; }
        t.setAssignedUserID(empId);
        if (!e.getAssignedTaskIds().contains(taskId)) e.getAssignedTaskIds().add(taskId);
        taskDb.update(t);
        employeeDb.update(e);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task assigned to employee.");
    }

    private void showAddNewProjectDialog() {
        if (projectDb == null) return;
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
        // Assign manager as currentManager if available
        String managerId = (currentManager != null) ? currentManager.getID() : "";
        Models.Project newProj = new Models.Project(projectId, name, desc, startDate, endDate, status, managerId);
        if (currentManager != null) currentManager.addManagedProjectId(projectId);
        projectDb.add(newProj);
        if (managerDb != null && currentManager != null) managerDb.update(currentManager);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project added.");
    }

    private void showUpdateProjectStatusDialog() {
        if (projectDb == null) return;
        // Select project from all projects
        java.util.List<Models.Project> allProjects = projectDb.getAll();
        if (allProjects.isEmpty()) { JOptionPane.showMessageDialog(this, "No projects available."); return; }
        String[] projectOptions = allProjects.stream().map(p -> p.getID() + " - " + p.getName()).toArray(String[]::new);
        String selectedProj = (String) JOptionPane.showInputDialog(this, "Select Project:", "Update Project Status", JOptionPane.PLAIN_MESSAGE, null, projectOptions, projectOptions[0]);
        if (selectedProj == null) return;
        String projectId = selectedProj.split(" - ")[0];
        Models.Project p = projectDb.getById(projectId);
        if (p == null) { JOptionPane.showMessageDialog(this, "Invalid Project ID."); return; }
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:", "Update Project Status", JOptionPane.PLAIN_MESSAGE, null, statusOptions, p.getStatus());
        if (newStatus == null || newStatus.isEmpty()) return;
        p.changeStatus(newStatus);
        projectDb.update(p);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project status updated.");
    }

    private void showRemoveProjectDialog() {
        if (projectDb == null) return;
        // Select project from all projects
        java.util.List<Models.Project> allProjects = projectDb.getAll();
        if (allProjects.isEmpty()) { JOptionPane.showMessageDialog(this, "No projects available."); return; }
        String[] projectOptions = allProjects.stream().map(p -> p.getID() + " - " + p.getName()).toArray(String[]::new);
        String selectedProj = (String) JOptionPane.showInputDialog(this, "Select Project:", "Remove Project", JOptionPane.PLAIN_MESSAGE, null, projectOptions, projectOptions[0]);
        if (selectedProj == null) return;
        String projectId = selectedProj.split(" - ")[0];
        projectDb.delete(projectId);
        if (currentManager != null) currentManager.removeManagedProjectId(projectId);
        if (managerDb != null && currentManager != null) managerDb.update(currentManager);
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