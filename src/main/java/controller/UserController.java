package controller;

import enums.Gender;
import model.common.Address;
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

    public String registerUser(String username, String password, boolean GDPR,
                               String firstName, String lastName,
                               int age, Address address, Gender gender, String email) {
        if (!GDPR){
            return "Please consent to the GDPR agreement.";
        }

        if (!isUsernameValid(username)) {
            return "Username is not valid. Username must be at least 8 symbols long.";
        }

        if (!isPasswordValid(password)){
            return "Password is not invalid. Password must be at least 5 symbols long";
        }

        if (!isNameValid(firstName) || !isNameValid(lastName)){
            return "The specified first and last name must be long at least 8 symbols each.";
        }

        if (!isAgeValid(age)){
            return "Specified age must be between 6 and 125 - excluded.";
        }

        if (!isGenderValid(gender)){
            return "The provided options for gender are: 'm' or 'f'.";
        }

        if (!isAddressValid(address)){
            return "Please provide a valid address. Fields must be non empty.";
        }

        if (!isEmailValid(email)){
            return "Please provide a valid email.";
        }

        User user = new User(firstName, lastName, address,
                gender, username, password, email, GDPR, age);

        userrepository.addUser(user);

        return "User " + username + " registered Successfully.";
    }

    private boolean isAddressValid(Address address) {
        return address.isAddressValid();
    }

    private boolean isGenderValid(Gender gender) {
        return gender.name().equals("m") || gender.name().equals("f");
    }

    private boolean isEmailValid(String email) {
        return Pattern.matches(
                "^[a-z][a-zA-Z0-9_.]*@([a-z][a-zA-Z0-9_]*(\\.))+[a-zA-Z]+", email);
    }

    private boolean isNameValid(String name) {
        return isStringInputValid(name) && name.trim().length() > 1 && name.trim().length() <= 20;
    }

    private boolean isAgeValid(int age) {
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
