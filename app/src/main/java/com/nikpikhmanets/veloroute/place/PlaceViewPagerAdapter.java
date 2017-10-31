package com.nikpikhmanets.veloroute.place;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;

import java.io.File;
import java.util.List;

public class PlaceViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> imageList;


    public PlaceViewPagerAdapter(Context mContext, List<String> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_photo_place, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);

        File file = new File(mContext.getApplicationInfo().dataDir + "/image_place/", imageList.get(position));
        if (file.exists()) {
            Glide.with(mContext).load(file).into(imageView);
        } else {

            StorageReference reference = FirebaseStorage.getInstance().getReference("/image_place/" + imageList.get(position));
            Glide.with(mContext).using(new FirebaseImageLoader()).load(reference).into(imageView);
        }

        container.addView(itemView, 0);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
