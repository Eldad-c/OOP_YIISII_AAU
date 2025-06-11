package Database;

import Models.Employee;
import java.util.ArrayList;

public class EmployeeDatabase {
    private ArrayList<Employee> employeeList;

    public EmployeeDatabase() {
        employeeList = new ArrayList<>();
    }

    public void add(Employee employee) {
        if (employee != null && getById(employee.getID()) == null) {
            employeeList.add(employee);
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
                return;
            }
        }
    }

    public void delete(String id) {
        employeeList.removeIf(e -> e.getID().equals(id));
    }

    public ArrayList<Employee> getAll() {
        return new ArrayList<>(employeeList);
    }
}
