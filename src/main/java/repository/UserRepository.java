package repository;

import model.user.impl.User;

import java.util.*;

/**
 * Has the role of containing all the registered users and execute simple operations like:
 * - adding user;
 * - searching for user
 * - getting all users
 * - etc.
 */
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    /**
     * Adds a user to the user repository if username is not taken.
     *
     * @param user Unique identifier for the user.
     * @return Message explaining the action that was taken.
     */
    public String addUser(User user) {
        String currentUsername = user.getUsername();

        if (users.containsKey(currentUsername)) {
            return "User with username " + currentUsername + " already exists";
        }

        users.putIfAbsent(currentUsername, user);

        return "User with username " + currentUsername + " was created.";
    }

    /**
     * Searches for a user by username.
     *
     * @param username String representation of the user's name.
     * @return if found returns the found User otherwise - null.
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * @return Gets all the users in the repository.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
