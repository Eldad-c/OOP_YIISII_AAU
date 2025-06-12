package Database;

import Models.Person;
import java.util.ArrayList;
import java.io.*;

public class PersonDatabase {
    private ArrayList<Person> personList;
    private static final String FILE_NAME = "./Database/PersonDatabase.txt";

    public PersonDatabase() {
        personList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Person person) {
        if (person != null && getById(person.getID()) == null) {
            personList.add(person);
            saveToFile();
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
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        personList.removeIf(p -> p.getID().equals(id));
        saveToFile();
    }

    public ArrayList<Person> getAll() {
        return new ArrayList<>(personList);
    }

    // Saves all Person objects to a file in the project root directory
    private void saveToFile() {
        try {
            java.io.File dir = new java.io.File("./Database");
            if (!dir.exists()) dir.mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));
            for (Person p : personList) {
                // Format: id|name|email|password|className
                pw.println(p.getID() + "|" + p.getName() + "|" + p.getEmail() + "|" + p.getPassword() + "|" + p.getClass().getSimpleName());
            }
            pw.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Loads Person objects from a file in the project root directory
    private void loadFromFile() {
        personList.clear();
        File dir = new File("./Database");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String className = parts[4];
                    // Only loads as Person (abstract), so you may want to extend this for Employee/Manager/Executive
                    personList.add(new Models.Person(name, id, email, password) {
                        @Override
                        public String getInfo() {
                            return "Person Name: " + getName() + ", ID: " + getID() + ", Email: " + getEmail();
                        }
                    });
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
