package Database;

import Models.Manager;
import java.util.ArrayList;

public class ManagerDatabase {
    private ArrayList<Manager> managerList;

    public ManagerDatabase() {
        managerList = new ArrayList<>();
    }

    public void add(Manager manager) {
        if (manager != null && getById(manager.getID()) == null) {
            managerList.add(manager);
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
        if (manager == null) return;
        for (int i = 0; i < managerList.size(); i++) {
            if (managerList.get(i).getID().equals(manager.getID())) {
                managerList.set(i, manager);
                return;
            }
        }
    }

    public void delete(String id) {
        managerList.removeIf(m -> m.getID().equals(id));
    }

    public ArrayList<Manager> getAll() {
        return new ArrayList<>(managerList);
    }
}
