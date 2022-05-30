package tms.persistance;

import tms.model.Task;
import tms.model.User;

public interface iTaskRepository extends Repository<Integer, Task>{
    Iterable<Task> findAllTasksOfUser(User user);
    Iterable<Task> findAllTasksOfCreator(User user);
    Task addUserToTask(User user);
}
