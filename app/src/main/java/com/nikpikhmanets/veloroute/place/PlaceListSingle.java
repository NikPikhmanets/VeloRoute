package com.nikpikhmanets.veloroute.place;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlaceListSingle {

    private static List<Place> placeList;

    public static List<Place> getListPlace() {
        if (placeList == null) {
            placeList = new ArrayList<>();
            downloadData();
        }
        return placeList;
    }

    private static void downloadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference routesReference = ref.child("place");
        routesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                placeList.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        placeList.add(snapshot.getValue(Place.class));
                    }
                } catch (Exception i) {
//                    Toast.makeText(context, "Ошибка загрузки данных #1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(context, "Ошибка загрузки данных #2", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
