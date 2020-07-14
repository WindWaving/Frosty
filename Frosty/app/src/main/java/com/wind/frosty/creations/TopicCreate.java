package com.wind.frosty.creations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.goyourfly.multi_picture.ImageLoader;
import com.goyourfly.multi_picture.MultiPictureView;
import com.goyourfly.vincent.Vincent;
import com.wind.frosty.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TopicCreate extends CreateActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_create);

        multiPictureView=findViewById(R.id.topic_image_view);
        imgList=new ArrayList<>();
        multiPictureView.setList(imgList);

        initPicView();
    }

    @Override
    public void toPost(View view) {

    }
}
