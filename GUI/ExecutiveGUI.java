package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ExecutiveGUI extends JPanel {
    // Backend references and current executive
    private Models.Executive currentExecutive;
    private Database.ExecutiveDatabase executiveDb;
    private Database.ManagerDatabase managerDb;
    private Database.EmployeeDatabase employeeDb;
    private Database.ProjectDatabase projectDb;
    private Database.TaskDatabase taskDb;

    private JTextArea statsArea, employeeListArea, managerListArea, projectListArea, executiveListArea;
    private JButton addEmployeeBtn, updateEmployeeBtn, removeEmployeeBtn;
    private JButton addManagerBtn, updateManagerBtn, removeManagerBtn;
    private JButton addProjectBtn, updateProjectBtn, removeProjectBtn;
    private JButton addExecutiveBtn, updateExecutiveBtn, removeExecutiveBtn;
    private JButton generateReportBtn, backBtn;
    private BackListener backListener;

    public interface BackListener { void onBack(); }
    public void setBackListener(BackListener listener) { this.backListener = listener; }

    public ExecutiveGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;

        // --- System Stats ---
        JLabel statsLabel = new JLabel("System Overview");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        add(statsLabel, gbc);

        statsArea = new JTextArea(3, 60);
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        gbc.gridy = 1;
        add(new JScrollPane(statsArea), gbc);

        // --- Employee List ---
        JLabel empLabel = new JLabel("Employees (A-Z):");
        empLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2; gbc.gridwidth = 1; gbc.gridx = 0;
        add(empLabel, gbc);
        employeeListArea = new JTextArea(10, 20);
        employeeListArea.setEditable(false);
        gbc.gridy = 3;
        add(new JScrollPane(employeeListArea), gbc);
        addEmployeeBtn = new JButton("Add Employee");
        updateEmployeeBtn = new JButton("Update Employee");
        removeEmployeeBtn = new JButton("Remove Employee");
        JPanel empBtnPanel = new JPanel();
        empBtnPanel.add(addEmployeeBtn); empBtnPanel.add(updateEmployeeBtn); empBtnPanel.add(removeEmployeeBtn);
        gbc.gridy = 4;
        add(empBtnPanel, gbc);

        // --- Manager List ---
        JLabel mgrLabel = new JLabel("Managers (A-Z):");
        mgrLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        add(mgrLabel, gbc);
        managerListArea = new JTextArea(10, 20);
        managerListArea.setEditable(false);
        gbc.gridy = 3;
        add(new JScrollPane(managerListArea), gbc);
        addManagerBtn = new JButton("Add Manager");
        updateManagerBtn = new JButton("Update Manager");
        removeManagerBtn = new JButton("Remove Manager");
        JPanel mgrBtnPanel = new JPanel();
        mgrBtnPanel.add(addManagerBtn); mgrBtnPanel.add(updateManagerBtn); mgrBtnPanel.add(removeManagerBtn);
        gbc.gridy = 4;
        add(mgrBtnPanel, gbc);

        // --- Project List ---
        JLabel projLabel = new JLabel("Projects (A-Z):");
        projLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2; gbc.gridy = 2;
        add(projLabel, gbc);
        projectListArea = new JTextArea(10, 20);
        projectListArea.setEditable(false);
        gbc.gridy = 3;
        add(new JScrollPane(projectListArea), gbc);
        addProjectBtn = new JButton("Add Project");
        updateProjectBtn = new JButton("Update Project");
        removeProjectBtn = new JButton("Remove Project");
        JPanel projBtnPanel = new JPanel();
        projBtnPanel.add(addProjectBtn); projBtnPanel.add(updateProjectBtn); projBtnPanel.add(removeProjectBtn);
        gbc.gridy = 4;
        add(projBtnPanel, gbc);

        // --- Executive List ---
        JLabel execLabel = new JLabel("Executives (A-Z):");
        execLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        add(execLabel, gbc);
        executiveListArea = new JTextArea(5, 60);
        executiveListArea.setEditable(false);
        gbc.gridy = 6;
        add(new JScrollPane(executiveListArea), gbc);
        addExecutiveBtn = new JButton("Add Executive");
        updateExecutiveBtn = new JButton("Update Executive");
        removeExecutiveBtn = new JButton("Remove Executive");
        JPanel execBtnPanel = new JPanel();
        execBtnPanel.add(addExecutiveBtn); execBtnPanel.add(updateExecutiveBtn); execBtnPanel.add(removeExecutiveBtn);
        gbc.gridy = 7;
        add(execBtnPanel, gbc);

        // --- Report and Back ---
        generateReportBtn = new JButton("Generate Report");
        backBtn = new JButton("Back");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(generateReportBtn); bottomPanel.add(backBtn);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        add(bottomPanel, gbc);

        // --- Event Listeners ---
        addEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addEmployeeDialog();
            }
        });
        updateEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateEmployeeDialog();
            }
        });
        removeEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                removeEmployeeDialog();
            }
        });
        addManagerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addManagerDialog();
            }
        });
        updateManagerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateManagerDialog();
            }
        });
        removeManagerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                removeManagerDialog();
            }
        });
        addProjectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addOrUpdateProjectDialog(true);
            }
        });
        updateProjectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addOrUpdateProjectDialog(false);
            }
        });
        removeProjectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                removeProjectDialog();
            }
        });
        addExecutiveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addOrUpdateExecutiveDialog(true);
            }
        });
        updateExecutiveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addOrUpdateExecutiveDialog(false);
            }
        });
        removeExecutiveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                removeExecutiveDialog();
            }
        });
        generateReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showGenerateReportDialog();
            }
        });
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (backListener != null) backListener.onBack();
            }
        });
    }

    public void setExecutiveAndDatabases(Models.Executive exec, Database.ExecutiveDatabase execDb, Database.ManagerDatabase mgrDb, Database.EmployeeDatabase empDb, Database.ProjectDatabase projDb, Database.TaskDatabase tDb) {
        this.currentExecutive = exec;
        this.executiveDb = execDb;
        this.managerDb = mgrDb;
        this.employeeDb = empDb;
        this.projectDb = projDb;
        this.taskDb = tDb;
        refreshDisplay();
    }

    private void refreshDisplay() {
        // --- Stats ---
        int totalEmployees = employeeDb.getAll().size();
        int totalManagers = managerDb.getAll().size();
        int totalProjects = projectDb.getAll().size();
        int totalTasks = taskDb != null ? taskDb.getAll().size() : 0;
        statsArea.setText(String.format("Employees: %d\nManagers: %d\nProjects: %d\nTasks: %d", totalEmployees, totalManagers, totalProjects, totalTasks));

        // --- Employee List ---
        java.util.List<Models.Employee> employees = employeeDb.getAll().stream()
            .sorted(Comparator.comparing(Models.Employee::getName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
        StringBuilder empSb = new StringBuilder();
        for (Models.Employee e : employees) {
            empSb.append(e.getName()).append(" (ID: ").append(e.getID()).append(")\n");
        }
        employeeListArea.setText(empSb.toString());

        // --- Manager List ---
        java.util.List<Models.Manager> managers = managerDb.getAll().stream()
            .sorted(Comparator.comparing(Models.Manager::getName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
        StringBuilder mgrSb = new StringBuilder();
        for (Models.Manager m : managers) {
            mgrSb.append(m.getName()).append(" (ID: ").append(m.getID()).append(")\n");
        }
        managerListArea.setText(mgrSb.toString());

        // --- Project List ---
        java.util.List<Models.Project> projects = projectDb.getAll().stream()
            .sorted(Comparator.comparing(Models.Project::getName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
        StringBuilder projSb = new StringBuilder();
        for (Models.Project p : projects) {
            projSb.append(p.getName()).append(" (ID: ").append(p.getID()).append(", Status: ").append(p.getStatus()).append(")\n");
        }
        projectListArea.setText(projSb.toString());

        // --- Executive List ---
        java.util.List<Models.Executive> executives = executiveDb.getAll().stream()
            .sorted(Comparator.comparing(Models.Executive::getName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
        StringBuilder execSb = new StringBuilder();
        for (Models.Executive e : executives) {
            execSb.append(e.getName()).append(" (ID: ").append(e.getID()).append(")\n");
        }
        executiveListArea.setText(execSb.toString());
    }

    // --- Add Employee ---
    private void addEmployeeDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter new Employee ID:");
        if (id == null || id.trim().isEmpty()) return;
        if (employeeDb.getById(id) != null) {
            JOptionPane.showMessageDialog(this, "ID already exists.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        if (email == null || email.trim().isEmpty()) return;
        JPasswordField passwordField = new JPasswordField();
        int pwResult = JOptionPane.showConfirmDialog(this, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pwResult != JOptionPane.OK_OPTION) return;
        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) return;
        // Manager selection (optional)
        java.util.List<Models.Manager> managers = managerDb.getAll();
        String[] managerOptions = managers.stream().map(m -> m.getID() + " - " + m.getName()).toArray(String[]::new);
        String managerID = null;
        if (managerOptions.length > 0) {
            managerID = (String) JOptionPane.showInputDialog(this, "Select manager (optional):", "Manager Selection", JOptionPane.PLAIN_MESSAGE, null, managerOptions, managerOptions[0]);
            if (managerID != null && managerID.contains(" - ")) managerID = managerID.split(" - ")[0];
        }
        Models.Employee emp = new Models.Employee(managerID == null ? "" : managerID, name, id, email, password);
        employeeDb.add(emp);
        employeeDb.saveToFile(); // <-- Ensure data is persisted
        // Add to manager's managedEmployeeIds if manager selected
        if (managerID != null && !managerID.isEmpty()) {
            Models.Manager mgr = managerDb.getById(managerID);
            if (mgr != null) {
                mgr.addManagedEmployeeId(id);
                managerDb.update(mgr);
                managerDb.saveToFile(); // <-- Persist manager update
            }
        }
        // Add to executive's organizationEmployeesList
        if (currentExecutive != null && !currentExecutive.getOrganizationEmployeesList().contains(id)) {
            currentExecutive.getOrganizationEmployeesList().add(id);
            executiveDb.update(currentExecutive);
            executiveDb.saveToFile(); // <-- Persist executive update
        }
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Employee added.");
    }

    // --- Update Employee ---
    private void updateEmployeeDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter existing Employee ID to update:");
        if (id == null || id.trim().isEmpty()) return;
        Models.Employee existing = employeeDb.getById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "ID does not exist.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter new name:", existing.getName());
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter new email:", existing.getEmail());
        if (email == null || email.trim().isEmpty()) return;
        JPasswordField passwordField = new JPasswordField();
        int pwResult = JOptionPane.showConfirmDialog(this, passwordField, "Enter new password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pwResult != JOptionPane.OK_OPTION) return;
        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) return;
        // Manager selection (optional)
        java.util.List<Models.Manager> managers = managerDb.getAll();
        String[] managerOptions = managers.stream().map(m -> m.getID() + " - " + m.getName()).toArray(String[]::new);
        final String[] selectedManagerID = { existing.getManagerID() };
        if (managerOptions.length > 0) {
            String currentManager = selectedManagerID[0] == null ? null : managers.stream().filter(m -> m.getID().equals(selectedManagerID[0])).map(m -> m.getID() + " - " + m.getName()).findFirst().orElse(managerOptions[0]);
            String selected = (String) JOptionPane.showInputDialog(this, "Select manager (optional):", "Manager Selection", JOptionPane.PLAIN_MESSAGE, null, managerOptions, currentManager);
            if (selected != null && selected.contains(" - ")) {
                selectedManagerID[0] = selected.split(" - ")[0];
            }
        }
        existing.setName(name);
        existing.setEmail(email);
        existing.setPassword(password);
        existing.setManagerID(selectedManagerID[0] == null ? "" : selectedManagerID[0]);
        employeeDb.update(existing);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Employee updated.");
    }

    // --- Add Manager ---
    private void addManagerDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter new Manager ID:");
        if (id == null || id.trim().isEmpty()) return;
        if (managerDb.getById(id) != null) {
            JOptionPane.showMessageDialog(this, "ID already exists.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        if (email == null || email.trim().isEmpty()) return;
        JPasswordField passwordField = new JPasswordField();
        int pwResult = JOptionPane.showConfirmDialog(this, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pwResult != JOptionPane.OK_OPTION) return;
        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) return;
        Models.Manager mgr = new Models.Manager(name, id, email, password);
        managerDb.add(mgr);
        managerDb.saveToFile(); // <-- Ensure data is persisted
        // Add to executive's organizationManagersList
        if (currentExecutive != null && !currentExecutive.getOrganizationManagersList().contains(id)) {
            currentExecutive.getOrganizationManagersList().add(id);
            executiveDb.update(currentExecutive);
            executiveDb.saveToFile(); // <-- Persist executive update
        }
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Manager added.");
    }

    // --- Update Manager ---
    private void updateManagerDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter existing Manager ID to update:");
        if (id == null || id.trim().isEmpty()) return;
        Models.Manager existing = managerDb.getById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "ID does not exist.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter new name:", existing.getName());
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter new email:", existing.getEmail());
        if (email == null || email.trim().isEmpty()) return;
        JPasswordField passwordField = new JPasswordField();
        int pwResult = JOptionPane.showConfirmDialog(this, passwordField, "Enter new password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pwResult != JOptionPane.OK_OPTION) return;
        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) return;
        existing.setName(name);
        existing.setEmail(email);
        existing.setPassword(password);
        managerDb.update(existing);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Manager updated.");
    }

    // --- Remove Employee ---
    private void removeEmployeeDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Employee ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        employeeDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Employee removed.");
    }

    // --- Remove Manager ---
    private void removeManagerDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Manager ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        managerDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Manager removed.");
    }

    // --- Add/Update Project ---
    private void addOrUpdateProjectDialog(boolean isAdd) {
        String id;
        if (isAdd) {
            id = JOptionPane.showInputDialog(this, "Enter new Project ID:");
            if (id == null || id.trim().isEmpty()) return;
            if (projectDb.getById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID already exists.");
                return;
            }
        } else {
            id = JOptionPane.showInputDialog(this, "Enter existing Project ID to update:");
            if (id == null || id.trim().isEmpty()) return;
            if (projectDb.getById(id) == null) {
                JOptionPane.showMessageDialog(this, "ID does not exist.");
                return;
            }
        }
        String name = JOptionPane.showInputDialog(this, "Enter project name:");
        if (name == null || name.trim().isEmpty()) return;
        String description = JOptionPane.showInputDialog(this, "Enter description link (optional):");
        String startDate = JOptionPane.showInputDialog(this, "Enter start date (yyyy-MM-dd):");
        String endDate = JOptionPane.showInputDialog(this, "Enter end date (yyyy-MM-dd):");
        // --- Status selection via radio buttons ---
        String[] statusOptions = {"STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED"};
        ButtonGroup statusGroup = new ButtonGroup();
        JPanel statusPanel = new JPanel(new GridLayout(0, 1));
        JRadioButton[] statusButtons = new JRadioButton[statusOptions.length];
        for (int i = 0; i < statusOptions.length; i++) {
            statusButtons[i] = new JRadioButton(statusOptions[i]);
            statusGroup.add(statusButtons[i]);
            statusPanel.add(statusButtons[i]);
        }
        // Default selection
        statusButtons[0].setSelected(true);
        int statusResult = JOptionPane.showConfirmDialog(this, statusPanel, "Select status:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (statusResult != JOptionPane.OK_OPTION) return;
        String status = null;
        for (JRadioButton btn : statusButtons) {
            if (btn.isSelected()) {
                status = btn.getText();
                break;
            }
        }
        if (status == null) return;
        // Manager selection (required)
        java.util.List<Models.Manager> managers = managerDb.getAll();
        if (managers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No managers available. Please add a manager first.");
            return;
        }
        String[] managerOptions = managers.stream().map(m -> m.getID() + " - " + m.getName()).toArray(String[]::new);
        String managerID = (String) JOptionPane.showInputDialog(this, "Select manager:", "Manager Selection", JOptionPane.PLAIN_MESSAGE, null, managerOptions, managerOptions[0]);
        if (managerID == null) return;
        if (managerID.contains(" - ")) managerID = managerID.split(" - ")[0];
        Models.Project proj = new Models.Project(id, name, description, startDate, endDate, status, managerID);
        if (isAdd) projectDb.add(proj); else projectDb.update(proj);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project " + (isAdd ? "added." : "updated."));
    }

    private void removeProjectDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Project ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        if (projectDb.getById(id) == null) {
            JOptionPane.showMessageDialog(this, "ID does not exist.");
            return;
        }
        projectDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project removed.");
    }

    // --- Add/Update Executive ---
    private void addOrUpdateExecutiveDialog(boolean isAdd) {
        String id;
        if (isAdd) {
            id = JOptionPane.showInputDialog(this, "Enter new Executive ID:");
            if (id == null || id.trim().isEmpty()) return;
            if (executiveDb.getById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID already exists.");
                return;
            }
        } else {
            id = JOptionPane.showInputDialog(this, "Enter existing Executive ID to update:");
            if (id == null || id.trim().isEmpty()) return;
            if (executiveDb.getById(id) == null) {
                JOptionPane.showMessageDialog(this, "ID does not exist.");
                return;
            }
        }
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        if (email == null || email.trim().isEmpty()) return;
        JPasswordField passwordField = new JPasswordField();
        int pwResult = JOptionPane.showConfirmDialog(this, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pwResult != JOptionPane.OK_OPTION) return;
        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) return;
        if (isAdd) {
            Models.Executive exec = new Models.Executive(name, id, email, password);
            executiveDb.add(exec);
        } else {
            Models.Executive existing = executiveDb.getById(id);
            existing.setName(name);
            existing.setEmail(email);
            existing.setPassword(password);
            executiveDb.update(existing);
        }
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Executive " + (isAdd ? "added." : "updated."));
    }

    private void removeExecutiveDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Executive ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        if (executiveDb.getById(id) == null) {
            JOptionPane.showMessageDialog(this, "ID does not exist.");
            return;
        }
        executiveDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Executive removed.");
    }

    // --- Generate Report ---
    private void showGenerateReportDialog() {
        if (currentExecutive == null || projectDb == null || employeeDb == null || managerDb == null || taskDb == null) return;
        StringBuilder report = new StringBuilder();
        report.append("--- PROJECTS OVERVIEW ---\n");
        java.util.List<Models.Project> projects = projectDb.getAll();
        if (projects.isEmpty()) {
            report.append("No projects available.\n");
        } else {
            for (Models.Project p : projects) {
                report.append("Project: ").append(p.getName()).append(" (ID: ").append(p.getID()).append(")\n");
                report.append("  Status: ").append(p.getStatus()).append("\n");
                // Manager
                Models.Manager mgr = managerDb.getById(p.getManagerID());
                if (mgr != null) {
                    report.append("  Managed by: ").append(mgr.getName()).append(" (ID: ").append(mgr.getID()).append(")\n");
                } else {
                    report.append("  Managed by: [Unknown] (ID: ").append(p.getManagerID()).append(")\n");
                }
                // Tasks
                java.util.List<String> taskIds = p.getTaskIDs();
                if (taskIds.isEmpty()) {
                    report.append("  No tasks assigned.\n");
                } else {
                    for (String taskId : taskIds) {
                        Models.Task t = taskDb.getById(taskId);
                        if (t != null) {
                            report.append("    Task: ").append(t.getID()).append(" - ").append(t.getDescriptionLink() != null ? t.getDescriptionLink() : "No Description").append("\n");
                            report.append("      Status: ").append(t.getStatus()).append("\n");
                            // Assigned employee
                            Models.Employee emp = employeeDb.getById(t.getAssignedUserID());
                            if (emp != null) {
                                report.append("      Assigned to: ").append(emp.getName()).append(" (ID: ").append(emp.getID()).append(")\n");
                            } else if (t.getAssignedUserID() != null && !t.getAssignedUserID().isEmpty()) {
                                report.append("      Assigned to: [Unknown] (ID: ").append(t.getAssignedUserID()).append(")\n");
                            } else {
                                report.append("      Assigned to: [Unassigned]\n");
                            }
                        }
                    }
                }
                report.append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, report.toString(), "Project Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
