package com.wind.frosty.creations;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.chrisbanes.photoview.PhotoView;
import com.wind.frosty.R;

public class ImagePreview extends AppCompatActivity {
    PhotoView pv;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_preview);
        pv=findViewById(R.id.preview_pic);
        toolbar=findViewById(R.id.preview_bar);
        goback();
        initPhotoView();
    }
        private void goback(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initPhotoView(){
        pv.setImageResource(R.drawable.pick_title);
    }
}
