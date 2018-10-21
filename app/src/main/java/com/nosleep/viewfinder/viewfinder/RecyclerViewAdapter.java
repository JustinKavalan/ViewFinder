package com.nosleep.viewfinder.viewfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> {

    private Bitmap[] mDataset;

    public void setmDataset(Bitmap[] mDataset) {
        this.mDataset = mDataset;
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public RecycleViewHolder(ImageView view){
            super(view);
            mImageView = view;
        }
    }

    public RecyclerViewAdapter(Bitmap[] myDataset){
        mDataset = myDataset;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView itemView =  (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        RecycleViewHolder vh = new RecycleViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        holder.mImageView.setImageBitmap(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
