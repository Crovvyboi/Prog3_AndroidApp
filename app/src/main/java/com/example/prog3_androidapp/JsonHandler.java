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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
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
                InputStream is = new URL("https://shareameal-api.herokuapp.com/api/meal").openStream();
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonString = new String(buffer, "UTF-8");
            } catch (IOException ex){
                ex.printStackTrace();
                return null;
            }

            try {
                InputStream is = new URL("https://shareameal-api.herokuapp.com/api/meal").openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = rd.read()) != -1) {
                    sb.append((char) cp);
                }
                jsonString = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }

            // Copy and insert data in local json file
//            File directory = new File(context.get().getFilesDir(), "Meals.json");
//            try {
//                FileWriter fw = new FileWriter(directory);
//                fw.write(jsonString);
//                fw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e(LOG_TAG, "Could not write file");
//            }

            // Get meals from Json
            try {
                JSONObject reader = new JSONObject(jsonString);
                JSONArray array = reader.getJSONArray("result");

                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);

                    // initialize meal at index
                    String name = object.getString("name");
                    String description = object.getString("description");
                    String imageURL = object.getString("imageUrl");
                    String chefURL = "https://thumbs.dreamstime.com/z/portrait-busy-chef-smiling-holding-cooking-utensil-fresh-ingredient-30014151.jpg";
                    String date = object.getString("dateTime");

                    double price = object.getDouble("price");

                    boolean vegetarian = object.getBoolean("isVega");
                    boolean vegan = object.getBoolean("isVegan");
                    boolean takeAway = object.getBoolean("isToTakeHome");
                    boolean active = object.getBoolean("isActive");

                    int maxParticipants = object.getInt("maxAmountOfParticipants");

                    ArrayList<Ingrediënt> ingrediënts = new ArrayList<>();

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
