package dataaccess;

import entity.Task;
import entity.TaskFactory;
import entity.User;
import entity.UserFactory;
import entity.Team;
import entity.TeamFactory;
import usecase.assign_task.AssignTaskDataAccessInterface;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) for the "Assign Task" use case.
 * Provides methods to retrieve tasks, users, and teams from the database,
 * check user assignment, and assign users to tasks.
 */
public class AssignTaskDataAccessObject implements AssignTaskDataAccessInterface {
    private static final String TASKS = "tasks";
    private static final String USERS = "users";
    private static final String ID = "_id";
    private static final String TEAMS = "teams";

    private final KandoMongoDatabase generalDataAccessObject;
    private final TaskFactory taskFactory = new TaskFactory();
    private final UserFactory userFactory = new UserFactory();
    private final TeamFactory teamFactory = new TeamFactory();

    public AssignTaskDataAccessObject(KandoMongoDatabase dao) {
        this.generalDataAccessObject = dao;
    }

    @Override
    public Task getTask(String taskIdx) {
        final Document taskDocument = generalDataAccessObject.getOne(TASKS, ID, taskIdx);
        Task task = null;

        if (taskDocument != null) {
            task = taskFactory.createFromDocument(taskDocument);
        }

        return task;
    }

    @Override
    public User getUser(String userIdx) {
        final Document userDocument = generalDataAccessObject.getOne(USERS, ID, userIdx);
        User user = null;

        if (userDocument != null) {
            user = userFactory.createFromDocument(userDocument);
        }

        return user;
    }

    @Override
    public Team getTeamByTask(String taskIdx) {
        final List<Document> allTeams = generalDataAccessObject.getAll(TEAMS);
        Team foundTeam = null;

        for (Document teamDocument : allTeams) {
            final Object tasksObj = teamDocument.get(TASKS);

            if (tasksObj instanceof List) {
                final List<?> tasksList = (List<?>) tasksObj;
                for (Object obj : tasksList) {
                    if (obj instanceof String) {
                        final String taskId = (String) obj;
                        if (taskId.equals(taskIdx)) {
                            foundTeam = teamFactory.createFromDocument(teamDocument);
                            break;
                        }
                    }
                }
            }
            else if (tasksObj instanceof String) {
                final String taskId = (String) tasksObj;
                if (taskId.equals(taskIdx)) {
                    foundTeam = teamFactory.createFromDocument(teamDocument);
                }
            }

            if (foundTeam != null) {
                break;
            }
        }

        return foundTeam;
    }

    @Override
    public boolean isUserAssignedToTask(String taskIdx, String userIdx) {
        final Task task = getTask(taskIdx);
        boolean assigned = false;

        if (task == null) {
            assigned = task.isUserAssigned(userIdx);
        }
        return assigned;
    }

    @Override
    public void assignUserToTask(String taskIdx, String userIdx) {
        final Document taskDocument = generalDataAccessObject.getOne(TASKS, ID, taskIdx);

        if (taskDocument == null) {
            List<String> users = taskDocument.getList(USERS, String.class);
            if (users == null) {
                users = new ArrayList<>();
            }

            if (!users.contains(userIdx)) {
                users.add(userIdx);
                generalDataAccessObject.update(TASKS, taskIdx, USERS, users);
            }
        }
    }
}

