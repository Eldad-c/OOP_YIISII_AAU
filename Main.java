import GUI.*;
import javax.swing.*;
import java.awt.*;
import Database.*;
import Models.*;

public class Main {
    private JFrame frame;
    private GridBagLayout gridBagLayout;
    private JPanel mainPanel;
    private LoginPageGUI loginPage;
    private EmployeeGUI employee;
    private ProjectManagerGUI manager;
    private ExecutiveGUI executive;

    public Main() {
        // --- Database Instantiation ---
        EmployeeDatabase employeeDb = new EmployeeDatabase();
        ManagerDatabase managerDb = new ManagerDatabase();
        ExecutiveDatabase executiveDb = new ExecutiveDatabase();
        // Add only one default executive account if not present
        if (executiveDb.getById("default") == null) {
            // Default executive: id, name, email, password all 'default'
            executiveDb.add(new Executive("default", "default", "default@email.com", "default"));
        }
        ProjectDatabase projectDb = new ProjectDatabase();
        TaskDatabase taskDb = new TaskDatabase();
        PersonDatabase personDb = new PersonDatabase();

        frame = new JFrame("Project Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null); // Center the window

        gridBagLayout = new GridBagLayout();
        mainPanel = new JPanel(gridBagLayout);

        loginPage = new LoginPageGUI();
        employee = new EmployeeGUI();
        manager = new ProjectManagerGUI();
        executive = new ExecutiveGUI();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        mainPanel.add(loginPage, gbc);
        mainPanel.add(employee, gbc);
        mainPanel.add(manager, gbc);
        mainPanel.add(executive, gbc);

        frame.add(mainPanel);

        // Helper to show only one panel
        Runnable showLogin = () -> {
            loginPage.setVisible(true);
            employee.setVisible(false);
            manager.setVisible(false);
            executive.setVisible(false);
        };
        Runnable showEmployee = () -> {
            loginPage.setVisible(false);
            employee.setVisible(true);
            manager.setVisible(false);
            executive.setVisible(false);
        };
        Runnable showManager = () -> {
            loginPage.setVisible(false);
            employee.setVisible(false);
            manager.setVisible(true);
            executive.setVisible(false);
        };
        Runnable showExecutive = () -> {
            loginPage.setVisible(false);
            employee.setVisible(false);
            manager.setVisible(false);
            executive.setVisible(true);
        };
        showLogin.run();

        // --- Login Authentication Logic ---
        loginPage.setLoginListener((userId, password, role) -> {
            boolean authenticated = false;
            switch (role) {
                case "Employee": {
                    Employee emp = employeeDb.getById(userId);
                    if (emp != null && emp.getPassword().equals(password)) {
                        employee.setEmployeeAndDatabases(emp, employeeDb, taskDb, projectDb);
                        authenticated = true;
                    }
                    break;
                }
                case "Manager": {
                    Manager mgr = managerDb.getById(userId);
                    if (mgr != null && mgr.getPassword().equals(password)) {
                        manager.setManagerAndDatabases(mgr, managerDb, employeeDb, projectDb, taskDb);
                        authenticated = true;
                    }
                    break;
                }
                case "Executive": {
                    Executive exec = executiveDb.getById(userId);
                    if (exec != null && exec.getPassword().equals(password)) {
                        executive.setExecutiveAndDatabases(exec, executiveDb, managerDb, employeeDb, projectDb, taskDb);
                        authenticated = true;
                    }
                    break;
                }
            }
            if (authenticated) {
                switch (role) {
                    case "Employee": showEmployee.run(); break;
                    case "Manager": showManager.run(); break;
                    case "Executive": showExecutive.run(); break;
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid ID or password.");
            }
        });

        // Back buttons switch back to Login
        employee.setBackListener(() -> showLogin.run());
        manager.setBackListener(() -> showLogin.run());
        executive.setBackListener(() -> showLogin.run());

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
