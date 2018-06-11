package education.pappayaed.com.mycameraandimage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, CameraAndGallary.CameraAndGallaryCallBack {

    private ImageView ivImage;

    private static final String TAG = "MainActivity";
    private static final String[] CAMERA_AND_READWRITE =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private static final int RC_CAMERA_AND_READWRITE_PERM = 124;

    private CameraAndGallary cameraAndGallary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        cameraAndGallary = new CameraAndGallary(this, this);





























        List<String> list = Arrays.asList("Test", "Android", "IOS", "Amazon");

        List<String> f = new ArrayList<>();


//        Iterator iterator = list.iterator();
//
//        while (iterator.hasNext()) {
//
//            if (iterator.next().toString().contains("a")) {
//
//            }
//
//        }

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        if (!isOnline()) {

                            Log.d(TAG, "rewriting request");

                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                            request = request.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }

                        return chain.proceed(request);

                    }
                })

                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response originalResponse = chain.proceed(chain.request());
                        String cacheControl = originalResponse.header("Cache-Control");

                        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + 10)
                                    .build();
                        } else {
                            return originalResponse;
                        }
                    }
                })

                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        api.getList().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() &&
                        response.raw().networkResponse() != null &&
                        response.raw().networkResponse().code() ==
                                HttpURLConnection.HTTP_NOT_MODIFIED) {
                    // the response hasn't changed, so you do not need to do anything
                    Log.e(TAG, "onResponse: Old  ");
//                    return;
                } else {

                    Log.e(TAG, "onResponse: new  ");
                }


                Log.e(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    interface Api {
        @GET("/api/users?page=1")
        Call<String> getList();
    }

    public void filter(Collection collection, Predicate predicate) {

        if ((collection != null) && (predicate != null))
            for (Iterator it = collection.iterator(); it.hasNext(); )
                if (!predicate.evaluate(it.next()))
                    it.remove();
    }

    public interface Predicate {
        boolean evaluate(Object o);
    }

    public void onSelectPhoto(View view) {

        locationAndContactsTask();

    }


    @AfterPermissionGranted(RC_CAMERA_AND_READWRITE_PERM)
    public void locationAndContactsTask() {
        if (EasyPermissions.hasPermissions(this, CAMERA_AND_READWRITE)) {

            cameraAndGallary.selectImage();
            // Have permissions, do the thing!
//            Toast.makeText(this, "TODO: Camera and Read and Write things", Toast.LENGTH_LONG).show();

        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_CAMERA_AND_READWRITE_PERM,
                    CAMERA_AND_READWRITE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        cameraAndGallary.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSelectFromGalleryResult(Bitmap bitmap) {
        ivImage.setImageBitmap(bitmap);
    }


}
