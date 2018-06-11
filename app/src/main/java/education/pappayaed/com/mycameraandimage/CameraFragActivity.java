package education.pappayaed.com.mycameraandimage;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class CameraFragActivity extends AppCompatActivity {

    GPSTracker gpsTracker;
    private static final String TAG = "CameraFragActivity";

    private static final String[] CAMERA_AND_READWRITE =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_frag);

        gpsTracker = new GPSTracker(getApplicationContext());


    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//
//
////        CameraFragment fragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
////
////        fragment.onActivityResult(requestCode, resultCode, data);
//
//
//    }

    public void onLocation(View view) {


//        startActivity(new Intent(this, MainActivity.class));


        Log.e(TAG, "onLocation: " + gpsTracker.getLocation() + "  " + gpsTracker.getLongitude());
//
        Toast.makeText(getApplicationContext(), "" + "onLocation: " + gpsTracker.getLocation() + "  " + gpsTracker.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    public void onPermission(View view) {

        if (RuntimePermissionRequest.checkMultiplePermission(this, CAMERA_AND_READWRITE)) {

            RuntimePermissionRequest.requestPermissionMultiple(this, CAMERA_AND_READWRITE, 2, "Permissins", "Required");

        } else {

            Toast.makeText(getApplicationContext(), "All permission granted", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        RuntimePermissionRequest.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }
}
