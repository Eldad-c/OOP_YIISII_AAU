package Models;
import java.util.Date;
public class Task {
    String ID;
    String descriptionLink;
    String assignedUserID;
    String status;
    Date dueDate;
    String projectID;
    Task (String ID, String descriptionLink, String assignedUserID, String status, Date dueDate, String projectID) {
        this.ID = ID;
        this.descriptionLink = descriptionLink;
        this.assignedUserID = assignedUserID;
        this.status = status;
        this.dueDate = dueDate;
        this.projectID = projectID; 
    }
    void assignUser (String userID){
    
    }
    void changeStatus (String status){
        this.status = status;
    }
    boolean isOverdue() {
        Date currentDate = new Date();
        return dueDate.before(currentDate);
    }

}
