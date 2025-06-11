package Models;
abstract class Person {
    private String name;
    private String ID;
    private String email;
    private String password;

    Person(String name, String ID, String email, String password) {
        this.name = name;
        this.ID = ID;
        this.email = email;
        this.password = password; 
    }

    public String getName() {
        return name;
    }   
    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
   abstract public String getinfo();
}
