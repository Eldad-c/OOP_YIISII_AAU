package Database;

import Models.Person;
import java.util.ArrayList;

public class PersonDatabase {
    private ArrayList<Person> personList;

    public PersonDatabase() {
        personList = new ArrayList<>();
    }

    public void add(Person person) {
        if (person != null && getById(person.getID()) == null) {
            personList.add(person);
        }
    }

    public Person getById(String id) {
        for (Person p : personList) {
            if (p.getID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void update(Person person) {
        if (person == null)
            return;
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getID().equals(person.getID())) {
                personList.set(i, person);
                return;
            }
        }
    }

    public void delete(String id) {
        personList.removeIf(p -> p.getID().equals(id));
    }

    public ArrayList<Person> getAll() {
        return new ArrayList<>(personList);
    }
}
