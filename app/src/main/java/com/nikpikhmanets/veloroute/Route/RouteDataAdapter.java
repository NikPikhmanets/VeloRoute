package com.nikpikhmanets.veloroute.Route;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.fragments.RouteFragment;

import java.util.List;

public class RouteDataAdapter extends RecyclerView.Adapter<RouteDataAdapter.RouteListViewHolder> {

    private List<RouteData> route;
    private LayoutInflater inflater;
    private Context context;

    public RouteDataAdapter(List<RouteData> route, Context context) {
        this.route = route;
        this.context = context;
        setHasStableIds(true);
    }

    public RouteDataAdapter() {

    }

    @Override
    public RouteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.item_list_route, parent, false);
        return new RouteListViewHolder(v);
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

    class RouteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

            itemView.setOnClickListener(this);
        }

        void bind(RouteData route) {
            imageRoute.setImageBitmap(route.getImageRoute());
            nameRoute.setText(route.getNameRoute());
            ground.setText(route.getGround());
            distance.setText(route.getDistance());
        }

        @Override
        public void onClick(View view) {

//            indexItem = view.getId();
            RouteFragment routeFragment = new RouteFragment();
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, routeFragment)
                    .commit();
        }
    }
}
