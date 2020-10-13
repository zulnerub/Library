import controller.BookController;
import controller.UserController;
import enums.Gender;
import exception.CustomException;
import model.common.Address;
import model.user.impl.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the application where the starting and initialization of the application occurs.
 */
public class Application {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static User loggedUser;

    public static void main(String[] args) {

        UserController userController = new UserController();
        BookController bookController = new BookController();

        boolean isUserLogged = false;

        while (!isUserLogged) {
            System.out.println("Please enter username: ");
            String username = getUserInput();

            System.out.println("Please enter a password: ");
            String password = getUserInput();

            if (userController.validateCredentials(username, password)) {
                loggedUser = userController.getUserByUsername(username);

                isUserLogged = true;

                System.out.println("User " + username + " logged in successfully.");
            } else {
                System.out.println("Provided username and password does not match any existing account.");
                System.out.println("Do you want to register an account instead ?    yes/no");

                try {
                    String wantsToRegister = bufferedReader.readLine();

                    if (wantsToRegister.equalsIgnoreCase("yes")) {
                        registerUserInput(userController);
                    }
                } catch (IOException exception) {
                    throw new CustomException("No input found.");
                }
            }
        }


    }

    /**
     * Gathers input from the user when prompted to register a new user.
     * Sends the input to be validate and to register a user if the input is valid.
     *
     * @param userController The controller responsible for the user registration and authentication in the system.
     * @throws IOException BufferedReader can throw exception if there are problems with the input.
     */
    private static void registerUserInput(UserController userController) throws IOException {
        System.out.println("Do you consent to the GDPR agreement:   yes/no");
        String userInputGDPR = bufferedReader.readLine();

        System.out.println("Please input username: ");
        String userInputUsername = bufferedReader.readLine();

        System.out.println("Please input password: ");
        String userInputPassword = bufferedReader.readLine();

        System.out.println("Please enter first name: ");
        String userInputFirstName = bufferedReader.readLine();

        System.out.println("Please enter last name: ");
        String userInputLastName = bufferedReader.readLine();

        System.out.println("Please enter your age: ");
        String userInputAge = bufferedReader.readLine();

        System.out.println("Please enter your gender:   male/female");
        String userInputGender = bufferedReader.readLine();

        System.out.println("Please enter email address: ");
        String userInputEmail = bufferedReader.readLine();

        System.out.println("Address:");
        System.out.println("Enter country: ");
        String userInputCountry = bufferedReader.readLine();

        System.out.println("Enter city: ");
        String userInputCity = bufferedReader.readLine();

        System.out.println("Enter street name: ");
        String userInputStreet = bufferedReader.readLine();

        Address newAddress = new Address(userInputCountry, userInputCity, userInputStreet);

        System.out.println(userController.registerUser(userInputUsername, userInputPassword,
                userInputGDPR.equalsIgnoreCase("yes"),
                userInputFirstName, userInputLastName, Integer.parseInt(userInputAge),
                newAddress, Gender.valueOf(userInputGender.toUpperCase()), userInputEmail));
    }

    /**
     * Uses BufferedReader to get the user input from the console when called.
     * Throws custom exception when the BufferedReader throws an IOException.
     *
     * @return String representation of the user input
     * or Throws CustomException.
     */
    private static String getUserInput() {
        try {
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new CustomException(exception.getMessage());
        }
    }
}
