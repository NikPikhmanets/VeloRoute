package com.nikpikhmanets.veloroute.adapters;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.models.Route;

import java.util.List;

public class RouteDataAdapter extends RecyclerView.Adapter<RouteDataAdapter.RouteListViewHolder> {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private StorageReference directoryReference = storageReference.child("routes_images/");

    private List<Route> data;
    private OnRecyclerItemClickListener listener;


    @Override
    public RouteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_route, parent, false);
        return new RouteListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RouteListViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Route> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    class RouteListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private ImageView ivRouteImage;
        private TextView tvRoad;
        private TextView tvLength;

        private RouteListViewHolder(View itemView) {
            super(itemView);

            ivRouteImage = (ImageView) itemView.findViewById(R.id.iv_route_image);
            tvName = (TextView) itemView.findViewById(R.id.tv_route_name);
            tvRoad = (TextView) itemView.findViewById(R.id.tv_route_road);
            tvLength = (TextView) itemView.findViewById(R.id.tv_route_length);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(data.get(getAdapterPosition()));
                    }
                }
            });
        }

        void bind(Route route) {
            tvName.setText(route.getName_ru());
            tvRoad.setText("грунт/асфальт: " + route.getDirt() + "/" + route.getRoad());
            tvLength.setText(route.getLength() + " км");
            StorageReference imageReference = directoryReference.child(route.getImage() + ".jpg");
            imageReference.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    ivRouteImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

//        @Override
//        public void onClick(View view) {
//
//            indexItem = view.getId();
//            RouteFragment routeFragment = new RouteFragment();
//            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.content_main, routeFragment)
//                    .commit();
//        }
    }
}
