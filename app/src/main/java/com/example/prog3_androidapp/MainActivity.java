package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    // Change to list of meals
    private LinkedList<Meal> mList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve list of meals from Json file and put them in recyclerview
        new JsonHandler(findViewById(R.id.recyclerView), this, this).execute("Meals.json");
    }

    // Goes to meal details on click of item in list
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    public void GoToDetails(View view) {
        // Implement Async task

        TextView tv = view.findViewById(R.id.word);
        String mealString = (String) tv.getText();
        // Selected Meal
        Log.d(LOG_TAG, mealString);

        MainActivity activity = new MainActivity();

        // Transfer lists and get meal
        ArrayList<Meal> meals = new ArrayList<>(mList);
        Meal meal = meals.stream().filter(meal1 -> meal1.GetName().equals(mealString)).findFirst().orElse(null);
        assert meal != null;
        Log.d(LOG_TAG, meal.toString());

        // Send object (serializable) to next activity
        Intent newIntent = new Intent(this, MealDetails.class);
        newIntent.putExtra("sentMeal", meal);
        startActivity(newIntent);
    }


    // Add new meal to list
    public void AddNew(View view) {
        Intent newIntent = new Intent(this, MealEntry.class);
        ArrayList<Meal> meals = new ArrayList<>(mList);
        newIntent.putExtra("mealList", meals);
        startActivity(newIntent);
    }

    public void SetLinkedList(LinkedList<Meal> mList) {
        this.mList = mList;
    }
}