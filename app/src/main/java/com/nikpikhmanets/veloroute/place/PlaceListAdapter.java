package com.nikpikhmanets.veloroute.place;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemPlaceClickListener;

import java.io.File;
import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private List<Place> place;
    private OnRecyclerItemPlaceClickListener placeListener;

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_place, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        holder.bind(place.get(position));
    }

    @Override
    public int getItemCount() {
        return place == null ? 0 : place.size();
    }

    public void setData(List<Place> place) {
        this.place = place;
        notifyDataSetChanged();
    }

    public void setOnItemPlaceClickListener(OnRecyclerItemPlaceClickListener placeListener) {
        this.placeListener = placeListener;
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagePlace;
        private TextView textNamePlace;
        private TextView textDescrPlace;

        private PlaceViewHolder(View itemView) {
            super(itemView);

            imagePlace = (ImageView) itemView.findViewById(R.id.imageItemPlace);
            textNamePlace = (TextView) itemView.findViewById(R.id.textNameItemPlace);
            textDescrPlace = (TextView) itemView.findViewById(R.id.textDescrItemPlace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (placeListener != null) {
                        placeListener.onItemClick(place.get(getAdapterPosition()));
                    }
                }
            });
        }

        void bind(Place place) {
            File file = new File(itemView.getContext().getApplicationInfo().dataDir + "/image_place/", place.getImageList().get(0));
            if (file.exists()) {
                Glide.with(itemView.getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagePlace);
            } else {
                StorageReference reference = FirebaseStorage.getInstance().getReference("/image_place/" + place.getImageList().get(0));
                Glide.with(itemView.getContext()).using(new FirebaseImageLoader()).load(reference).into(imagePlace);
            }
            textNamePlace.setText(place.getName());
            textDescrPlace.setText(place.getDescription());
        }
    }
}
