package Models;
class Employee extends Person {
    private String managerID;
    private String projectID;
    private String EmployeeID;
    Employee(String managerID, String projectID, String EmployeeID, String name, String ID, String email) {
        super(name, ID, email);
        this.managerID = managerID; 
        this.projectID = projectID;
        this.EmployeeID = EmployeeID;
        


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
} 

