package Interfaces;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ProjectManager extends JPanel {
    private JTextArea projectArea, employeeArea;
    private JLabel project, employee;
    private JComboBox<String> taskDropdown, projectDropdown;

    public ProjectManager() {
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

        // Projects Text Area
        projectArea = new JTextArea(5, 40);
        projectArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridy = 1;
        add(projectArea, gbc);

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

        // Employees Text Area
        employeeArea = new JTextArea(5, 40);
        employeeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridy = 4;
        add(employeeArea, gbc);

        // Project Dropdown
        String[] projectOptions = {"Add New Project", "Update Project Status", "Remove Project"};
        projectDropdown = new JComboBox<>(projectOptions);
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(projectDropdown, gbc);

        // Add event handling
        taskDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTask = (String) taskDropdown.getSelectedItem();
                System.out.println("Task Action Selected: " + selectedTask);
                // Add specific method calls here if needed
            }
        });

        projectDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProject = (String) projectDropdown.getSelectedItem();
                System.out.println("Project Action Selected: " + selectedProject);
                // Add specific method calls here if needed
            }
        });
    }
}
