package Interfaces;

public class Employee extends person {
     private String managerId; // The ID of their direct manager
    private List<String> assignedTaskIds; // IDs of specific tasks assigned to this employee
    private List<String> projectIds; // IDs of projects this employee is actively involved in
//constructor
    public Employee(String name, String id, String managerId, List<String> assignedTaskIds, List<String> projectIds) {
        super(name, id); // Call the constructor of the Person class
        this.managerId = managerId;
        this.assignedTaskIds = assignedTaskIds;
        this.projectIds = projectIds;
    }
 // Getters and Setters
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public List<String> getAssignedTaskIds() {
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
    }
    
 public static void main(String[] args) {
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
    }
}
