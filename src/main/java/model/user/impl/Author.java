package model.user.impl;

import model.user.Person;

import java.util.Date;

/**
 * Creates an object Author to complement the Book objects.
 * Stores Dates of birth and death as well as common fields and methods from Person
 */
public class Author extends Person {
    private Date dateOfBirth;
    private Date dateOfDeath;

    public Author(String firstName, String lastName, Date dateOfBirth, Date dateOfDeath) {
        super(firstName, lastName);
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

    /**
     * @return Get the date of birth of the author.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @return Get the date of death of the author.
     */
    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    /**
     * @param dateOfDeath Set date of death when author was alive when the book was created.
     */
    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }
}
