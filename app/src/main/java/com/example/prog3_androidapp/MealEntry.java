package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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


}