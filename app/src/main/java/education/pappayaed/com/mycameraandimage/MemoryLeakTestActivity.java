package education.pappayaed.com.mycameraandimage;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pub.devrel.easypermissions.AppSettingsDialog;

public class MemoryLeakTestActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissionClass.PermissionCallback {

    private static Button button;

    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};

    EasyPermissionClass easyPermissionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak_test);


        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(onClickListener);

        MediaTest mediaTest = new MediaTest();

        MediaONOFF mediaONOFF = new MediaONOFF();

        State stateOn = new MediaPlayerOn(mediaTest);
        State stateOFF = new MediaPlayerOn(mediaTest);

        mediaONOFF.setState(stateOn);
        mediaONOFF.doAction();

        TestEnum testEnum = new TestEnum();
        testEnum.start();
        easyPermissionClass = new EasyPermissionClass(this);

        if (testEnum.getTest() == TestEnum.Test.PLAYING) {

        }


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            easyPermissionClass.callPermission(LOCATION_AND_CONTACTS, "Need Permissions", EasyPermissionClass.RC_CAMERA_PERM);


//            Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        easyPermissionClass.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
//            String yes = getString(R.string.yes);
//            String no = getString(R.string.no);
//
//            // Do something after user returned from app settings screen, like showing a Toast.
//            Toast.makeText(
//                    this,
//                    getString(R.string.returned_from_app_settings_to_activity,
//                            hasCameraPermission() ? yes : no,
//                            hasLocationAndContactsPermissions() ? yes : no,
//                            hasSmsPermission() ? yes : no),
//                    Toast.LENGTH_LONG)
//                    .show();
//        }
    }

    @Override
    public void onClick(View v) {

//        Toast.makeText(v.getContext(), "Test", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPermissionSuccess() {
        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionFailed() {

        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

    }
}
