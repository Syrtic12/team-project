package use_case.logged_in;

import entity.Task;

import java.util.List;

public class LoggedInOutputData {
    private final List<Task> taskList;
    public LoggedInOutputData(List<Task> taskList) {
        this.taskList = taskList;
    }
    public List<Task> getTaskList() {
        return taskList;
    }
}
