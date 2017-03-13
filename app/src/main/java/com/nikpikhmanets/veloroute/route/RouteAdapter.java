package com.nikpikhmanets.veloroute.route;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemRouteClickListener;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteListViewHolder> {

    private List<Route> data;
    private OnRecyclerItemRouteClickListener listener;


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

    public List<Route> getData() {
        return data;
    }

    public void setData(List<Route> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRecyclerItemRouteClickListener listener) {
        this.listener = listener;
    }

    class RouteListViewHolder extends RecyclerView.ViewHolder {

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
            tvRoad.setText(getTypeRoad(route));
            tvLength.setText(String.format("%s км", route.getLength()));
            Glide.with(itemView.getContext()).load(route.getImageURL()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRouteImage);
        }

        @NonNull
        private String getTypeRoad(Route route) {
            String typeRoad = "";
            if (route.getRoad() == 0) {
                typeRoad = "грунт: " + route.getDirt();
            } else if (route.getDirt() == 0) {
                typeRoad = "асфальт: " + route.getRoad();
            } else {
                typeRoad = "грунт/асфальт: " + route.getDirt() + "/" + route.getRoad();
            }
            return typeRoad;
        }
    }
}
