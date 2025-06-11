package Models;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Person {
    private String managerID;
    private String projectID;
    private String EmployeeID;
    /*private List<String> assignedTaskIds; 
    private List<String> projectIds;*/

    Employee(String managerID, String projectID, String EmployeeID, String name, String ID, String email, String password) {
        super(name, ID, email, password);
        this.managerID = managerID; 
        this.projectID = projectID;
        this.EmployeeID = EmployeeID;
        /*this.assignedTaskIds = assignedTaskIds;
        this.projectIds = projectIds;  */      


    }
    public String getManagerID() {
        return managerID;
    }
    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
    public String getProjectID() {
        return projectID;
    }
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
    public String getEmployeeID() {
        return EmployeeID;
    }
    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    /*public List<String> getAssignedTaskIds() {
        return assignedTaskIds;
    }

    public void setAssignedTaskIds(List<String> assignedTaskIds) {
        this.assignedTaskIds = assignedTaskIds;
    }

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }*/



    @Override
    public String getinfo() {
        return "Employee Name: " + getName() + ", ID: " + getID() + ", Email: " + getEmail() +
               ", Manager ID: " + managerID + ", Project ID: " + projectID + ", Employee ID: " + EmployeeID;
    }

    /*public static void main(String[] args) {
        List<String> tasks = new ArrayList<>();
        tasks.add("Task1");
        tasks.add("Task2");

        List<String> projects = new ArrayList<>();
        projects.add("Project1");
        projects.add("Project2");

        // Create an Employee object
        Employee employee = new Employee("John Doe", "E123", "M456", tasks, projects);

        // Print employee details
        System.out.println("Employee Name: " + employee.getName());
        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Manager ID: " + employee.getManagerId());
        System.out.println("Assigned Tasks: " + employee.getAssignedTaskIds());
        System.out.println("Project IDs: " + employee.getProjectIds());
    }*/
} 

