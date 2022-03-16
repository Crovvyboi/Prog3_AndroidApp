package com.example.prog3_androidapp;

import static android.content.ContentValues.TAG;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class JsonHandler extends AsyncTask<String, Integer, LinkedList<Meal>> {
    private WeakReference<RecyclerView> recyclerViewWeakReference;
    private WeakReference<Context> context;
    private MainActivity mainActivity;

    public JsonHandler (RecyclerView recyclerView, Context context, MainActivity mainActivity) {
        recyclerViewWeakReference = new WeakReference<>(recyclerView);
        this.context = new WeakReference<>(context);
        this.mainActivity = mainActivity;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected LinkedList<Meal> doInBackground(String... strings) {
        String jsonString;
        LinkedList<Meal> mList = new LinkedList<>();

        for (String string: strings) {
            // Get json string from file
            try{
                InputStream is = context.get().getAssets().open(string);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonString = new String(buffer, "UTF-8");
            } catch (IOException ex){
                ex.printStackTrace();
                return null;
            }

            // Copy and insert data in local json file
            File directory = new File(context.get().getFilesDir(), "Meals.json");
            try {
                FileWriter fw = new FileWriter(directory);
                fw.write(jsonString);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Could not write file");
            }

            // Get meals from Json
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
                    String chefURL = object.getString("chefURL");
                    // find format
                    String date = object.getString("dateTime");

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

                    Meal newMeal = new Meal(name, description, imageURL, chefURL, date, price, ingrediënts, vegetarian, vegan, takeAway, active, maxParticipants);
                    mList.add(newMeal);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to parse JSON");
            }
        }
        mainActivity.SetLinkedList(mList);
        return mList;
    }

    protected void onPostExecute(LinkedList<Meal> mList){
        // Update UI
        Toast gotItems = new Toast(context.get());
        gotItems.setText("Amount of meals read: " + mList.size());
        gotItems.show();

        // Get a handle to the RecyclerView.
        RecyclerView mRecyclerView = recyclerViewWeakReference.get();
        // Create an adapter and supply the data to be displayed.
        ListAdapter mAdapter = new ListAdapter(context.get(), mList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(context.get(), 2));

    }



}
