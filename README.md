I. Classes and attributes to write in:
- Person: Id, name, email
- Employee extends Person: managerId, projectIds, assignedTasks
- Manager extends Person: projectIds, employeeIds
- Executive extends Person: managersList, projectsList, employeesList
- Project: Id, name, descriptionLink(optional), startDate, endDate, tasksList, status, managerId
- Task: Id, descriptionLink(optional), assignedUserId, status, dueDate, projectId
- Report: (?)

II. Methods:
- Person: getInfo()
- Employee: getTasks(), getProjectStatus(), updateTaskStatus(), getInfo()
- Manager: getEmployees(), getTasks(), getProjectStatus(), updateTaskStatus(), updateProjectStatus(), getInfo()
- Executive: getManagersList(), getProjectsList(), getEmployeesList(), generateReport(), getInfo()
- Project: addTask(), removeTask(), getTasks(), getStatus(), changeStatus()
- Task: assignUser(), changeStatus(), isOverdue()
- Report: (?)

III. GUI UX roadmap:
- Front page: Select role: Employee, Manager, or Executive -> Enter Id, name, and email (should we add password here?) -> If match, continue
- If Manager: Enter Id and name -> If matching: show current employees status, projects status, and button if manager wants to change -> If want to change: ask input in specified format -> If change matching: change
- If Employee: Enter Id and name -> If matching: show current project status, tasks status, and button if employee wants to change tasks status -> If want to change: ask input in specified format -> If change matching: change
- If Executive: Enter Id and name -> If matching: show a window of current projects, current employees, and current status 
