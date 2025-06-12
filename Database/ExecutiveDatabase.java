package Database;

import Models.Executive;
import java.util.ArrayList;
import java.io.*;

public class ExecutiveDatabase {
    private ArrayList<Executive> executiveList;
    private static final String FILE_NAME = "./Database/ExecutiveDatabase.txt";

    public ExecutiveDatabase() {
        executiveList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Executive executive) {
        if (executive != null && getById(executive.getID()) == null) {
            executiveList.add(executive);
            saveToFile();
        }
    }

    public Executive getById(String id) {
        for (Executive e : executiveList) {
            if (e.getID().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public void update(Executive executive) {
        if (executive == null)
            return;
        for (int i = 0; i < executiveList.size(); i++) {
            if (executiveList.get(i).getID().equals(executive.getID())) {
                executiveList.set(i, executive);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        executiveList.removeIf(e -> e.getID().equals(id));
        saveToFile();
    }

    public ArrayList<Executive> getAll() {
        return new ArrayList<>(executiveList);
    }

    private void saveToFile() {
        try {
            java.io.File dir = new java.io.File("./Database");
            if (!dir.exists()) dir.mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));
            for (Executive e : executiveList) {
                // Format: id|name|email|password|organizationManagersList(comma)|organizationProjectsList(comma)|organizationEmployeesList(comma)
                pw.print(e.getID() + "|" + e.getName() + "|" + e.getEmail() + "|" + e.getPassword() + "|");
                pw.print(String.join(",", e.getOrganizationManagersList()));
                pw.print("|");
                pw.print(String.join(",", e.getOrganizationProjectsList()));
                pw.print("|");
                pw.println(String.join(",", e.getOrganizationEmployeesList()));
            }
            pw.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        executiveList.clear();
        File dir = new File("./Database");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    ArrayList<String> managers = new ArrayList<>();
                    if (!parts[4].isEmpty()) {
                        for (String mid : parts[4].split(","))
                            managers.add(mid);
                    }
                    ArrayList<String> projects = new ArrayList<>();
                    if (!parts[5].isEmpty()) {
                        for (String pid : parts[5].split(","))
                            projects.add(pid);
                    }
                    ArrayList<String> employees = new ArrayList<>();
                    if (!parts[6].isEmpty()) {
                        for (String eid : parts[6].split(","))
                            employees.add(eid);
                    }
                    Executive exec = new Executive(name, id, email, password);
                    exec.getOrganizationManagersList().addAll(managers);
                    exec.getOrganizationProjectsList().addAll(projects);
                    exec.getOrganizationEmployeesList().addAll(employees);
                    executiveList.add(exec);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
