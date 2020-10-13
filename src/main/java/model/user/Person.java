package model.user;

/**
 * Abstract class to provide partial implementation for Author and User.
 * Contains common methods.
 */
public abstract class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return String concatenation of the first and last name separated by single empty space.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * @return Provide the first name of the entity.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return Provide the second name of the entity.
     */
    public String getLastName() {
        return lastName;
    }
}
