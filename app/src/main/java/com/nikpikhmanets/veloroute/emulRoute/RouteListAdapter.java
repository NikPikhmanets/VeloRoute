package com.nikpikhmanets.veloroute.emulRoute;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;

import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter <RouteListAdapter.RouteListViewHolder>{

    private List<EmulListRoute> route;
    private LayoutInflater inflater;

    public RouteListAdapter(List<EmulListRoute> route) {
        this.route = route;
        setHasStableIds(true);
    }


    @Override
    public RouteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return RouteListViewHolder.create(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RouteListViewHolder holder, int position) {
        holder.bind(route.get(position));
    }

    @Override
    public long getItemId(int position) {
        return route.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return route == null ? 0 : route.size();
    }

    static class RouteListViewHolder extends RecyclerView.ViewHolder {

        private TextView nameRoute;
        private ImageView imageRoute;
        private TextView ground;
        private TextView distance;

        private RouteListViewHolder(View itemView) {
            super(itemView);

            imageRoute = (ImageView) itemView.findViewById(R.id.imageRoute);
            nameRoute = (TextView) itemView.findViewById(R.id.nameRoute);
            ground = (TextView) itemView.findViewById(R.id.groundRoute);
            distance = (TextView) itemView.findViewById(R.id.distanceRoute);
        }

        static RouteListViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new RouteListViewHolder(inflater.inflate(R.layout.item_listroute, parent, false));
        }

        void bind(EmulListRoute route) {
            imageRoute.setImageResource(route.getImageRoute());
            nameRoute.setText(route.getNameRoute());
            ground.setText(route.getGround());
            distance.setText(route.getDistance());
        }
    }
}
