package Models;
public class Executive extends Person{
    private String managersList;
    private String projectsList;
    private String employeeList;
    Executive (String managersList, String projectsList, String employeeList, String name, String ID, String email) {
        super(name, ID, email);
        this.managersList = managersList;
        this.projectsList = projectsList;
        this.employeeList = employeeList;
    }
    
}
