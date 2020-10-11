import controller.UserController;
import exception.CustomException;
import model.user.impl.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {


        List<User> loggedUsers = new ArrayList<>();
        UserController userController = new UserController();

        String username = getUserInput();
        String password = getUserInput();

        if (userController.validateCredentials(username, password)) {
            User loggedUser = userController.getUserByUsername(username);
            loggedUsers.add(loggedUser);
            System.out.println("User " + username + " logged in successfully.");
        }else {
            System.out.println("Provided username and password does not match any existing account.");
        }

    }

    private static String getUserInput() {
        try {
            return bufferedReader.readLine();
        }catch (IOException exception){
            throw new CustomException(exception.getMessage());
        }
    }
}
