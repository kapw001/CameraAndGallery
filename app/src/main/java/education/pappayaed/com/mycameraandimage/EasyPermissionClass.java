package education.pappayaed.com.mycameraandimage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yasar on 10/3/18.
 */

public class EasyPermissionClass implements EasyPermissions.PermissionCallbacks {

    private Activity mContext;
    private PermissionCallback permissionCallBack;
    public static final int RC_CAMERA_PERM = 453;

    private static EasyPermissionClass easyPermissionClass;

//    private EasyPermissionClass(Activity context) {
//        this.mContext = context;
//
////        if (context instanceof Activity) {
////
////            this.mContext = (Activity) context;
////        } else if (context instanceof Fragment) {
////
////            this.mContext = ((Fragment) context).getActivity();
////        }
//
//    }

//    public static <T> EasyPermissionClass getEasyPermissionClass(Activity context) {
//
//        if (easyPermissionClass == null) {
//            easyPermissionClass = new EasyPermissionClass(context);
//        }
//
//        return easyPermissionClass;
//    }


    public EasyPermissionClass(Activity mContext) {
        this.mContext = mContext;
        this.permissionCallBack = (PermissionCallback) mContext;
    }

    public EasyPermissionClass(Fragment mContext) {
        this.mContext = mContext.getActivity();
        this.permissionCallBack = (PermissionCallback) mContext;
    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void callPermission(String[] perms, String msg, int requestCode) {
        if (EasyPermissions.hasPermissions(mContext, perms)) {

            permissionCallBack.onPermissionSuccess();

        } else {
            EasyPermissions.requestPermissions(
                    mContext,
                    msg,
                    requestCode,
                    perms);
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {


    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        this.permissionCallBack.onPermissionFailed();

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(mContext, perms)) {
            new AppSettingsDialog.Builder(mContext).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    interface PermissionCallback {

        void onPermissionSuccess();

        void onPermissionFailed();

    }
}
