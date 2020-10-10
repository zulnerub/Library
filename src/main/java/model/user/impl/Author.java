package model.user.impl;

import model.user.Person;

import java.util.Date;

public class Author extends Person {
    private Date dateOfBirth;
    private Date dateOfDeath;

    public Author(String firstName, String lastName, Date dateOfBirth, Date dateOfDeath) {
        super(firstName, lastName);
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfDeath(){
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath){
        this.dateOfDeath = dateOfDeath;
    }
}
