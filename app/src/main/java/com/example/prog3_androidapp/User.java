package com.example.prog3_androidapp;

import java.util.List;

public abstract class User {
    private String firstName;
    private String lastName;
    private String emailAdress;
    private String phoneNumber;
    private String password;

    private Location livesAt;

    private String pictureURL;
}

class Cook extends User {
    private Location worksAt;
    private String description;
    private List<Meal> makesMeals;
    private int rating;
}

class Participant extends User {
    private List<Meal> myMeals;
    private List<Meal> favouriteCooks;
}

class Location {
    private String streetNumberCombo;
    private String postal;
    private String city;
}
