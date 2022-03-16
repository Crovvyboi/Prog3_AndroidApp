package com.example.prog3_androidapp;

import static android.content.ContentValues.TAG;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import org.json.JSONException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class MealEntry extends AppCompatActivity {

    ArrayList<Meal> mList;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_entry);

        Intent intent = getIntent();
        mList = (ArrayList<Meal>) intent.getSerializableExtra("mealList");

        Log.d(LOG_TAG, "Amount of current meals in list: " + mList.size());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddNewMeal(View view) {
        EditText nameText = findViewById(R.id.nameText);
        EditText descripText = findViewById(R.id.descriptionText);
        EditText imgUrl = findViewById(R.id.imageUrl);
        EditText pricetext = findViewById(R.id.priceText);
        CheckBox vegeBox = findViewById(R.id.vegebox);
        CheckBox veganBox = findViewById(R.id.veganBox);
        Switch takeSwitch = findViewById(R.id.switchTakeAway);
        Switch awaSwitch = findViewById(R.id.switchAvailable);

        try {
            Meal newMeal = new Meal(
                    nameText.getText().toString(),
                    descripText.getText().toString(),
                    imgUrl.toString(),
                    "https://thumbs.dreamstime.com/z/portrait-busy-chef-smiling-holding-cooking-utensil-fresh-ingredient-30014151.jpg",
                    LocalDate.now().toString(),
                    Double.valueOf(pricetext.getText().toString()),
                    new ArrayList<String>(),
                    vegeBox.isChecked(),
                    veganBox.isChecked(),
                    takeSwitch.isChecked(),
                    awaSwitch.isChecked(),
                    7
            );

            mList.add(newMeal);

            ToMain();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to create meal");
        }


    }

    public void ToMain () {
        Intent newIntent = new Intent(this, MainActivity.class);
        ArrayList<Meal> meals = new ArrayList<>(mList);
        newIntent.putExtra("mealList", meals);
        newIntent.putExtra("state", 1);
        startActivity(newIntent);
    }
}