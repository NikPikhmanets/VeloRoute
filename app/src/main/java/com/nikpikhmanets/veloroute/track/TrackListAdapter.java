package com.nikpikhmanets.veloroute.track;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class TrackListAdapter extends CursorAdapter/*RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder>*/ {

    public TrackListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

//    private List<TrackList> trackLists;
//    private LayoutInflater inflater;
//
//    public TrackListAdapter(List<TrackList> trackLists) {
//        this.trackLists = trackLists;
//        setHasStableIds(true);
//    }
//
//    @Override
//    public TrackListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (inflater == null) {
//            inflater = LayoutInflater.from(parent.getContext());
//        }
//        return TrackListViewHolder.create(inflater, parent);
//    }
//
//    @Override
//    public void onBindViewHolder(TrackListViewHolder holder, int position) {
//        holder.bind(trackLists.get(position));
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return trackLists.get(position).getId();
//    }
//
//    @Override
//    public int getItemCount() {
//        return trackLists == null ? 0 : trackLists.size();
//    }
//
//    static class TrackListViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
//
//        private TextView nameRouteTextView;
//        private TextView createDateRouteTextView;
//
//        private TrackListViewHolder(View itemView) {
//            super(itemView);
//
//            nameRouteTextView = (TextView) itemView.findViewById(R.id.nameMyRoute);
//            createDateRouteTextView = (TextView) itemView.findViewById(R.id.createDateMyRoute);
//
//            itemView.setOnClickListener(this);
//        }
//
//        static TrackListViewHolder create(LayoutInflater inflater, ViewGroup parent) {
//            return new TrackListViewHolder(inflater.inflate(R.layout.item_list_track, parent, false));
//        }
//
//        void bind(TrackList trackList) {
//            nameRouteTextView.setText(trackList.getName());
//        }
//
//
//        @Override
//        public void onClick(View view) {
////            String file = trackLists.get(view.getId()).getName();
//        }
//    }
}
