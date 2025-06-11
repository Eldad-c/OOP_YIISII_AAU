package Interfaces;
import java.util.List;
public interface Executive {
    List<String> getAllManagers(); // Retrieve all managers
    List<String> getAllEmployees(); // Retrieve all employees
    List<String> getProjectSummary(); // Summarize overall project and task status
    void generateReport(String reportType); // Generate a report
    void deleteAccount(String userId); // Delete a user account
    void addProjectToOrganization(String projectId); // Add a project to the organization
    void removeProjectFromOrganization(String projectId); // Remove a project from the organization
    
    // Button actions
    void onGenerateReportButtonClick(String reportType); // Action for Generate Report button
    void onDeleteAccountButtonClick(String userId); // Action for Delete Account button
    void onAddProjectButtonClick(String projectId); // Action for Add Project button
    void onRemoveProjectButtonClick(String projectId); // Action for Remove Project button
}

