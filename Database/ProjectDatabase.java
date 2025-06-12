package Database;

import Models.Project;
import java.util.ArrayList;
import java.io.*;

public class ProjectDatabase {
    private ArrayList<Project> projectList;
    private static final String FILE_NAME = "ProjectDatabase.txt";

    public ProjectDatabase() {
        projectList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Project project) {
        if (project != null && getById(project.getID()) == null) {
            projectList.add(project);
            saveToFile();
        }
    }

    public Project getById(String id) {
        for (Project p : projectList) {
            if (p.getID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void update(Project project) {
        if (project == null)
            return;
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getID().equals(project.getID())) {
                projectList.set(i, project);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        projectList.removeIf(p -> p.getID().equals(id));
        saveToFile();
    }

    public ArrayList<Project> getAll() {
        return new ArrayList<>(projectList);
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Project p : projectList) {
                // Format: id|name|descriptionLink|startDate|endDate|status|managerID|taskIDs(comma)
                pw.print(p.getID() + "|" + p.getName() + "|" + p.getDescriptionLink() + "|" + p.getStartDate() + "|" + p.getEndDate() + "|" + p.getStatus() + "|" + p.getManagerID() + "|");
                pw.println(String.join(",", p.getTaskIDs()));
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        projectList.clear();
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8) {
                    String id = parts[0];
                    String name = parts[1];
                    String descriptionLink = parts[2];
                    String startDate = parts[3];
                    String endDate = parts[4];
                    String status = parts[5];
                    String managerID = parts[6];
                    ArrayList<String> taskIDs = new ArrayList<>();
                    if (!parts[7].isEmpty()) {
                        for (String tid : parts[7].split(","))
                            taskIDs.add(tid);
                    }
                    Project p = new Project(id, name, descriptionLink, startDate, endDate, status, managerID);
                    p.setTaskIDs(taskIDs);
                    projectList.add(p);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
