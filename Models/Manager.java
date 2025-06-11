package Models;
public class Manager extends Person {
    private String projectID;
    private String employeeID;

    public Manager(String projectID, String employeeID, String name, String ID, String email) {
        super(name, ID, email);
        this.projectID = projectID;
        this.employeeID = employeeID;
        
        

    }
    public String getProjectID() {
        return projectID;
    }
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
    public String getEmployeeID() {
        return employeeID;
    }   
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }   

}
