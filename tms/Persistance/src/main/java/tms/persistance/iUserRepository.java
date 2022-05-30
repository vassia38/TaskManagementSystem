package tms.persistance;

import tms.model.User;

public interface iUserRepository extends Repository<String, User> {
    User findUser(String username, String password);
}
