package com.example.prog3_androidapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.LinkedList;

public class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final LinkedList<Meal> mList;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, LinkedList<Meal> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mList = wordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal mCurrent = mList.get(position);
        holder.titleText.setText(mCurrent.GetName());
        holder.datePrice.setText(mCurrent.GetDatePrice());
        new FrontImg(holder.img).execute(mCurrent.GetImgURL());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView titleText;
    public final TextView datePrice;
    public final ImageView img;
    final ListAdapter mAdapter;

    public ViewHolder(View itemView, ListAdapter adapter) {
        super(itemView);
        titleText = itemView.findViewById(R.id.title);
        datePrice = itemView.findViewById(R.id.datePrice);
        img = itemView.findViewById(R.id.imageView);
        this.mAdapter = adapter;
    }
}

class FrontImg extends AsyncTask<String, Void, Bitmap> {
    ImageView mealImage;

    public FrontImg(ImageView mealImage) {
        this.mealImage = mealImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        Bitmap mealimg = null;

        try{
            String url = urls[0];
            InputStream is = new java.net.URL(url).openStream();
            mealimg = BitmapFactory.decodeStream(is);

        } catch (Exception e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mealimg;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        mealImage.setImageBitmap(bitmap);
    }
}
