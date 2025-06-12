package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        addEmployeeBtn.addActionListener(_ -> addOrUpdatePersonDialog("Employee", true));
        updateEmployeeBtn.addActionListener(_ -> addOrUpdatePersonDialog("Employee", false));
        removeEmployeeBtn.addActionListener(_ -> removePersonDialog("Employee"));
        addManagerBtn.addActionListener(_ -> addOrUpdatePersonDialog("Manager", true));
        updateManagerBtn.addActionListener(_ -> addOrUpdatePersonDialog("Manager", false));
        removeManagerBtn.addActionListener(_ -> removePersonDialog("Manager"));
        addProjectBtn.addActionListener(_ -> addOrUpdateProjectDialog(true));
        updateProjectBtn.addActionListener(_ -> addOrUpdateProjectDialog(false));
        removeProjectBtn.addActionListener(_ -> removeProjectDialog());
        addExecutiveBtn.addActionListener(_ -> addOrUpdateExecutiveDialog(true));
        updateExecutiveBtn.addActionListener(_ -> addOrUpdateExecutiveDialog(false));
        removeExecutiveBtn.addActionListener(_ -> removeExecutiveDialog());
        generateReportBtn.addActionListener(_ -> showGenerateReportDialog());
        backBtn.addActionListener(_ -> { if (backListener != null) backListener.onBack(); });
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

    // --- Add/Update Employee or Manager ---
    private void addOrUpdatePersonDialog(String type, boolean isAdd) {
        String id = JOptionPane.showInputDialog(this, (isAdd ? "Enter new " : "Enter ") + type + " ID:");
        if (id == null || id.trim().isEmpty()) return;
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        if (email == null || email.trim().isEmpty()) return;
        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password == null || password.trim().isEmpty()) return;
        if (type.equals("Employee")) {
            String managerId = JOptionPane.showInputDialog(this, "Enter manager ID for this employee:");
            if (managerId == null || managerId.trim().isEmpty()) return;
            Models.Employee emp = new Models.Employee(managerId, name, id, email, password);
            if (isAdd) employeeDb.add(emp); else employeeDb.update(emp);
        } else if (type.equals("Manager")) {
            Models.Manager mgr = new Models.Manager(name, id, email, password);
            if (isAdd) managerDb.add(mgr); else managerDb.update(mgr);
        }
        refreshDisplay();
        JOptionPane.showMessageDialog(this, type + (isAdd ? " added." : " updated."));
    }

    private void removePersonDialog(String type) {
        String id = JOptionPane.showInputDialog(this, "Enter " + type + " ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        if (type.equals("Employee")) employeeDb.delete(id);
        else if (type.equals("Manager")) managerDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, type + " removed.");
    }

    // --- Add/Update Project ---
    private void addOrUpdateProjectDialog(boolean isAdd) {
        String id = JOptionPane.showInputDialog(this, (isAdd ? "Enter new " : "Enter ") + "Project ID:");
        if (id == null || id.trim().isEmpty()) return;
        String name = JOptionPane.showInputDialog(this, "Enter project name:");
        if (name == null || name.trim().isEmpty()) return;
        String description = JOptionPane.showInputDialog(this, "Enter description link (optional):");
        String startDate = JOptionPane.showInputDialog(this, "Enter start date (yyyy-MM-dd):");
        String endDate = JOptionPane.showInputDialog(this, "Enter end date (yyyy-MM-dd):");
        String status = JOptionPane.showInputDialog(this, "Enter status (STARTED, IN_PROGRESS, COMPLETED, etc.):");
        String managerId = JOptionPane.showInputDialog(this, "Enter manager ID for this project:");
        if (managerId == null || managerId.trim().isEmpty()) return;
        Models.Project proj = new Models.Project(id, name, description, startDate, endDate, status, managerId);
        if (isAdd) projectDb.add(proj); else projectDb.update(proj);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project " + (isAdd ? "added." : "updated."));
    }

    private void removeProjectDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Project ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        projectDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Project removed.");
    }

    // --- Add/Update Executive ---
    private void addOrUpdateExecutiveDialog(boolean isAdd) {
        String id = JOptionPane.showInputDialog(this, (isAdd ? "Enter new " : "Enter ") + "Executive ID:");
        if (id == null || id.trim().isEmpty()) return;
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        if (email == null || email.trim().isEmpty()) return;
        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password == null || password.trim().isEmpty()) return;
        Models.Executive exec = new Models.Executive(name, id, email, password);
        if (isAdd) executiveDb.add(exec); else executiveDb.update(exec);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Executive " + (isAdd ? "added." : "updated."));
    }

    private void removeExecutiveDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Executive ID to remove:");
        if (id == null || id.trim().isEmpty()) return;
        executiveDb.delete(id);
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Executive removed.");
    }

    // --- Generate Report ---
    private void showGenerateReportDialog() {
        if (currentExecutive == null || projectDb == null || employeeDb == null) return;
        String[] options = {"PROJECT_PROGRESS", "EMPLOYEE_WORKLOAD"};
        String reportType = (String) JOptionPane.showInputDialog(this, "Select report type:", "Generate Report", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (reportType == null) return;
        Models.Report report = currentExecutive.generateReport(reportType, projectDb, employeeDb);
        JOptionPane.showMessageDialog(this, report.getContent(), "Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
