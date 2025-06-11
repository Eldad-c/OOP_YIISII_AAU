import Interfaces.*;
import Models.*;
import javax.swing.*;
import java.awt.*;
public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        frame = new JFrame("Project Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        LoginPage loginPage = new LoginPage();
        //Employee employee = new Employee();
        ProjectManager manager = new ProjectManager();
        //Executive executive = new Executive();

        mainPanel.add(loginPage, "Login");
        //mainPanel.add(employee, "Employee");
        mainPanel.add(manager, "Manager");
        //mainPanel.add(executive, "Executive");

        frame.add(mainPanel);
        loginPage.setLoginListener(new LoginPage.LoginListener() {
            @Override
            public void onLoginSuccess(String username, String role) {
                switch (role) {
                    //case "Employee":
                      //  cardLayout.show(mainPanel, "Employee");
                        //break;
                    case "Manager":
                        cardLayout.show(mainPanel, "Manager");
                        break;
                    //case "Executive":
                        //cardLayout.show(mainPanel, "Executive");
                        //break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Invalid role.");
                }
            }
        });


        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Main();
    }


}
