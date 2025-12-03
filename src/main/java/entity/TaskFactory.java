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
        if (objectId != null) {
            task.setIdx(objectId.toString());
        }

        Object usersObj = taskDoc.get("users");
        if (usersObj instanceof List) {
            List<?> usersList = (List<?>) usersObj;
            for (Object user : usersList) {
                if (user != null) {
                    task.assignUser(user.toString());
                }
            }
        }
        return task;
    }
}
