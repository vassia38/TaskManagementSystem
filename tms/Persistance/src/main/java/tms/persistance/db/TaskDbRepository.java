package tms.persistance.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import tms.model.Task;
import tms.model.User;
import tms.persistance.Utils.JdbcUtils;
import tms.persistance.iTaskRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class TaskDbRepository implements iTaskRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public TaskDbRepository(Properties props) {
        logger.info("Initializing TaskDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    public Task findOneById(Integer id) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlSelect = "select * from tasks where id=?";
//        String sqlFindUser = "select * from users where username=? LIMIT 1";
        Task found = null;
        try(PreparedStatement ps1 = conn.prepareStatement(sqlSelect);
            //PreparedStatement ps2 = conn.prepareStatement(sqlFindUser)
        ) {
            ps1.setInt(1, id);
            try(ResultSet result1 = ps1.executeQuery()) {
                if(result1.next()) {
                    String name = result1.getString("name");
                    String description = result1.getString("description");
                    int status = result1.getInt("status");
                    /*ResultSet result2 = ps2.executeQuery();
                    if(!result2.next())
                        return null;
                    String username = result2.getString("username");
                    int elevated = result2.getInt("elevated");
                    User creator = new User(username, elevated);*/
                    String username = result1.getString("creator");
                    User creator = new User(username);
                    found = new Task(id, name, creator, description, status);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        logger.traceExit(found);
        return found;
    }


    public Iterable<Task> findAll() {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        List<Task> tasks = new ArrayList<>();
        String sqlSelect = "select * from tasks";
        try(PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            try(ResultSet result = ps.executeQuery()) {
                while(result.next()) {
                    Integer id = result.getInt("id");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    int status = result.getInt("status");
                    String username = result.getString("creator");
                    User creator = new User(username);
                    Task task = new Task(id, name, creator, description, status);
                    tasks.add(task);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        logger.traceExit(tasks);
        return tasks;
    }


    public Task save(Task entity) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlInsert = "insert into tasks(name, description, creator) values (?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getDescription());
            ps.setString(3,entity.getCreator().getUsername());
            int result = ps.executeUpdate();
            logger.trace("Saved {} instances", result);
            ResultSet rs = ps.getGeneratedKeys();
            long generatedKey = 0L;
            if (rs.next()) {
                generatedKey = rs.getLong(1);
            }
            logger.traceExit(entity);
            return entity;
        } catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        return null;
    }


    public Task delete(Integer id) {
        Task found = this.findOneById(id);
        if(found == null)
            return null;
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlInsert = "delete from tasks where id=?";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            logger.trace("deleted {} instances", result);
            logger.traceExit(found);
            return found;
        } catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        return null;
    }


    public Integer size() {
        return null;
    }


    public Task update(Task entity) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlInsert = "update tasks set name=?, description=?,status=? where id=?";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getDescription());
            ps.setInt(3, entity.getIntStatus());
            ps.setInt(4, entity.getId());
            int result = ps.executeUpdate();
            logger.trace("updated {} instances", result);
            logger.traceExit(entity);
            return entity;
        } catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Task> findAllTasksOfUser(User user) {
        return new ArrayList<>();
    }

    @Override
    public Iterable<Task> findAllTasksOfCreator(User user) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        List<Task> tasks = new ArrayList<>();
        String sqlSelect = "select * from tasks where creator=?";
        try(PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setString(1, user.getUsername());
            try(ResultSet result = ps.executeQuery()) {
                while(result.next()) {
                    Integer id = result.getInt("id");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    int status = result.getInt("status");
                    String username = result.getString("creator");
                    User creator = new User(username);
                    Task task = new Task(id, name, creator, description, status);
                    tasks.add(task);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        logger.traceExit(tasks);
        return tasks;
    }

    @Override
    public Task addUserToTask(User user) {
        return null;
    }
}
