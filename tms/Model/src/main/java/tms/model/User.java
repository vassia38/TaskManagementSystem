package tms.model;
import java.util.Objects;

public class User extends Entity<String>{
    private String password;
    private int elevated;

    public User() {
        password = "";
    }

    public User(String username) {
        this();
        this.setId(username);
    }

    public User( String username, int elevated) {
        this(username);
        this.elevated = elevated;
    }

    public User( String username, String password) {
        this.setId(username);
        this.password = password;
        this.elevated = 0;
    }

    public User(String username, String password, int elevated) {
        this(username, password);
        this.elevated = elevated;
    }

    public String getUsername() {
        return this.getId();
    }

    public void setUsername(String username) {
        this.setId(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        User other = (User) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassword());
    }

    public int getElevated() {
        return elevated;
    }

    public void setElevated(int elevated) {
        this.elevated = elevated;
    }
}
