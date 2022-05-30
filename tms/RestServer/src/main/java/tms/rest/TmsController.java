package tms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tms.model.Task;
import tms.model.User;
import tms.persistance.RepositoryException;
import tms.persistance.db.TaskDbRepository;
import tms.persistance.db.UserDbRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tms")
public class TmsController {

    private static final String template = "Hello, %s!";
    private static final HashSet<User> loggedUsers = new HashSet<>();

    @Autowired
    private UserDbRepository userRepository;
    @Autowired
    private TaskDbRepository taskRepository;

    @CrossOrigin
    @RequestMapping("/users/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @CrossOrigin
    @RequestMapping( value = "/users", method = RequestMethod.GET)
    public User[] getAllUsers() {
        System.out.println("Get all users ...");
        return loggedUsers.toArray(new User[0]);
    }

    @CrossOrigin
    @RequestMapping( value = "/tasks", method = RequestMethod.GET)
    public Task[] getAllTasks() {
        System.out.println("Get all tasks ...");
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            tasks.add(task);
        }
        return tasks.toArray(new Task[0]);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        System.out.println("Get user by username "+ username);
        User user = userRepository.findOneById(username);
        if (user==null)
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) {
        User found = userRepository.findUser(user.getUsername(), user.getPassword());
        if(found == null)
            throw new RepositoryException("Login failed!");
        loggedUsers.add(found);
        System.out.println(found);
        return found;
    }

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@RequestBody User user) {
        User found = userRepository.findOneById(user.getUsername());
        System.out.println(found);
        if(found == null)
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        loggedUsers.remove(found);
        System.out.println(found + "logged out");
        return new ResponseEntity<>("Logout success", HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User create(@RequestBody User user){
        User saved = userRepository.save(user);
        if( saved == null)
            throw new RepositoryException("Error creating user");
        return saved;
    }

    @CrossOrigin
    @RequestMapping(value = "/users/{username}", method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
            System.out.println("Updating user ...");
        return userRepository.update(user);

    }

    @CrossOrigin
    @RequestMapping(value="/users/{username}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String username){
        System.out.println("Deleting user ... "+ username);
        try {
            userRepository.delete(username);
            return new ResponseEntity<User>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
