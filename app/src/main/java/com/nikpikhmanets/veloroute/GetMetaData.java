package com.nikpikhmanets.veloroute;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

class GetMetaData {

    static void getMetaDataGpxFile(String file) {

        StorageReference gpxReference = FirebaseStorage.getInstance().getReference("gpx_file/" + file);
        gpxReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                DownloadData.getInstance().setData(storageMetadata.getSizeBytes());
            }
        });
        gpxReference.getMetadata().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DownloadData.getInstance().setData((long) 0);
            }
        });
    }
}
