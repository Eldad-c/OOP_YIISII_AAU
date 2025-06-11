package Models;
public class Executive extends Person{
    private String managersList;
    private String projectsList;
    private String employeeList;
    Executive (String managersList, String projectsList, String employeeList, String name, String ID, String email, String password) {
        super(name, ID, email, password);
        this.managersList = managersList;
        this.projectsList = projectsList;
        this.employeeList = employeeList;
    }
    @Override
    public String getinfo() {
        return "Executive Name: " + getName() + ", ID: " + getID() + ", Email: " + getEmail() +
               ", Managers List: " + managersList + ", Projects List: " + projectsList +
               ", Employee List: " + employeeList;
    }
    
}
