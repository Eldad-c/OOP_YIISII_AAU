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
