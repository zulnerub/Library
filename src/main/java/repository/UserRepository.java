package repository;

import model.user.impl.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
    }

    public void addUser(User user){
        users.add(user);
    }

    public User getUser(String username){
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers(){
        return users;
    }
}
