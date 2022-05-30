package tms.persistance.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import tms.model.User;
import tms.persistance.Utils.JdbcUtils;
import tms.persistance.iUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class UserDbRepository implements iUserRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public UserDbRepository(Properties props) {
        logger.info("Initializing UserDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    public User findOneById(String username) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlSelect = "select * from users where username=?";
        User found = null;
        try(PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setString(1, username);
            try(ResultSet result = ps.executeQuery()) {
                if(result.next()) {
                    String password = result.getString("password");
                    int elevated = result.getInt("elevated");
                    found = new User(username, password, elevated);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        logger.traceExit(found);
        return found;
    }


    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        String sqlSelect = "select * from users";
        try(PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            try(ResultSet result = ps.executeQuery()) {
                while(result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    int elevated = result.getInt("elevated");
                    User u = new User(username, password);
                    users.add(u);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        logger.traceExit(users);
        return users;
    }


    public User save(User entity) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlInsert = "insert into users(username, password, elevated) values (?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1,entity.getUsername());
            ps.setString(2,entity.getPassword());
            ps.setLong(3,entity.getElevated());
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


    public User delete(String username) {
        User found = this.findOneById(username);
        if(found == null)
            return null;
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlInsert = "delete from users where username=?";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1,username);
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


    public User update(User entity) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlInsert = "update users set password=? where username=?";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1,entity.getPassword());
            ps.setString(2,entity.getUsername());
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
    public User findUser(String username, String password) {
        logger.traceEntry();
        Connection conn = dbUtils.getConnection();
        String sqlSelect = "select * from users where username=? and password=?";
        User found = null;
        try(PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try(ResultSet result = ps.executeQuery()) {
                if(result.next()) {
                    int elevated = result.getInt("elevated");
                    found = new User(username, password, elevated);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println(e);
        }
        logger.traceExit(found);
        return found;
    }
}
