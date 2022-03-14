package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    // Change to list of meals
    private final LinkedList<Meal> mList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve list of meals
        // Retrieve from Room, database or json (isn't required)
        for (int i = 0; i < 20; i++) {
            Meal newmeal = new Meal("Meal " + i, (double) i);
            mList.add(newmeal);
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ListAdapter(this, mList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    public void GoToDetails(View view) {
        // Goes to meal details on click of item in list

        TextView tv = view.findViewById(R.id.word);
        String mealString = (String) tv.getText();
        // Selected Meal
        Log.d(LOG_TAG, mealString);

        MainActivity activity = new MainActivity();

        // Transfer lists and get meal
        ArrayList<Meal> meals = new ArrayList<>();
        for (Meal meal: mList) {
            meals.add(meal);
        }
        Meal meal = meals.stream().filter(meal1 -> meal1.GetName().equals(mealString)).findFirst().orElse(null);
        Log.d(LOG_TAG, meal.GetPrice());

        // Send object (serializable) to next activity
        Intent newIntent = new Intent(this, MealDetails.class);
        newIntent.putExtra("sentMeal", meal);
        startActivity(newIntent);
    }
}