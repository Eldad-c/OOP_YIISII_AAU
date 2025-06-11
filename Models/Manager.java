package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Manager extends Person {
    private List<String> managedProjectIDs;
    private List<String> managedEmployeeIDs;

    public Manager(List<String> managedProjectIDs, List<String> managedEmployeeIDs,
                   String name, String ID, String email, String password) {
        super(name, ID, email, password);
        this.managedProjectIDs = managedProjectIDs != null ? managedProjectIDs : new ArrayList<>();
        this.managedEmployeeIDs = managedEmployeeIDs != null ? managedEmployeeIDs : new ArrayList<>();
    }

    public List<String> getManagedProjectIDs() {
        return new ArrayList<>(managedProjectIDs);
    }

    public List<String> getManagedEmployeeIDs() {
        return new ArrayList<>(managedEmployeeIDs);
    }

    public void addManagedProjectID(String projectId) {
        if (projectId != null && !managedProjectIDs.contains(projectId)) {
            managedProjectIDs.add(projectId);
        }
    }

    public void removeManagedProjectID(String projectId) {
        managedProjectIDs.remove(projectId);
    }

    public void addManagedEmployeeID(String employeeId) {
        if (employeeId != null && !managedEmployeeIDs.contains(employeeId)) {
            managedEmployeeIDs.add(employeeId);
        }
    }

    public void removeManagedEmployeeID(String employeeId) {
        managedEmployeeIDs.remove(employeeId);
    }

    @Override
    public String getInfo() {
        return "Manager Name: " + getName() +
               ", ID: " + getID() +
               ", Email: " + getEmail() +
               ", Managed Projects: " + managedProjectIDs.size() +
               ", Managed Employees: " + managedEmployeeIDs.size();
    }

    public List<Employee> getManagedEmployees(List<Employee> allEmployees) {
        List<Employee> managedEmployees = new ArrayList<>();
        if (allEmployees == null) {
            return managedEmployees;
        }
        for (String empId : managedEmployeeIDs) {
            for (Employee employee : allEmployees) {
                if (employee.getID().equals(empId) && this.getID().equals(employee.getManagerID())) {
                    managedEmployees.add(employee);
                    break;
                }
            }
        }
        return managedEmployees;
    }

    public List<Task> getTasksForManagedProjects(List<Project> allProjects, List<Task> allTasks) {
        List<Task> tasksInManagedProjects = new ArrayList<>();
        if (allProjects == null || allTasks == null) {
            return tasksInManagedProjects;
        }

        List<Project> actualManagedProjects = new ArrayList<>();
        for (String projId : managedProjectIDs) {
            for (Project project : allProjects) {
                if (project.getID().equals(projId)) {
                    actualManagedProjects.add(project);
                    break;
                }
            }
        }

        for (Project managedProject : actualManagedProjects) {
            for (Task task : allTasks) {
                if (task.getProjectID().equals(managedProject.getID())) {
                    tasksInManagedProjects.add(task);
                }
            }
        }
        return tasksInManagedProjects;
    }

    public Map<String, String> getProjectStatuses(List<Project> allProjects) {
        Map<String, String> projectStatuses = new HashMap<>();
        if (allProjects == null) {
            return projectStatuses;
        }
        for (String projId : managedProjectIDs) {
            for (Project project : allProjects) {
                if (project.getID().equals(projId)) {
                    projectStatuses.put(project.getID(), project.getStatus());
                    break;
                }
            }
        }
        return projectStatuses;
    }

    public void updateTaskStatus(String taskId, String newStatus, List<Task> allTasks, List<Project> allProjects) {
        if (allTasks == null || allProjects == null) {
            System.out.println("Error: Global task or project list is unavailable.");
            return;
        }

        boolean isTaskInManagedProject = false;
        String taskProjectID = null;

        Task targetTask = null;
        for (Task task : allTasks) {
            if (task.getID().equals(taskId)) {
                targetTask = task;
                taskProjectID = task.getProjectID();
                break;
            }
        }

        if (targetTask == null) {
            System.out.println("Error: Task with ID " + taskId + " not found.");
            return;
        }

        for (String managedProjId : managedProjectIDs) {
            if (managedProjId.equals(taskProjectID)) {
                isTaskInManagedProject = true;
                break;
            }
        }

        if (!isTaskInManagedProject) {
            System.out.println("Error: Task " + taskId + " does not belong to a project managed by this manager.");
            return;
        }

        targetTask.changeStatus(newStatus);
        System.out.println("Task " + taskId + " status updated to: " + newStatus);
    }

    public void updateProjectStatus(String projectId, String newStatus, List<Project> allProjects) {
        if (allProjects == null || !managedProjectIDs.contains(projectId)) {
            System.out.println("Error: Project " + projectId + " is not managed by this manager or global project list is unavailable.");
            return;
        }

        for (Project project : allProjects) {
            if (project.getID().equals(projectId)) {
                project.changeStatus(newStatus);
                System.out.println("Project " + projectId + " status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Error: Project " + projectId + " not found in the global list.");
    }

    public void addProject(Project newProject, List<Project> allProjects) {
        if (newProject == null) {
            System.out.println("Error: Cannot add a null project.");
            return;
        }

        if (!managedProjectIDs.contains(newProject.getID())) {
            managedProjectIDs.add(newProject.getID());
        }

        if (allProjects != null && !allProjects.contains(newProject)) {
            allProjects.add(newProject);
            System.out.println("Project '" + newProject.getName() + "' (ID: " + newProject.getID() + ") added to manager's list and overall organization.");
        } else {
            System.out.println("Project '" + newProject.getName() + "' (ID: " + newProject.getID() + ") already exists in the organization or global list is null.");
        }
    }

    public void removeProject(String projectId, List<Project> allProjects) {
        boolean removedFromManaged = managedProjectIDs.remove(projectId);

        boolean removedFromGlobal = false;
        if (allProjects != null) {
            removedFromGlobal = allProjects.removeIf(p -> p.getID().equals(projectId));
        }

        if (removedFromManaged || removedFromGlobal) {
            System.out.println("Project with ID " + projectId + " successfully removed.");
        } else {
            System.out.println("Failed to remove project with ID " + projectId + ". Project not found in managed list or global list.");
        }
    }
}