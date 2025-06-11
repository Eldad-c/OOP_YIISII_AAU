package Database;

import Models.Project;
import java.util.ArrayList;

public class ProjectDatabase {
    private ArrayList<Project> projectList;

    public ProjectDatabase() {
        projectList = new ArrayList<>();
    }

    public void add(Project project) {
        if (project != null && getById(project.getID()) == null) {
            projectList.add(project);
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
                return;
            }
        }
    }

    public void delete(String id) {
        projectList.removeIf(p -> p.getID().equals(id));
    }

    public ArrayList<Project> getAll() {
        return new ArrayList<>(projectList);
    }
}
