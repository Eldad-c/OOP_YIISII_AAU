import GUI.*;
import javax.swing.*;
import java.awt.*;
import Database.*;
import Models.*;

public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        // --- Database Instantiation ---
        EmployeeDatabase employeeDb = new EmployeeDatabase();
        ManagerDatabase managerDb = new ManagerDatabase();
        ExecutiveDatabase executiveDb = new ExecutiveDatabase();
        // Add only one default executive account if not present
        if (executiveDb.getById("defaultexecutive") == null) {
            executiveDb.add(new Executive("defaultexecutive", "defaultexecutive", "defaultexecutive@email.com", "defaultpassword"));
        }
        ProjectDatabase projectDb = new ProjectDatabase();
        TaskDatabase taskDb = new TaskDatabase();
        PersonDatabase personDb = new PersonDatabase();

        frame = new JFrame("Project Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        LoginPageGUI loginPage = new LoginPageGUI();
        EmployeeGUI employee = new EmployeeGUI();
        ProjectManagerGUI manager = new ProjectManagerGUI();
        ExecutiveGUI executive = new ExecutiveGUI();

        mainPanel.add(loginPage, "Login");
        mainPanel.add(employee, "Employee");
        mainPanel.add(manager, "Manager");
        mainPanel.add(executive, "Executive");

        frame.add(mainPanel);

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
                cardLayout.show(mainPanel, role);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid ID or password.");
            }
        });

        // Back buttons switch back to Login
        employee.setBackListener(() -> cardLayout.show(mainPanel, "Login"));
        manager.setBackListener(() -> cardLayout.show(mainPanel, "Login"));
        executive.setBackListener(() -> cardLayout.show(mainPanel, "Login"));

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
