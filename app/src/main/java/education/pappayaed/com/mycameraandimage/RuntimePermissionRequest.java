package education.pappayaed.com.mycameraandimage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yasar on 28/10/17.
 */

public class RuntimePermissionRequest {

    private static final String TAG = "RuntimePermissionReques";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    public static boolean checkSinglePermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
        } else {
            return false;
        }
    }

    public static boolean checkMultiplePermission(Context context, String[] permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean checkMultiplePermissionWithshouldShowRequestPermissionRationale(Context context, String[] permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permission.length; i++) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission[i])) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static void requestPermissionSingle(final Context context, final String permission, final int requestCode, final String title, final String message) {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                permission)) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{permission},
                                    requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{permission},
                    requestCode);
        }


    }

    public static void requestPermissionMultiple(final Context context, final String[] permission, final int requestCode, final String title, final String message) {


        int count = 0;
        for (int i = 0; i < permission.length; i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    permission[i])) {
                count++;
            }
        }

        Log.e(TAG, "requestPermissionMultiple: " + count);

        if (count > 0) {

//            AlertDialog.Builder builder;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
//            } else {
//                builder = new AlertDialog.Builder(context);
//            }
//            builder.setTitle(title)
//                    .setMessage(message)
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            ActivityCompat.requestPermissions((Activity) context,
                                    permission,
                                    requestCode);
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            // do nothing
////                            ActivityCompat.requestPermissions((Activity) context,
////                                    permission,
////                                    requestCode);
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();


        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    permission,
                    requestCode);

        }


    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static void onRequestPermissionsResult(final Context context, int requestCode,
                                                  String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//
//                } else {
//
//
//                    if (checkMultiplePermissionWithshouldShowRequestPermissionRationale(context, permissions)) {
////                        showStoragePermissionRationale();
//
//                        Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
//
//                    } else {
//
////                        Snackbar snackbar = Snackbar.make(, getResources().getString(R.string.message_no_storage_permission_snackbar), Snackbar.LENGTH_LONG);
////                        snackbar.setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {
////
////                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
////                                intent.setData(uri);
////                                ((Activity) context).startActivityForResult(intent, 34);
////                            }
////                        });
////                        snackbar.show();
//
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//                        intent.setData(uri);
//                        ((Activity) context).startActivityForResult(intent, 34);
//                    }
//                }
//
//                // permission denied, boo! Disable the
//                // functionality that depends on this permission.
//            }
//            return;
//        }
//
//        // other 'case' lines to check for other
//        // permissions this app might request.




        if(permissions.length == 0){
            return;
        }
        boolean allPermissionsGranted = true;
        if(grantResults.length>0){
            for(int grantResult: grantResults){
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if(!allPermissionsGranted){
            boolean somePermissionsForeverDenied = false;
            for(String permission: permissions){
                if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)){
                    //denied
                    Log.e("denied", permission);
                }else{
                    if(ActivityCompat.checkSelfPermission((Activity) context, permission) == PackageManager.PERMISSION_GRANTED){
                        //allowed
                        Log.e("allowed", permission);
                    } else{
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if(somePermissionsForeverDenied){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("You have forcefully denied some of the required permissions " +
                                "for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", context.getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        } else {
            switch (requestCode) {
                //act according to the request code used while requesting the permission(s).
            }
        }


    }
}


