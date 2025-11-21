package entity;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public class TaskFactory {

    public Task create(String title, String description, Integer status) {
        return new Task(title, description, status);
    }

    public Task createFromDocument(Document taskDoc) {
        String title = taskDoc.getString("title");
        String description = taskDoc.getString("description");
        Integer status = taskDoc.getInteger("status");
        Task task = new Task(title, description, status);

        ObjectId objectId = taskDoc.getObjectId("_id");

        List<String> users =  taskDoc.getList("users", String.class);
        if (users != null) {
            for (String user : users) {
                task.assignUser(user);
            }
        }
        return task;
    }
}
