package Models;

import java.util.ArrayList;

public class Employee extends Person {
    private String managerID;
    private ArrayList<String> assignedTaskIds;
    private ArrayList<String> projectIds;

    public Employee(String managerID, String name, String ID, String email, String password) {
        super(name, ID, email, password);
        this.managerID = managerID;
        this.assignedTaskIds = new ArrayList<>();
        this.projectIds = new ArrayList<>();
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public ArrayList<String> getAssignedTaskIds() {
        return assignedTaskIds;
    }

    public void setAssignedTaskIds(ArrayList<String> assignedTaskIds) {
        this.assignedTaskIds = assignedTaskIds;
    }

    public ArrayList<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(ArrayList<String> projectIds) {
        this.projectIds = projectIds;
    }

    // Retrieves actual Task objects assigned to this employee by consulting a
    // TaskDatabase
    public ArrayList<Models.Task> getAssignedTasks(Database.TaskDatabase taskDb) {
        ArrayList<Models.Task> tasks = new ArrayList<>();
        for (String taskId : assignedTaskIds) {
            Models.Task t = taskDb.getById(taskId);
            if (t != null)
                tasks.add(t);
        }
        return tasks;
    }

    // Fetches status summaries for projects they are involved in
    public ArrayList<String> getProjectStatuses(Database.ProjectDatabase projectDb) {
        ArrayList<String> statuses = new ArrayList<>();
        for (String projectId : projectIds) {
            Models.Project p = projectDb.getById(projectId);
            if (p != null)
                statuses.add(p.getStatus());
        }
        return statuses;
    }

    // Allows the employee to update the status of a task assigned to them
    public void updateTaskStatus(String taskId, String newStatus, Database.TaskDatabase taskDb) {
        if (assignedTaskIds.contains(taskId)) {
            Models.Task t = taskDb.getById(taskId);
            if (t != null)
                t.changeStatus(newStatus);
        }
    }

    @Override
    public String getInfo() {
        return "Employee Name: " + getName() + ", ID: " + getID() + ", Email: " + getEmail() +
                ", Manager ID: " + managerID;
    }

}
