package com.example.zizoj.fit.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zizoj.fit.R;

public class ImageWorkoutAdapter extends PagerAdapter {

    private Context context ;

    private int [] ImageIds = new int[] {
            R.drawable.back,
            R.drawable.chest,
            R.drawable.sholder,
            R.drawable.abs,
            R.drawable.bi,
            R.drawable.leg,
            R.drawable.tri,
            R.drawable.stritsh,
            R.drawable.stritch2 };


    public ImageWorkoutAdapter(Context context){

        this.context = context;
    }


    @Override
    public int getCount() {
        return ImageIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(ImageIds[position]);

        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}