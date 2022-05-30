package tms.model;

import java.util.Objects;

public class Comment extends Entity<Integer>{
    User creator;
    String content;
    Integer id_task;

    public Comment() {
        creator = new User();
        content = "";
        id_task = 0;
    }
    public Comment(User creator, String content, Integer id_task) {
        this.creator = creator;
        this.content = content;
        this.id_task = id_task;
    }
    public Comment(Integer id, User creator, String content, Integer id_task) {
        this(creator, content, id_task);
        this.setId(id);
    }


    public User getCreator() {
        return creator;
    }

    public String getContent() {
        return content;
    }

    public Integer getId_task() {
        return id_task;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId_task(Integer id_task) {
        this.id_task = id_task;
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
        Comment other = (Comment) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreator());
    }
}
