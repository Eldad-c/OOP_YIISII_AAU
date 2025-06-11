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
        this.assignedUserID = userID;
    }

    void changeStatus (String status){
        this.status = status;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getDescriptionLink() {
        return descriptionLink;
    }
    public void setDescriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
    }
    public String getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(String assignedUserID) {
        this.assignedUserID = assignedUserID;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getProjectID() {
        return projectID;
    }
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
    
    boolean isOverdue() {
        Date currentDate = new Date();
        return dueDate.before(currentDate);
    }
}