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
     * Adds a User object to the collection of users.
     *
     * @param user Object of type User validated in advance.
     */
    public void addUser(User user) {
        users.putIfAbsent(user.getUsername(), user);
    }

    /**
     * Searches for a user by username.
     *
     * @param username String representation of the user's name.
     * @return if found returns the found User otherwise - null.
     */
    public User getUser(String username) {
        String foundUsername = users.keySet().stream()
                .filter(uName -> uName.equals(username))
                .findFirst()
                .orElse(null);

        return foundUsername != null ? users.get(foundUsername) : null;
    }

    /**
     * @return Gets all the users in the repository.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
