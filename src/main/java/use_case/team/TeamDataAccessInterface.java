package use_case.team;

import entity.Task;

public interface TeamDataAccessInterface {

    public Task getTask(String taskID);

}
