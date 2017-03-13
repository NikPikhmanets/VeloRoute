package com.nikpikhmanets.veloroute.track;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;

import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder> {

    private List<TrackList> trackLists;
    private LayoutInflater inflater;

    public TrackListAdapter(List<TrackList> trackLists) {
        this.trackLists = trackLists;
        setHasStableIds(true);
    }

    @Override
    public TrackListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return TrackListViewHolder.create(inflater, parent);
    }

    @Override
    public void onBindViewHolder(TrackListViewHolder holder, int position) {
        holder.bind(trackLists.get(position));
    }

    @Override
    public long getItemId(int position) {
        return trackLists.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return trackLists == null ? 0 : trackLists.size();
    }

    static class TrackListViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        private TextView nameRouteTextView;
        private TextView createDateRouteTextView;

        private TrackListViewHolder(View itemView) {
            super(itemView);

            nameRouteTextView = (TextView) itemView.findViewById(R.id.nameMyRoute);
            createDateRouteTextView = (TextView) itemView.findViewById(R.id.createDateMyRoute);

            itemView.setOnClickListener(this);
        }

        static TrackListViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new TrackListViewHolder(inflater.inflate(R.layout.item_list_myroute, parent, false));
        }

        void bind(TrackList trackList) {
            nameRouteTextView.setText(trackList.getName());
        }


        @Override
        public void onClick(View view) {
//            String file = trackLists.get(view.getId()).getName();
        }
    }
}
