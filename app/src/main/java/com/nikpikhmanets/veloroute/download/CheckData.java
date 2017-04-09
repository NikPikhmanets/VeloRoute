package com.nikpikhmanets.veloroute.download;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.route.Route;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.nikpikhmanets.veloroute.R.array.Directory;

public class CheckData {

    final private int ID_GET_META_DATA = 11;
    final private int ID_DOWNLOAD_DATA = 22;
    private static final String TAG = "tag";
    private String typeFLASH = "directory";
    private String pathFileGpx;
    private String pathFilePhoto;

    private Context context;

    private ProgressDialog mProgressDialog;

    private List<Route> routeList;
    private List<Place> placeList;

    private List<String> missingFileList = new ArrayList<>();
    private long sizeByte;
    private int counterFile;

    private CheckGpxFile checkGpxFile = new CheckGpxFile();
    private CheckPlacePhoto checkPlacePhoto = new CheckPlacePhoto();

    public CheckData(Context context) {
        this.context = context;
    }

    public void setRouteList(List<Route> routeList, List<Place> placeList) {
        this.routeList = routeList;
        this.placeList = placeList;
    }

    public void startCheckData() {
        getTypeFlashSetting();
        if (checkForDirectory()) {
            missingFileList.clear();
            checkGpxFile.setRouteList(routeList);
            missingFileList = checkGpxFile.checkGpxFile(pathFileGpx);

            checkPlacePhoto.setPlaceList(placeList);
            missingFileList.addAll(checkPlacePhoto.checkPhoto(pathFilePhoto));

            if (missingFileList.size() != 0) {
                sizeByte = 0;
                counterFile = 0;
                getMetaData(missingFileList.get(counterFile));
            } else
                hideProgressDialog();
        } else {
            hideProgressDialog();
            showMessageDialog(context.getString(R.string.oops), context.getString(R.string.err_check_data));
        }
    }

    private void getMetaData(String file) {
        StorageReference gpxReference = FirebaseStorage.getInstance().getReference(file);
        gpxReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                downloadData(storageMetadata.getSizeBytes(), ID_GET_META_DATA);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                downloadData(0, ID_GET_META_DATA);
            }
        });
    }

    private void downloadFile(String file) {
        String path = context.getApplicationInfo().dataDir;
        File fileGpx = new File(path + "/" + file);

        StorageReference gpxReference = FirebaseStorage.getInstance().getReference("/" + file);
        gpxReference.getFile(fileGpx).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                downloadData(0, ID_DOWNLOAD_DATA);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                downloadData(0, ID_DOWNLOAD_DATA);
            }
        });
    }

    private void downloadData(long size, int id) {
        switch (id) {
            case ID_GET_META_DATA:
                sizeByte += size;
                counterFile++;
                if (counterFile != missingFileList.size()) {
                    getMetaData(missingFileList.get(counterFile));
                } else {
                    hideProgressDialog();
                    showSizeForDownloadDialog(sizeByte);
                }
                break;
            case ID_DOWNLOAD_DATA:
                counterFile++;
                if (counterFile != missingFileList.size()) {
                    downloadFile(missingFileList.get(counterFile));
                }
                showDownloadDialog(missingFileList.size() - 1, counterFile);
                break;
        }
    }

    private boolean checkForDirectory() {
        String GPX_DIR = "/gpx_file/";
        String PHOTO_DIR = "/image_place/";
        if (typeFLASH.equals(context.getResources().getStringArray(Directory)[0])) {
            pathFileGpx = context.getApplicationInfo().dataDir + GPX_DIR;
            pathFilePhoto = context.getApplicationInfo().dataDir + PHOTO_DIR;
            if (checkDir(pathFileGpx) || checkDir(pathFilePhoto))
                return false;
        } else {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                pathFileGpx = Environment.getExternalStorageDirectory().getAbsolutePath() + GPX_DIR;
                pathFilePhoto = Environment.getExternalStorageDirectory().getAbsolutePath() + PHOTO_DIR;
                if (checkDir(pathFileGpx) || checkDir(pathFilePhoto))
                    return false;
            }
        }
        return true;
    }

    private boolean checkDir(String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
//                    Toast.makeText(context, "Error create dir", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }


    private void getTypeFlashSetting() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        typeFLASH = prefs.getString("directory", context.getResources().getStringArray(Directory)[0]);
    }

    public void showProgressDialog(String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(caption);
        mProgressDialog.show();
    }

    private void showDownloadDialog(int max, int count) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // устанавливаем стиль
            mProgressDialog.setMessage(context.getString(R.string.download_wait));  // задаем текст
            mProgressDialog.setMax(max);
            mProgressDialog.setProgress(0);
            mProgressDialog.setProgressPercentFormat(null);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            mProgressDialog.setProgress(count);
        }
        if (max == count) {
            mProgressDialog.dismiss();
            Log.d(TAG, "showDownloadDialog: completed");
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

    }

    private void showMessageDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        ad.setCancelable(false);
        ad.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void showSizeForDownloadDialog(Long size) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Необходимо обновить базу данных размером " + checkSize(size))
                .setPositiveButton("обновить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        counterFile = 0;
                        downloadFile(missingFileList.get(counterFile));
                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    private String checkSize(Long size) {

        String sizeStr = "";
        if (size < 1024) {
            sizeStr = size + " байт";
        } else if (size > 1024 && size < 1024 * 1000) {
            sizeStr = size / 1024 /* + "," + size % 1024*/ + " кбайт";
        } else if (size > 1024 * 1000) {
            sizeStr = size / 1024000/* + "," + size % 102400*/ + " мбайт";
        }
        return sizeStr;
    }
}
