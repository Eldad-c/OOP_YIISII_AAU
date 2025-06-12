package Database;

import Models.Task;
import java.util.ArrayList;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TaskDatabase {
    private ArrayList<Task> taskList;
    private static final String FILE_NAME = "TaskDatabase.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public TaskDatabase() {
        taskList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Task task) {
        if (task != null && getById(task.getID()) == null) {
            taskList.add(task);
            saveToFile();
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
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        taskList.removeIf(t -> t.getID().equals(id));
        saveToFile();
    }

    public ArrayList<Task> getAll() {
        return new ArrayList<>(taskList);
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : taskList) {
                // Format: id|descriptionLink|assignedUserID|status|dueDate|projectID
                String dueDateStr = t.getDueDate() != null ? sdf.format(t.getDueDate()) : "";
                pw.println(t.getID() + "|" + t.getDescriptionLink() + "|" + t.getAssignedUserID() + "|" + t.getStatus() + "|" + dueDateStr + "|" + t.getProjectID());
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        taskList.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String descriptionLink = parts[1];
                    String assignedUserID = parts[2];
                    String status = parts[3];
                    String dueDateStr = parts[4];
                    String projectID = parts[5];
                    java.util.Date dueDate = null;
                    if (!dueDateStr.isEmpty()) {
                        try { dueDate = sdf.parse(dueDateStr); } catch (Exception e) { dueDate = null; }
                    }
                    Task t = new Task(id, descriptionLink, assignedUserID, status, dueDate, projectID);
                    taskList.add(t);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
