package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.util.Log;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class Meal implements Serializable {
    private String name;
    private String description;
    private String imageURL;
    private String chefURL;

    private String dateTime;

    private double price;

    private ArrayList<Ingrediënt> ingrediënts;
    private ArrayList<String> allergens;

    private boolean vegetarian;
    private boolean vegan;
    private boolean takeAway;
    private boolean active;

    private int maxParticipants;

    public Meal(String name, String description, String imageURL, String chefURL, String dateTime, double price, ArrayList<String> allergens, boolean vegetarian, boolean vegan, boolean takeAway, boolean active, int maxParticipants)
    {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.chefURL = chefURL;

        this.dateTime = dateTime;

        this.price = price;

        this.ingrediënts = new ArrayList<>();
        this.allergens = allergens;

        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.takeAway = takeAway;
        this.active = active;
        this.maxParticipants = maxParticipants;
    }

    public void AddIngrediënt (Ingrediënt ingrediënt)
    {
        // Adds ingrediënt to meal
        if (!ingrediënts.contains(ingrediënt))
        {
            ingrediënts.add(ingrediënt);
            // Add to allergens if allergenic
            if (ingrediënt.GetAllergenic() && !allergens.contains(ingrediënt))
            {
                allergens.add(ingrediënt.GetName());
            }
        }
        else {
            Throwable exception = new Throwable("Double ingrediënt in meal.");
            Log.i(LOG_TAG, "Ingrediënt already exists in meal!", exception);
        }
    }

    // Getters & Setters
    public String GetName(){
        return this.name;
    }
    public String GetPrice(){ return "€ " + String.valueOf(this.price); }
    public String GetDescription(){ return this.description; }
    public String GetImgURL(){ return this.imageURL; }
    public String GetChefURL(){ return this.chefURL; }
    public String GetServed(){
        return "Served on: " + dateTime;
    }
    public String GetAllergies(){
        if (allergens.isEmpty()){
            return "No allergens present in this meal!";
        }
        else {
            String string = "Allergens: " + allergens.get(0);
            for (String all: allergens) {
                string = string + ", " + all;
            }
            return string;
        }
    }
    public String GetProperties() {
        String properties = "This meal is: \n";

        if (vegetarian){
            properties = properties + "+ Vegetarian \n";
        }
        if (vegan){
            properties = properties + "+ Vegan \n";
        }
        if (takeAway){
            properties = properties + "+ Take away \n";
        }
        if (active){
            properties = properties + "+ Active \n";
        }
        else {
            properties = properties + "- Inactive \n";
        }

        return properties;
    }
    public String GetDatePrice(){
        return dateTime + " | " + GetPrice();
    }

    @Override
    public String toString(){
        return name + " (" + price + "): " + description;
    }
}

class Ingrediënt {
    private String name;
    private String imageURL;
    private boolean allergenic;


    public Ingrediënt(String name, String imageURL, boolean allergenic)
    {
        this.name = name;
        this.imageURL = imageURL;
        this.allergenic = allergenic;
    }

    public boolean GetAllergenic ()
    {
        return allergenic;
    }
    public String GetURL(){return imageURL;}
    public String GetName() { return name; }
}
