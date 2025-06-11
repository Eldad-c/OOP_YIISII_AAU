# Project Management System

## I. Core Classes and Attributes

### Person (Abstract Base Class)

* `id`: String (Unique identifier for any individual in the system.)
* `name`: String
* `email`: String
* `password`: String (For secure user authentication.)

### Employee (Extends Person)

* `managerId`: String (The ID of their direct manager.)
* `assignedTaskIds`: List<String> (IDs of specific tasks assigned to this employee.)
* `projectIds`: List<String> (IDs of projects this employee is actively involved in.)

### Manager (Extends Person)

* `managedProjectIds`: List<String> (IDs of projects directly overseen by this manager.)
* `managedEmployeeIds`: List<String> (IDs of employees directly managed by this manager.)

### Executive (Extends Person)

* `organizationManagersList`: List<String> (IDs of all managers within the organization, for high-level oversight.)
* `organizationProjectsList`: List<String> (IDs of all projects within the organization.)
* `organizationEmployeesList`: List<String> (IDs of all employees within the organization.)

### Project

* `id`: String
* `name`: String
* `descriptionLink`: String (Optional URL to a detailed project description.)
* `startDate`: Date
* `endDate`: Date
* `taskIds`: List<String> (IDs of all tasks associated with this project.)
* `status`: String (e.g., "STARTED", "IN_PROGRESS", "COMPLETED", "ON_HOLD", "CANCELLED". Note: Relies on consistent string literals.)
* `managerId`: String (The ID of the manager responsible for this project.)

### Task

* `id`: String
* `descriptionLink`: String (Optional URL to a detailed task description.)
* `assignedUserId`: String (The ID of the employee assigned to this task.)
* `status`: String (e.g., "PENDING", "IN_PROGRESS", "COMPLETED", "BLOCKED". Note: Relies on consistent string literals.)
* `dueDate`: Date
* `projectId`: String (The ID of the project this task belongs to.)

### Report

* `id`: String
* `dateGenerated`: Date
* `content`: String (or a more complex data structure representing the report details.)
* `generatedByUserId`: String (The ID of the Executive who initiated the report generation.)

---

## II. Key Methods and Functionality

This section details the actions each class can perform, with an emphasis on logical data access. Many methods will interact with conceptual "Database" classes to retrieve actual objects from their IDs.

### Person

* `abstract String getInfo()`: Returns basic identity information (ID, name, email). (Abstract, as specific role implementations will vary.)

### Employee

* `String getInfo()`: Overrides Person.getInfo(), including employee-specific details.
* `List<Task> getAssignedTasks(TaskDatabase taskDb)`: Retrieves actual Task objects assigned to this employee by consulting a TaskDatabase.
* `Map<String, String> getProjectStatuses(ProjectDatabase projectDb)`: Fetches status summaries for projects they are involved in.
* `void updateTaskStatus(String taskId, String newStatus, TaskDatabase taskDb)`: Allows the employee to update the status of a task assigned to them. (Status is now String)

### Manager

* `String getInfo()`: Overrides Person.getInfo(), including manager-specific details.
* `List<Employee> getManagedEmployees(EmployeeDatabase empDb)`: Retrieves actual Employee objects directly managed by this manager.
* `List<Task> getTasksForManagedProjects(ProjectDatabase projectDb, TaskDatabase taskDb)`: Clarification: Managers don't own tasks directly; they oversee tasks within their managed projects. This method will query ProjectDatabase and TaskDatabase to gather tasks related to projects they manage.
* `Map<String, String> getProjectStatuses(ProjectDatabase projectDb)`: Provides status summaries for projects this manager oversees.
* `void updateTaskStatus(String taskId, String newStatus, TaskDatabase taskDb)`: Enables managers to update the status of tasks within their managed projects. (Status is now String)
* `void updateProjectStatus(String projectId, String newStatus, ProjectDatabase projectDb)`: Allows managers to change the status of projects they manage. (Status is now String)

### Executive

* `String getInfo()`: Overrides Person.getInfo(), including executive-specific details.
* `List<Manager> getAllManagers(ManagerDatabase managerDb)`: Retrieves all manager objects for a comprehensive view.
* `List<Project> getAllProjects(ProjectDatabase projectDb)`: Retrieves all project objects in the organization.
* `List<Employee> getAllEmployees(EmployeeDatabase empDb)`: Retrieves all employee objects in the organization.
* `Report generateReport(String reportType, AnyDatabase... dbs)`: Creates various reports (e.g., project progress, employee workload) by querying relevant "Database" classes.
* `void viewSystemOverview()`: Provides a high-level summary of organizational metrics.

### Project

* `void addTask(Task newTask, TaskDatabase taskDb)`: Adds a new task to this project and persists it in the TaskDatabase.
* `void removeTask(String taskId, TaskDatabase taskDb)`: Removes a task by its ID from the project and the database.
* `List<Task> getTasks(TaskDatabase taskDb)`: Retrieves all Task objects associated with this specific project.
* `String getStatus()`: Returns the current status of the project. (Status is now String)
* `void changeStatus(String newStatus)`: Updates the project's status. (Status is now String)

### Task

* `void assignUser(String userId)`: Assigns an employee (by ID) to this task.
* `void changeStatus(String newStatus)`: Updates the task's status. (Status is now String)
* `boolean isOverdue()`: Determines if the task's dueDate has passed.

### Report

* `void displayReport()`: Presents the generated report content.

---

## III. Data Management Layer (Conceptual "Databases")

To enable efficient retrieval and management of objects (e.g., getting an Employee object from just an id), we will conceptually utilize "Database" classes. In a simple application, these might be `ArrayList` or `HashMap` collections managed centrally.

* `EmployeeDatabase`: Manages the collection of all Employee objects.
* `ManagerDatabase`: Manages the collection of all Manager objects.
* `ProjectDatabase`: Manages the collection of all Project objects.
* `TaskDatabase`: Manages the collection of all Task objects.

These "database" classes would typically offer methods like `getById(String id)`, `add(Object obj)`, `update(Object obj)`, `delete(String id)`, and `getAll()`.

---

## IV. GUI / User Experience (UX) Roadmap

This section outlines the planned user interaction flow and the display for each user role.

### Front Page: LOG IN

* **User Input**: Prompt for Id and Password.
* **Role Selection**: Allow users to select their role (Employee, Manager, or Executive) via radio buttons or a dropdown.
* **Authentication**: Verify the entered Id and Password against the respective `EmployeeDatabase`, `ManagerDatabase`, or `ExecutiveDatabase`.
* **Navigation**: Upon successful authentication, redirect the user to their specific role-based dashboard.

### If Manager Dashboard:

* **Display**:
    * Overview of `managedEmployees` with their current task statuses.
    * Overview of `managedProjects` with their current project statuses.
* **Actions**:
    * Buttons/Inputs to "Update Task Status" (requires `taskId`, `newStatus` string).
    * Buttons/Inputs to "Update Project Status" (requires `projectId`, `newStatus` string).
    * Option to "Assign Task" to an employee under their management.
    * Option to "Add New Task" to a project they manage.

### If Employee Dashboard:

* **Display**:
    * List of `assignedTasks` showing status (string) and `dueDate`.
    * List of projects they are involved in, along with their status (string).
* **Actions**:
    * Button/Input to "Update My Task Status" (requires `taskId`, `newStatus` string).

### If Executive Dashboard:

* **Display**:
    * High-level system overview (e.g., total employees, projects, managers).
    * Summaries of overall project and task statuses.
    * Lists (potentially filterable/sortable) of:
        * All Managers in the organization.
        * All Projects in the organization.
        * All Employees in the organization.
* **Actions**:
    * Button to "Generate Report" (potentially offering different report types).
