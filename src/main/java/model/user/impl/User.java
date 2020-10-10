package model.user.impl;

import enums.Gender;
import model.common.Address;
import model.common.History;
import model.user.Person;

public class User extends Person {
    private Address location;
    private Gender gender;
    private History history;
    private String username;
    private String password;
    private String email;
    private boolean GDPR;
    private int age;


    public User(String firstName, String lastName, Address location, Gender gender, History history, String username, String password, String email, boolean gdpr, int age) {
        super(firstName, lastName);
        this.location = location;
        this.gender = gender;
        this.history = history;
        this.username = username;
        this.password = password;
        this.email = email;
        GDPR = gdpr;
        this.age = age;
    }
}
