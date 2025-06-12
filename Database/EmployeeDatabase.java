package Database;

import Models.Employee;
import java.util.ArrayList;
import java.io.*;

public class EmployeeDatabase {
    private ArrayList<Employee> employeeList;
    private static final String FILE_NAME = "./Database/EmployeeDatabase.txt";

    public EmployeeDatabase() {
        employeeList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Employee employee) {
        if (employee != null && getById(employee.getID()) == null) {
            employeeList.add(employee);
            saveToFile();
        }
    }

    public Employee getById(String id) {
        for (Employee e : employeeList) {
            if (e.getID().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public void update(Employee employee) {
        if (employee == null) return;
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getID().equals(employee.getID())) {
                employeeList.set(i, employee);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        employeeList.removeIf(e -> e.getID().equals(id));
        saveToFile();
    }

    public ArrayList<Employee> getAll() {
        return new ArrayList<>(employeeList);
    }

    private void saveToFile() {
        try {
            java.io.File dir = new java.io.File("./Database");
            if (!dir.exists()) dir.mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));
            for (Employee e : employeeList) {
                // Format: id|name|email|password|managerID|assignedTaskIds(comma)|projectIds(comma)
                pw.print(e.getID() + "|" + e.getName() + "|" + e.getEmail() + "|" + e.getPassword() + "|" + e.getManagerID() + "|");
                pw.print(String.join(",", e.getAssignedTaskIds()));
                pw.print("|");
                pw.println(String.join(",", e.getProjectIds()));
            }
            pw.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        employeeList.clear();
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
                    String managerID = parts[4];
                    ArrayList<String> assignedTaskIds = new ArrayList<>();
                    if (!parts[5].isEmpty()) {
                        for (String tid : parts[5].split(",")) assignedTaskIds.add(tid);
                    }
                    ArrayList<String> projectIds = new ArrayList<>();
                    if (!parts[6].isEmpty()) {
                        for (String pid : parts[6].split(",")) projectIds.add(pid);
                    }
                    Employee e = new Employee(managerID, name, id, email, password);
                    e.setAssignedTaskIds(assignedTaskIds);
                    e.setProjectIds(projectIds);
                    employeeList.add(e);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
