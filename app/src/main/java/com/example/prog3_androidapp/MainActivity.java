package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    // Change to list of meals
    private LinkedList<Meal> mList = new LinkedList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();


        // if user starts app for first time, update internal storage file, otherwise read from internal file
        if (intent.getSerializableExtra("state") == null){
            // Retrieve list of meals from Json file and put them in recyclerview
            new JsonHandler(findViewById(R.id.recyclerView), this, this).execute("Meals.json");
        }
        else if (intent.getSerializableExtra("state").toString().equals("1")){
            mList = new LinkedList<Meal>((Collection<? extends Meal>) intent.getSerializableExtra("mealList"));
            Log.d(LOG_TAG, "Amount of current meals in list: " + mList.size());
            Log.d(LOG_TAG, "Here from Entry");

            Toast gotItems = new Toast(this);
            gotItems.setText("Amount of meals read: " + mList.size());
            gotItems.show();

            // Get a handle to the RecyclerView.
            RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
            // Create an adapter and supply the data to be displayed.
            ListAdapter mAdapter = new ListAdapter(this, mList);
            // Connect the adapter with the RecyclerView.
            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }


    // Goes to meal details on click of item in list
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    public void GoToDetails(View view) {
        // Implement Async task

        TextView tv = view.findViewById(R.id.title);
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