package use_case.create_task;

public class CreateTaskInputData {
    private final String teamId;
    private final String invokedBy; // user id
    private final String title;
    private final String description;
    private final Integer status;

    public CreateTaskInputData(String teamId, String invokedBy, Integer status, String description, String title) {
        this.teamId = teamId;
        this.invokedBy = invokedBy;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public String getTeamId() { return teamId; }
    public String getInvokedBy() { return invokedBy; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getStatus() { return status; }
}
