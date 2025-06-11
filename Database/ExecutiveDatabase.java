package Database;

import Models.Executive;
import java.util.ArrayList;

public class ExecutiveDatabase {
    private ArrayList<Executive> executiveList;

    public ExecutiveDatabase() {
        executiveList = new ArrayList<>();
    }

    public void add(Executive executive) {
        if (executive != null && getById(executive.getID()) == null) {
            executiveList.add(executive);
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
                return;
            }
        }
    }

    public void delete(String id) {
        executiveList.removeIf(e -> e.getID().equals(id));
    }

    public ArrayList<Executive> getAll() {
        return new ArrayList<>(executiveList);
    }
}
