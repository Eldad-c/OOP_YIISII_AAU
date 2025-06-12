package Database;

import Models.Manager;
import java.util.ArrayList;
import java.io.*;

public class ManagerDatabase {
    private ArrayList<Manager> managerList;
    private static final String FILE_NAME = "./Database/ManagerDatabase.txt";

    public ManagerDatabase() {
        managerList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Manager manager) {
        if (manager != null && getById(manager.getID()) == null) {
            managerList.add(manager);
            saveToFile();
        }
    }

    public Manager getById(String id) {
        for (Manager m : managerList) {
            if (m.getID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public void update(Manager manager) {
        if (manager == null)
            return;
        for (int i = 0; i < managerList.size(); i++) {
            if (managerList.get(i).getID().equals(manager.getID())) {
                managerList.set(i, manager);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        managerList.removeIf(m -> m.getID().equals(id));
        saveToFile();
    }

    public ArrayList<Manager> getAll() {
        return new ArrayList<>(managerList);
    }

    private void saveToFile() {
        try {
            java.io.File dir = new java.io.File("./Database");
            if (!dir.exists()) dir.mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));
            for (Manager m : managerList) {
                // Format: id|name|email|password|managedProjectIds(comma)|managedEmployeeIds(comma)
                pw.print(m.getID() + "|" + m.getName() + "|" + m.getEmail() + "|" + m.getPassword() + "|");
                pw.print(String.join(",", m.getManagedProjectIds()));
                pw.print("|");
                pw.println(String.join(",", m.getManagedEmployeeIds()));
            }
            pw.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        managerList.clear();
        File dir = new File("./Database");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    ArrayList<String> managedProjectIds = new ArrayList<>();
                    if (!parts[4].isEmpty()) {
                        for (String pid : parts[4].split(","))
                            managedProjectIds.add(pid);
                    }
                    ArrayList<String> managedEmployeeIds = new ArrayList<>();
                    if (!parts[5].isEmpty()) {
                        for (String eid : parts[5].split(","))
                            managedEmployeeIds.add(eid);
                    }
                    Manager m = new Manager(name, id, email, password);
                    m.getManagedProjectIds().addAll(managedProjectIds);
                    m.getManagedEmployeeIds().addAll(managedEmployeeIds);
                    managerList.add(m);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
