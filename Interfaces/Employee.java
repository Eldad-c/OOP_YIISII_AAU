
package Interfaces;

import java.util.List;

public interface Employee {
    // Display methods
    List<String> getAssignedTasks(); // Retrieve list of assigned tasks with status and due date
    List<String> getProjects(); // Retrieve list of projects with status

    // Action methods
    void updateTaskStatus(String taskId, String newStatus); // Update the status of a task

    // Button action methods
    void onUpdateTaskStatusButtonClick(String taskId, String newStatus); // Action for updating task status
}
