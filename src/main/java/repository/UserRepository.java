package repository;

import model.user.impl.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Has the role of containing all the registered users and execute simple operations like:
 * - adding user;
 * - searching for user
 * - getting all users
 * - etc.
 */
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
    }

    /**
     * Adds a User object to the collection of users.
     *
     * @param user Object of type User validated in advance.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Searches for a user by username.
     *
     * @param username String representation of the user's name.
     * @return if found returns the found User otherwise - null.
     */
    public User getUser(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * @return Gets all the users in the repository.
     */
    public List<User> getAllUsers() {
        return users;
    }
}
