package com.example.prog3_androidapp;

import static android.content.ContentValues.TAG;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    // Change to list of meals
    private final LinkedList<Meal> mList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve list of meals from Json file
        DeserializeJSON();


        // Get a handle to the RecyclerView.
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
        ListAdapter mAdapter = new ListAdapter(this, mList);
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
        ArrayList<Meal> meals = new ArrayList<>(mList);
        Meal meal = meals.stream().filter(meal1 -> meal1.GetName().equals(mealString)).findFirst().orElse(null);
        assert meal != null;
        Log.d(LOG_TAG, meal.GetPrice());

        // Send object (serializable) to next activity
        Intent newIntent = new Intent(this, MealDetails.class);
        newIntent.putExtra("sentMeal", meal);
        startActivity(newIntent);
    }

    public void DeserializeJSON(){
        // Deserialize json and apply to values

        String jsonString = getJsonFromAssets("Meals.json" );
        ArrayList<Meal> meals = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(jsonString);
            JSONArray array = reader.getJSONArray("mealList");



            for (int i = 0; i < array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);

                // initialize meal at index
                String name = object.getString("name");
                String description = object.getString("description");
                String imageURL = object.getString("imageURL");
                // find format
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = df.parse(object.getString("dateTime"));

                double price = object.getDouble("price");

                boolean vegetarian = object.getBoolean("vegetarian");
                boolean vegan = object.getBoolean("vegan");
                boolean takeAway = object.getBoolean("takeAway");
                boolean active = object.getBoolean("active");

                int maxParticipants = object.getInt("maxParticipants");

                JSONArray ingredientArray = object.getJSONArray("ingrediënts");
                ArrayList<Ingrediënt> ingrediënts = new ArrayList<>();
                for (int k = 0; k < ingredientArray.length(); k++){
                    // Get and assign ingredients
                    JSONObject ingredientObj = ingredientArray.getJSONObject(k);

                    String ingName = ingredientObj.getString("name");
                    String ingImg = ingredientObj.getString("imageURL");
                    boolean ingAll = ingredientObj.getBoolean("allergenic");

                    Ingrediënt newIngredient = new Ingrediënt(ingName, ingImg, ingAll);

                }

                Meal newMeal = new Meal(name, description, imageURL, date, price, ingrediënts, vegetarian, vegan, takeAway, active, maxParticipants);
                mList.add(newMeal);
            }

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to parse JSON");
        }

    }

    public String getJsonFromAssets( String filename){
        String jsonString = null;
        try{
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }

        return  jsonString;
    }
}