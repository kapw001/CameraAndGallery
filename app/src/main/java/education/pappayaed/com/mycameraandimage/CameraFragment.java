package education.pappayaed.com.mycameraandimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class CameraFragment extends Fragment implements CameraAndGallary.CameraAndGallaryCallBack {


    private CameraAndGallary cameraAndGallary;
    private ImageView ivImage;
    private Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraAndGallary = new CameraAndGallary(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        button = (Button) view.findViewById(R.id.btnSelectPhoto);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAndGallary.selectImage();
            }
        });


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
