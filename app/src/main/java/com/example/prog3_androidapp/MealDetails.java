package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

    public void SetFields(Meal meal) {
        // Set fields in view
        Bitmap mealView = null;
        Bitmap chefView = null;

        ImageView mealImage = this.findViewById(R.id.MealImage);

        ImageView chefImage = this.findViewById(R.id.ChefImage);

        new DownLoadImageTask(mealImage, chefImage).execute(meal.GetImgURL(), meal.GetChefURL());


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

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap[]> {
        ImageView mealView;
        ImageView chefView;

        public DownLoadImageTask(ImageView mealView, ImageView chefView){
            this.mealView = mealView;
            this.chefView = chefView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap[] doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo1 = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo1 = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            urlOfImage = urls[1];
            Bitmap logo2 = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo2 = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }

            Bitmap[] bitmaps = new Bitmap[]{
                    logo1,logo2
            };

            return bitmaps;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap[] result){
            mealView.setImageBitmap(result[0]);
            chefView.setImageBitmap(result[1]);
        }
    }
}