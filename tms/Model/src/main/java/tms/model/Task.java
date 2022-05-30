package tms.model;

import java.util.Objects;

public class Task extends Entity<Integer> {
    String name, description;
    User creator;
    Status status;

    public Task() {
        name = "";
        description = "";
        creator = new User();
        status = Status.CLOSED;
    }

    public Task(String name, User creator, String description) {
        this.name =name;
        this.creator = creator;
        this.description = description;
    }

    public Task(String name, User creator, String description, Status status) {
        this(name, creator, description);
        this.status = status;
    }
    public Task(Integer id,String name, User creator, String description, Status status) {
        this(name, creator, description, status);
        this.setId(id);
    }

    public Task(String name, User creator, String description, Integer status) {
        this(name, creator, description);
        if(status < 0 || status > 2)
            return;
        Status st = Status.PENDING;
        if(status == 1) st = Status.DONE;
        if(status == 2) st = Status.CLOSED;
        this.setStatus(st);
    }
    public Task(Integer id, String name, User creator, String description, Integer status) {
        this(name, creator, description, status);
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Status getStatus() {
        return status;
    }

    public int getIntStatus() {
        if(status == Status.PENDING)
            return 0;
        if(status == Status.DONE)
            return 1;
        return 2;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "tsm.model.User{ username = '" + id +
                "' }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Task other = (Task) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreator());
    }
}
