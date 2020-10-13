package controller;

import enums.Gender;
import model.common.Address;
import model.user.impl.User;
import repository.UserRepository;

import java.util.regex.Pattern;

/**
 * Creates connection between the user and the user repository.
 * Provides authentication and validation of the user.
 * Operates on the user repository.
 */
public class UserController {
    private final UserRepository userrepository = new UserRepository();

    /**
     * Provides a check if a user with that username is already registered and
     * if user exists validates the provided password.
     *
     * @param username - username provided by the user.
     * @param password - password provided by the user.
     * @return true if authentication is successful or false on failure.
     */
    public boolean validateCredentials(String username, String password) {
        User userToAuthenticate = getUser(username);

        if (userToAuthenticate != null) {
            return userToAuthenticate.getPassword().equals(password);
        }

        return false;
    }

    /**
     * Calls a method of the userRepository to search for a user with said username.
     *
     * @param username String representation of the user's username to be searched for.
     * @return The found user or null if user does n't exist.
     */
    public User getUserByUsername(String username) {
        return userrepository.getUser(username);
    }

    /**
     * Takes parameters from the console, validates them and
     * if valid register a user via the user repository method to add user.
     * If not returns a statement about the first invalid parameter found.
     *
     * @param username  String input from the console.
     * @param password  String input from the console.
     * @param GDPR      Represent whether the user agrees or disagrees with the GDPR rules.
     * @param firstName String input from the console.
     * @param lastName  String input from the console.
     * @param age       int input from the console
     * @param address   Object of type Address containing street, city and country fields.
     * @param gender    Enum containing value either 'm' (male) or 'f' (female).
     * @param email     String input from the console.
     * @return Ether error message if any of the parameters is invalid
     * or success message if user is created.
     */
    public String registerUser(String username, String password, boolean GDPR,
                               String firstName, String lastName,
                               int age, Address address, Gender gender, String email) {
        if (!GDPR) {
            return "Please consent to the GDPR agreement.";
        }

        if (!isUsernameValid(username)) {
            return "Username is not valid. Username must be at least 8 symbols long.";
        }

        if (!isPasswordValid(password)) {
            return "Password is not invalid. Password must be at least 5 symbols long";
        }

        if (!isNameValid(firstName) || !isNameValid(lastName)) {
            return "The specified first and last name must be long at least 8 symbols each.";
        }

        if (!isAgeValid(age)) {
            return "Specified age must be between 6 and 125 - excluded.";
        }

        if (!isGenderValid(gender)) {
            return "The provided options for gender are: 'm' or 'f'.";
        }

        if (!isAddressValid(address)) {
            return "Please provide a valid address. Fields must be non empty.";
        }

        if (!isEmailValid(email)) {
            return "Please provide a valid email.";
        }

        User user = new User(firstName, lastName, address,
                gender, username, password, email, GDPR, age);

        userrepository.addUser(user);

        return "User " + username + " registered Successfully.";
    }

    /**
     * Provides validation for the address. Calling internal method to validate the address.
     *
     * @param address Object of type Address.
     * @return true if parameter is valid or false if not valid.
     */
    private boolean isAddressValid(Address address) {
        return address.isAddressValid();
    }

    /**
     * Provides validation for the gender input.
     *
     * @param gender Enum of type Gender.
     * @return true if parameter is valid or false if not valid.
     */
    private boolean isGenderValid(Gender gender) {
        return gender.name().equals("m") || gender.name().equals("f");
    }

    /**
     * Validation of the email using a regex.
     *
     * @param email String representation of the user's email.
     * @return true if parameter is valid or false if not valid.
     */
    private boolean isEmailValid(String email) {
        return Pattern.matches(
                "^[a-z][a-zA-Z0-9_.]*@([a-z][a-zA-Z0-9_]*(\\.))+[a-zA-Z]+", email);
    }

    /**
     * Provides validation for either the first name or the last name based on name length.
     *
     * @param name String input from the console.
     * @return true if parameter is valid or false if not valid.
     */
    private boolean isNameValid(String name) {
        return isStringInputValid(name) && name.trim().length() > 1 && name.trim().length() <= 20;
    }

    /**
     * Validation for the given age of the user, must be between 7 and 124 inclusive.
     *
     * @param age int value of the users age
     * @return true if parameter is valid or false if not valid.
     */
    private boolean isAgeValid(int age) {
        return age > 6 && age < 125;
    }

    /**
     * Validation for the assigned password for creating a new user.
     * Must be bigger then 4 symbols.
     *
     * @param password String value from the console.
     * @return true if parameter is valid or false if not valid.
     */
    private boolean isPasswordValid(String password) {
        return isStringInputValid(password) && password.length() > 4;
    }

    /**
     * Validates the provided input for the username.
     * If the username is not empty, blank and if there is already a user with such username.
     *
     * @param username String representation of the username of the user
     * @return true if there is no user with that name, and the string is valid and over 7 symbols long.
     * or false if any of the above are not met.
     */
    private boolean isUsernameValid(String username) {
        return isStringInputValid(username) && username.length() > 7 && getUser(username) == null;
    }

    /**
     * Checks if given string is non null and not blank.
     *
     * @param stringInput A string input.
     * @return true if the provided string matches the expected checks otherwise - false.
     */
    private boolean isStringInputValid(String stringInput) {
        return stringInput != null && !stringInput.isBlank();
    }

    /**
     * Searches the user repository for a user with a given name.
     *
     * @param username String representation of the username.
     * @return User if such exist with that username or null.
     */
    private User getUser(String username) {
        return userrepository.getAllUsers()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
