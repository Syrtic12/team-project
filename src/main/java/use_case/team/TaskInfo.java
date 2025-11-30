package use_case.team;

public class TaskInfo {
    private String id;
    private String assignedUsers;
    private String title;
    private String description;

    public TaskInfo(String id, String title, String description, String assignedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedUsers = assignedUsers;
    }
    public String getId() {
        return id;
    }
    public String getAssignedUsers (){
        return assignedUsers;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAssignedUsers(String assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    @Override
    public String toString() {
        return title;
    }
}