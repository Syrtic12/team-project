package usecase.edit_task;

import entity.Task;

public class EditTaskOutputData {
    private final boolean success;
    private final String message;
    private final Task editedTask;
    private final Integer oldStatus;

    public EditTaskOutputData(boolean success, String message, Task editedTask, Integer oldStatus) {
        this.success = success;
        this.message = message;
        this.editedTask = editedTask;
        this.oldStatus = oldStatus;
    }

    /**
     * @return the old status of the task before editing
     */
    public Integer getOldStatus() { return oldStatus; }


    /**
     * @return the edited task if the edit was successful, null otherwise
     */
    public Task getEditedTask() { return editedTask; }

    /**
     * @return the edited task if the edit was successful, null otherwise
     */
    public boolean isSuccess() { return success; }


    /**
     * @return the message associated with the edit operation
     */
    public String getMessage() { return message; }
}