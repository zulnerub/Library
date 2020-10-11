package controller;

import model.user.impl.User;
import repository.UserRepository;

import java.util.regex.Pattern;

public class UserController {
    private final UserRepository userrepository;

    public UserController(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    public boolean validateCredentials(String username, String password) {
        User userToAuthenticate = getUser(username);

        if (userToAuthenticate != null) {
            return userToAuthenticate.getPassword().equals(password);
        }

        return false;
    }

    public String registerUser(String username, String password) {
        if (isUsernameValid(username)) {
            User user = new User(username, password)
            userrepository.addUser();
        } else {
            return "Username not valid. Username must be at least 8 symbols long.";
        }

    }

    private boolean isEmailValid(String email){
        return Pattern.matches(
                "^[a-z][a-zA-Z0-9_.]*@([a-z][a-zA-Z0-9_]*(\\.))+[a-zA-Z]+", email);
    }

    private boolean validateGDPRConsent(boolean GDPRConsent){
        return GDPRConsent;
    }

    private boolean isNameValid(String name){
        return name != null && name.trim().length() > 1 && name.trim().length() <= 20;
    }

    private boolean isAgeValid(int age){
        return age > 6 && age < 125;
    }

    private boolean isPasswordValid(String password) {
        return isStringInputValid(password) && password.length() > 4;
    }

    private boolean isUsernameValid(String username) {
        return isStringInputValid(username) && username.length() > 7 && getUser(username) == null;
    }

    private boolean isStringInputValid(String stringInput) {
        return stringInput != null && !stringInput.isBlank();
    }

    private User getUser(String username) {
        return userrepository.getAllUsers()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
