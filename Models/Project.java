package Models;
public class Project {

    private String ID;
    private String name;
    private String descriptionLink;
    private String startDate;
    private String endDate;
    private String status;
    private String managerID;
    private String taskIDs;
    Project(String ID, String name, String descriptionLink, String startDate, String endDate, String status, String managerID, String taskIDs) {
        this.ID = ID;
        this.name = name;
        this.descriptionLink = descriptionLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.managerID = managerID;
        this.taskIDs = taskIDs; 

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
    public void setStatus(String status) {
        this.status = status;
    }
    public String getManagerID() {
        return managerID;
    }
    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
    public String getTaskIDs() {
        return taskIDs;
    }
    public void setTaskIDs(String taskIDs) {
        this.taskIDs = taskIDs;
    }
    public String getInfo() {
        return "Project Name: " + name + ", ID: " + ID + ", Description Link: " + descriptionLink +
               ", Start Date: " + startDate + ", End Date: " + endDate + ", Status: " + status +
               ", Manager ID: " + managerID + ", Task IDs: " + taskIDs;
    }
   
    // void addTask( Task newTask, TaskDatabase taskDb){
    //     taskDb.addTask(newTask);
    //     this.taskIDs += "," + newTask.getID();
    // }

    // void removeTask(String taskID, TaskDatabase taskDb) {
    //     taskDb.removeTask(taskID);
    //     this.taskIDs = this.taskIDs.replace("," + taskID, "").replace(taskID + ",", "").replace(taskID, "");
    // }
    // String getStatus(){
    //     return status;
    // }
    // void changeStatus(String newStatus){
    //     this.status = newStatus;

    // }
    // List <Task> getTasks (TaskDatabase taskDb) {
    //     List<Task> tasks = new ArrayList<>();
    //     String[] taskIDsArray = this.taskIDs.split(",");
    //     for (String taskID : taskIDsArray) {
    //         Task task = taskDb.getTaskByID(taskID);
    //         if (task != null) {
    //             tasks.add(task);
    //         }
    //     }
    //     return tasks;
    // }
}
