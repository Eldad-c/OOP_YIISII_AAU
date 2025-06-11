package Models;

import Database.TaskDatabase;
import java.util.ArrayList;

public class Project {
    private String ID;
    private String name;
    private String descriptionLink;
    private String startDate;
    private String endDate;
    private String status;
    private String managerID;
    private ArrayList<String> taskIDs;

    public Project(String ID, String name, String descriptionLink, String startDate, String endDate, String status,
            String managerID) {
        this.ID = ID;
        this.name = name;
        this.descriptionLink = descriptionLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.managerID = managerID;
        this.taskIDs = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }

    public void setDescriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void changeStatus(String status) {
        this.status = status;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public ArrayList<String> getTaskIDs() {
        return taskIDs;
    }

    public void setTaskIDs(ArrayList<String> taskIDs) {
        this.taskIDs = taskIDs != null ? taskIDs : new ArrayList<>();
    }

    // Adds a new task to this project and persists it in the TaskDatabase
    public void addTask(Task newTask, TaskDatabase taskDb) {
        if (newTask == null || newTask.getID() == null || taskIDs.contains(newTask.getID()))
            return;
        taskIDs.add(newTask.getID());
        taskDb.add(newTask);
    }

    // Removes a task by its ID from the project and the database
    public void removeTask(String taskId, TaskDatabase taskDb) {
        taskIDs.remove(taskId);
        taskDb.delete(taskId);
    }

    // Retrieves all Task objects associated with this specific project
    public ArrayList<Task> getTasks(TaskDatabase taskDb) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (String taskId : taskIDs) {
            Task t = taskDb.getById(taskId);
            if (t != null)
                tasks.add(t);
        }
        return tasks;
    }

    public String getInfo() {
        return "Project Name: " + name + ", ID: " + ID + ", Description Link: " + descriptionLink +
                ", Start Date: " + startDate + ", End Date: " + endDate + ", Status: " + status +
                ", Manager ID: " + managerID + ", Number of Tasks: " + taskIDs.size();
    }
}