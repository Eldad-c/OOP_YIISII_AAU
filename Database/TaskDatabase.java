package Database;

import Models.Task;
import java.util.ArrayList;

public class TaskDatabase {
    private ArrayList<Task> taskList;

    public TaskDatabase() {
        taskList = new ArrayList<>();
    }

    public void add(Task task) {
        if (task != null && getById(task.getID()) == null) {
            taskList.add(task);
        }
    }

    public Task getById(String id) {
        for (Task t : taskList) {
            if (t.getID().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public void update(Task task) {
        if (task == null) return;
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getID().equals(task.getID())) {
                taskList.set(i, task);
                return;
            }
        }
    }

    public void delete(String id) {
        taskList.removeIf(t -> t.getID().equals(id));
    }

    public ArrayList<Task> getAll() {
        return new ArrayList<>(taskList);
    }
}
