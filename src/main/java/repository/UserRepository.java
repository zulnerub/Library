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

    public List<User> getAllUsers(){
        return users;
    }
}
