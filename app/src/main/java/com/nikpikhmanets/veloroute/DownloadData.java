package com.nikpikhmanets.veloroute;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.route.Route;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadData {
    private Context context;
    private static DownloadData instance = null;

    private List<Route> routesList;

    private List<String> listMissingFile = new ArrayList<>();
    private List<Long> checkList = new ArrayList<>();

    private ProgressDialog progressDialog;

    public static DownloadData getInstance() {
        if (instance == null) {
            instance = new DownloadData();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setRoutesList(List<Route> routesList) {
        this.routesList = routesList;
    }

    public void download() {
        createDir();
        checkGpxFileInMemory();
        beginDownload();
    }

    private void checkGpxFileInMemory() {

        File fileGpx;
        String filePath;
        String path = context.getApplicationInfo().dataDir;
        fileGpx = new File(path + "/gpx_file/");
        if (fileGpx.exists()) {
            for (int i = 0; i < routesList.size(); i++) {
                filePath = path + "/gpx_file/" + routesList.get(i).getGpx() + ".gpx";
                fileGpx = new File(filePath);
                if (!(fileGpx.exists() && fileGpx.isFile())) {
                    listMissingFile.add(routesList.get(i).getGpx() + ".gpx");
                }
            }
        }
    }

    private void createDir() {
        String dir = context.getApplicationInfo().dataDir + "/gpx_file/";
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                Toast.makeText(context, "Error create dir", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void beginDownload() {

        if (listMissingFile != null && listMissingFile.size() != 0) {

            for (int i = 0; i < listMissingFile.size(); i++) {
                GetMetaData.getMetaDataGpxFile(listMissingFile.get(i));
            }
        }
    }

    void setData(Long sizeBytes) {
        checkList.add(sizeBytes);
        if (checkList.size() == listMissingFile.size()) {
            long size = 0;
            for (int i = 0; i < checkList.size(); i++) {
                size += checkList.get(i);
            }
            showAlertDialog(size);
        }
    }

    private void showAlertDialog(Long size) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Внимание!")
                .setMessage("\tНекоторые данные недоступны оффлайн! \nНеобходимо загрузить " + checkSize(size))
                .setPositiveButton("обновить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadGpxFile();
                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
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

    private void downloadGpxFile() {

        for (int i = 0; i < listMissingFile.size(); i++) {

            String path = context.getApplicationInfo().dataDir;
            File fileGpx = new File(path + "/gpx_file/" + listMissingFile.get(i));

            StorageReference gpxReference = FirebaseStorage.getInstance().getReference("gpx_file/" + listMissingFile.get(i));
            final int count = i;
            gpxReference.getFile(fileGpx).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    showProgressDialog(listMissingFile.size() - 1, count);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showProgressDialog(listMissingFile.size() - 1, count);
                }
            });
        }
    }

    private void showProgressDialog(int max, int count) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // устанавливаем стиль
            progressDialog.setMessage("Загружаю. Подождите...");  // задаем текст
            progressDialog.setMax(max);
            progressDialog.setProgress(0);
            progressDialog.setProgressPercentFormat(null);
            progressDialog.show();
        }
        else {
            progressDialog.setProgress(count);
        }
        if(max == count)
            progressDialog.dismiss();
    }
}
