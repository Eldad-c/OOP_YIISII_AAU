package Models;

import java.util.Date;

public class Report {
    String ID;
    Date dateGenerated;
    String content;
    String generatedByUserID;

    Report(String ID, Date dateGenerated, String content, String generatedByUserID) {
        this.ID = ID;
        this.dateGenerated = dateGenerated;
        this.content = content;
        this.generatedByUserID = generatedByUserID;
    }

    public void displayReport() {
        System.out.println(content);
    }
    public String getID() {
        return ID;
    }
    public Date getDateGenerated() {
        return dateGenerated;
    }
    public String getContent() {
        return content;
    }
    public String getGeneratedByUserID() {
        return generatedByUserID;
    }
}