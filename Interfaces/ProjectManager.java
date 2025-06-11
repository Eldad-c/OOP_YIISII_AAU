package Interfaces;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class ProjectManager {
    private JTextArea projectArea, employeeArea;
    private JLabel project, employee;
    private JFrame pmFrame;
    private JButton taskUpdate, newTask, projectUpdate, taskAssign;

    public ProjectManager () {
        pmFrame = new JFrame("Welcome {Project Manager Name}");
        pmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pmFrame.setSize(900, 600);
        pmFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 2;
        gbc.weighty = 3;

        //Current Projects Label
        project = new JLabel("Current Projects:");
        project.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pmFrame.add(project, gbc);

        //Projects Text Area
        projectArea = new JTextArea(10, 40);
        projectArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridy = 1;
        pmFrame.add(projectArea, gbc);
        
        //Current Employees Label
        employee = new JLabel("Current Employees:");
        employee.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pmFrame.add(employee, gbc);

        //Current Employees Area
        employeeArea = new JTextArea(10, 40);
        employeeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridy = 3;
        pmFrame.add(employeeArea, gbc);

        //Buttons Row 1
        gbc.gridy = 4;
        gbc.gridwidth = 1;

        taskUpdate = new JButton("Update Task Status");
        gbc.gridx = 0;
        pmFrame.add(taskUpdate, gbc);

        projectUpdate = new JButton("Update Project Status");
        gbc.gridx = 1;
        pmFrame.add(projectUpdate, gbc);


        //Buttons Row 2
        gbc.gridy = 5;

        newTask = new JButton("Add New Task");
        gbc.gridx = 0;
        pmFrame.add(newTask, gbc);

        taskAssign = new JButton("Assign Task");
        gbc.gridx = 1;
        pmFrame.add(taskAssign, gbc);



        pmFrame.setVisible(true);
        





    }

    public static void main(String[] args) {
        new ProjectManager();
    }

}
