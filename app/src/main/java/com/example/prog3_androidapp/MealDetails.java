package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("RestrictedApi")
public class MealDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        // Get object from MainActivity
        Intent intent = getIntent();
        Meal meal = (Meal) intent.getSerializableExtra("sentMeal");

        Log.d(LOG_TAG, "Current meal: " + meal.GetName());

        SetFields(meal);
    }

    public void SetFields(Meal meal){
        // Set fields in view
        View mealImage = this.findViewById(R.id.MealImage);

        View chefImage = this.findViewById(R.id.ChefImage);



        TextView propertiesList = this.findViewById(R.id.PropertyList);
        propertiesList.setText(meal.GetProperties());

        TextView titleText = this.findViewById(R.id.TitleText);
        titleText.setText(meal.GetName());

        TextView priceText = this.findViewById(R.id.PriceText);
        priceText.setText(String.valueOf(meal.GetPrice()));

        TextView descripText = this.findViewById(R.id.DescriptionText);
        descripText.setText(meal.GetDescription());

        TextView servedText = this.findViewById(R.id.ServedText);
        servedText.setText(meal.GetServed());

        TextView allergText = this.findViewById(R.id.AllergiesText);
        allergText.setText(meal.GetAllergies());
    }
}