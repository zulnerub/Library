import controller.BookController;
import controller.UserController;
import enums.BookGenre;
import enums.BookTags;
import enums.Gender;
import exception.CustomException;
import model.common.Address;
import model.user.impl.Author;
import model.user.impl.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

/**
 * Main class of the application where the starting and initialization of the application occurs.
 */
public class Application {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final UserController userController = new UserController();
    private static final BookController bookController = new BookController();
    private static LocalDate currentDate = LocalDate.now();
    private static User loggedUser;
    private static boolean isUserLogged = false;

    public static void main(String[] args) {

        init();

        //loginUser();

        System.out.println(bookController.searchBookByAuthorsFirstName("George"));
        System.out.println(bookController.searchBookByAuthorsLastName("Rolling"));
        bookController.searchBookByAuthorsFullName("George Martin")
                .forEach(b -> System.out.println(
                        b.getISBN() + " "
                                + b.getTitle() + " "
                                + b.getGenre().name() + " "
                               // + b.getAuthors().forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName())) + " "
                                //+ b.getBookTags().forEach(System.out::println)
                ));

    }

    private static void init(){
        LocalDate ld = LocalDate.of(1965, 1, 1);
        LocalDate sLd = LocalDate.of(1972,3,22);
        Author georgeMartin = new Author("George", "Martin", ld, null);
        Author joanRolling = new Author("Joan", "Rolling", sLd, null);

        System.out.println(bookController.addPaperBook("1234-5", "Game of thrones",
                "Very interesting book about internal and  external royal family affairs.",
                Collections.singletonList(georgeMartin),
                BookGenre.FANTASY, Arrays.asList(BookTags.STORY, BookTags.HOBBY), 5));

        System.out.println(bookController.addEBook("1234-6", "Harry Potter",
                "A book about magic and magitians",
                Collections.singletonList(joanRolling),
                BookGenre.FANTASY, Arrays.asList(BookTags.STORY, BookTags.CHILDREN), "http://harrypotter.online.read.com"));

        System.out.println(bookController.addDownloadableEBook("1234-7", "The day the earth stood still",
                "A book about aliens and post apocaliptic world scenarious.",
                Collections.singletonList(joanRolling),
                BookGenre.SCI_FI, Collections.singletonList(BookTags.LEARNING), "http://earthstood.online.read.com",
                "http://stillearth.online.download.com"));
    }

    private static void loginUser() {
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
                        registerUserInput();
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
     * @throws IOException BufferedReader can throw exception if there are problems with the input.
     */
    private static void registerUserInput() throws IOException {
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
