package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExecutiveGUI extends JPanel {
    private JTextArea overviewArea;
    private JButton updateProjectsBtn, updateEmployeesBtn, generateReportBtn, backBtn;
    private BackListener backListener;

    public interface BackListener {
        void onBack();
    }

    public void setBackListener(BackListener listener) {
        this.backListener = listener;
    }

    public ExecutiveGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Executive Overview Label
        JLabel overviewLabel = new JLabel("Executive Overview:");
        overviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(overviewLabel, gbc);

        // Overview Text Area inside ScrollPane
        overviewArea = new JTextArea(8, 50);
        overviewArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        overviewArea.setEditable(false);
        JScrollPane overviewScrollPane = new JScrollPane(overviewArea);
        gbc.gridy = 1;
        add(overviewScrollPane, gbc);

        // Buttons
        updateProjectsBtn = new JButton("Update Projects");
        updateEmployeesBtn = new JButton("Update Employees");
        generateReportBtn = new JButton("Generate Report");

        // Set equal button widths
        Dimension btnSize = new Dimension(160, 30);
        updateProjectsBtn.setPreferredSize(btnSize);
        updateEmployeesBtn.setPreferredSize(btnSize);
        generateReportBtn.setPreferredSize(btnSize);

        gbc.gridwidth = 1;
        gbc.gridy = 2;

        gbc.gridx = 0;
        add(updateProjectsBtn, gbc);

        gbc.gridx = 1;
        add(updateEmployeesBtn, gbc);

        gbc.gridx = 2;
        add(generateReportBtn, gbc);

        // Back Button
        backBtn = new JButton("Back");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(backBtn, gbc);

        // Event Listeners
        updateProjectsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Update Projects clicked");
            }
        });

        updateEmployeesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Update Employees clicked");
            }
        });

        generateReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Generate Report clicked");
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backListener != null) {
                    backListener.onBack();
                }
            }
        });
    }
}
