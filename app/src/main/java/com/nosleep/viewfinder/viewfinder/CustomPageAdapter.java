package com.nosleep.viewfinder.viewfinder;

import android.content.Context;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CustomPageAdapter extends PagerAdapter {

    private Context mContext;
    //Make firebase pass images into drawable and add references to this array

    //array of all of the images
    private int[] images = {R.drawable.night_sky_1, R.drawable.night_sky_2,
            R.drawable.night_sky_3};

    public CustomPageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        int img = images[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_cycle_images,
                collection, false);
        ImageView img_view = layout.findViewById(R.id.imageView);
        img_view.setImageResource(img);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int customPagerEnum = images[position];
        return mContext.getString(images[position]);
    }

}