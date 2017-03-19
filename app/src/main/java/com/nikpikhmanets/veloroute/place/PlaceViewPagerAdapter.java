package com.nikpikhmanets.veloroute.place;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nikpikhmanets.veloroute.R;

public class PlaceViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mResources;


    public PlaceViewPagerAdapter(Context mContext, int[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_photo_place, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        Glide.with(mContext).load( mResources[position]).into(imageView);
//        imageView.setImageResource(mResources[position]);

        container.addView(itemView, 0);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
