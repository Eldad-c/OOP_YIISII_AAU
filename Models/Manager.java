package Models;
public class Manager extends Person {
    private String managedProjectID;
    private String managedEmployeeID;

    public Manager(String managedProjectID, String managedEmployeeID, String name, String ID, String email, String password) {
        super(name, ID, email, password);
        this.managedProjectID = managedProjectID;
        this.managedEmployeeID = managedEmployeeID;
        
        

    }
   @Override 
    public String getInfo() {
        return "Manager Name: " + getName() + ", ID: " + getID() + ", Email: " + getEmail() +
               ", Managed Project ID: " + managedProjectID + ", Managed Employee ID: " + managedEmployeeID;
    }
    

    // public String getProjectID() {
    //     return projectID;
    // }
    // public void setProjectID(String projectID) {
    //     this.projectID = projectID;
    // }
    // public String getEmployeeID() {
    //     return employeeID;
    // }   
    // public void setEmployeeID(String employeeID) {
    //     this.employeeID = employeeID;
    // }   
    // public String getEmployees(){
    //     return "Employee ID: " + employeeID;
    // }
    // public String getProjects(){
    //     return "Project ID: " + projectID;
    // }
    
}
