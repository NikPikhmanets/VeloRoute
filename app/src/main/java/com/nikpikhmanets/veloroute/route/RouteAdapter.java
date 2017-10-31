package com.nikpikhmanets.veloroute.route;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemRouteClickListener;

import java.util.List;
import java.util.Locale;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteListViewHolder> {

    private Context context;
    private List<Route> data;
    private OnRecyclerItemRouteClickListener listener;

    public RouteAdapter(Context context) {
        this.context = context;
    }


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
        private RatingBar rbRating;
        private TextView tvRating;

        private RouteListViewHolder(View itemView) {
            super(itemView);

            ivRouteImage = (ImageView) itemView.findViewById(R.id.iv_route_image);
            tvName = (TextView) itemView.findViewById(R.id.tv_route_name);
            tvRoad = (TextView) itemView.findViewById(R.id.tv_route_road);
            tvLength = (TextView) itemView.findViewById(R.id.tv_route_length);
            rbRating = (RatingBar) itemView.findViewById(R.id.rb_rating);
            tvRating = (TextView) itemView.findViewById(R.id.tv_rating);

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

            setNameRoute(route);
            tvRoad.setText(getTypeRoad(route));
            tvLength.setText(String.format("%s " + context.getString(R.string.km), route.getLength()));
            Glide.with(itemView.getContext()).load(route.getImageURL()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRouteImage);
            rbRating.setRating(route.getRating());
            tvRating.setText(String.format(Locale.US, "%.1f", route.getRating()));
        }

        private void setNameRoute(Route route) {
            String locale = context.getResources().getConfiguration().locale.toString();
            switch (locale) {
                case "ru_RU":
                    tvName.setText(route.getName_ru());
                    break;
                case "uk_UA":
                    tvName.setText(route.getName_ua());
                    break;
                default:
                    tvName.setText(route.getName_en());
                    break;
            }
        }

        @NonNull
        private String getTypeRoad(Route route) {
            String typeRoad = "";
            if (route.getRoad() == 0) {
                typeRoad = context.getString(R.string.ground) + " " + route.getDirt();
            } else if (route.getDirt() == 0) {
                typeRoad = context.getString(R.string.asphalt) + " "  + route.getRoad();
            } else {
                typeRoad = context.getString(R.string.ground_and_asphalt) + " " + route.getDirt() + "/" + route.getRoad();
            }
            return typeRoad;
        }
    }
}
