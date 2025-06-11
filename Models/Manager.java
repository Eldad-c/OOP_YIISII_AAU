package Models;

import Database.EmployeeDatabase;
import Database.ProjectDatabase;
import Database.TaskDatabase;
import java.util.ArrayList;

public class Manager extends Person {
    private ArrayList<String> managedProjectIds;
    private ArrayList<String> managedEmployeeIds;

    public Manager(String name, String ID, String email, String password) {
        super(name, ID, email, password);
        this.managedProjectIds = new ArrayList<>();
        this.managedEmployeeIds = new ArrayList<>();
    }

    public ArrayList<String> getManagedProjectIds() {
        return managedProjectIds;
    }
    public ArrayList<String> getManagedEmployeeIds() {
        return managedEmployeeIds;
    }
    public void addManagedProjectId(String projectId) {
        if (projectId != null && !managedProjectIds.contains(projectId)) {
            managedProjectIds.add(projectId);
        }
    }
    public void removeManagedProjectId(String projectId) {
        managedProjectIds.remove(projectId);
    }
    public void addManagedEmployeeId(String employeeId) {
        if (employeeId != null && !managedEmployeeIds.contains(employeeId)) {
            managedEmployeeIds.add(employeeId);
        }
    }
    public void removeManagedEmployeeId(String employeeId) {
        managedEmployeeIds.remove(employeeId);
    }
    @Override
    public String getInfo() {
        return "Manager Name: " + getName() + ", ID: " + getID() + ", Email: " + getEmail() +
               ", Managed Projects: " + managedProjectIds.size() + ", Managed Employees: " + managedEmployeeIds.size();
    }
    // Retrieves actual Employee objects directly managed by this manager
    public ArrayList<Employee> getManagedEmployees(EmployeeDatabase empDb) {
        ArrayList<Employee> managed = new ArrayList<>();
        for (String empId : managedEmployeeIds) {
            Employee e = empDb.getById(empId);
            if (e != null) managed.add(e);
        }
        return managed;
    }
    // Gathers tasks related to projects they manage
    public ArrayList<Task> getTasksForManagedProjects(ProjectDatabase projectDb, TaskDatabase taskDb) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (String projId : managedProjectIds) {
            Project p = projectDb.getById(projId);
            if (p != null) {
                for (String taskId : p.getTaskIDs()) {
                    Task t = taskDb.getById(taskId);
                    if (t != null) tasks.add(t);
                }
            }
        }
        return tasks;
    }
    // Provides status summaries for projects this manager oversees
    public ArrayList<String> getProjectStatuses(ProjectDatabase projectDb) {
        ArrayList<String> statuses = new ArrayList<>();
        for (String projId : managedProjectIds) {
            Project p = projectDb.getById(projId);
            if (p != null) statuses.add(p.getStatus());
        }
        return statuses;
    }
    // Enables managers to update the status of tasks within their managed projects
    public void updateTaskStatus(String taskId, String newStatus, TaskDatabase taskDb) {
        for (Task t : getTasksForManagedProjects(null, taskDb)) {
            if (t.getID().equals(taskId)) {
                t.changeStatus(newStatus);
                break;
            }
        }
    }
    // Allows managers to change the status of projects they manage
    public void updateProjectStatus(String projectId, String newStatus, ProjectDatabase projectDb) {
        if (managedProjectIds.contains(projectId)) {
            Project p = projectDb.getById(projectId);
            if (p != null) p.changeStatus(newStatus);
        }
    }
    // Allows the manager to add a new project
    public void addProject(Project newProject, ProjectDatabase projectDb) {
        if (newProject != null && !managedProjectIds.contains(newProject.getID())) {
            managedProjectIds.add(newProject.getID());
            projectDb.add(newProject);
        }
    }
    // Allows the manager to remove a project
    public void removeProject(String projectId, ProjectDatabase projectDb) {
        managedProjectIds.remove(projectId);
        projectDb.delete(projectId);
    }
}