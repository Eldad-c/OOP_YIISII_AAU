package Models;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String ID;
    private String name;
    private String descriptionLink;
    private String startDate;
    private String endDate;
    private String status;
    private String managerID;
    private List<String> taskIDs;

    Project(String ID, String name, String descriptionLink, String startDate, String endDate, String status, String managerID, List<String> taskIDs) {
        this.ID = ID;
        this.name = name;
        this.descriptionLink = descriptionLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.managerID = managerID;
        this.taskIDs = taskIDs != null ? taskIDs : new ArrayList<>();
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
    public List<String> getTaskIDs() {
        return new ArrayList<>(taskIDs);
    }
    public void setTaskIDs(List<String> taskIDs) {
        this.taskIDs = taskIDs != null ? taskIDs : new ArrayList<>();
    }
    public String getInfo() {
        return "Project Name: " + name + ", ID: " + ID + ", Description Link: " + descriptionLink +
               ", Start Date: " + startDate + ", End Date: " + endDate + ", Status: " + status +
               ", Manager ID: " + managerID + ", Number of Tasks: " + taskIDs.size();
    }
    
    void addTask(Task newTask, List<Task> allTasks){
        if (newTask == null || newTask.getID() == null || taskIDs.contains(newTask.getID())) {
            return;
        }
        taskIDs.add(newTask.getID());
        if (allTasks != null && !allTasks.contains(newTask)) {
            allTasks.add(newTask);
        }
    }

    void removeTask(String taskID, List<Task> allTasks) {
        if (taskID == null) {
            return;
        }
        taskIDs.remove(taskID);
        if (allTasks != null) {
            allTasks.removeIf(task -> task.getID().equals(taskID) && task.getProjectID().equals(this.ID));
        }
    }

    List <Task> getTasks (List<Task> allTasks) {
        List<Task> projectTasks = new ArrayList<>();
        if (allTasks == null) {
            return projectTasks;
        }
        for (String taskID : taskIDs) {
            for (Task task : allTasks) {
                if (task.getID().equals(taskID) && task.getProjectID().equals(this.ID)) {
                    projectTasks.add(task);
                    break;
                }
            }
        }
        return projectTasks;
    }
}