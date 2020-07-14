package com.wind.frosty.creations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.goyourfly.multi_picture.ImageLoader;
import com.goyourfly.multi_picture.MultiPictureView;
import com.goyourfly.vincent.Vincent;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicCreate extends CreateActivity {
    TextInputEditText content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_create);

        multiPictureView=findViewById(R.id.topic_create_img);
        imgList=new ArrayList<>();
        multiPictureView.setList(imgList);
        httpManager= HttpManager.getInstance();
        content=findViewById(R.id.topic_create_desc);
        callback=new postCallback();
        url= HttpManager.apiUrl+"topic";

        initPicView();
    }

    @Override
    public void toPost(View view) {
        getImagePaths();
        System.out.println("开始上传"+imgUrls);
        Map<String,String> params=new HashMap();
        if(content.getText().toString().length()==0){
            Toast.makeText(this,"请填写话题内容",Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("content",content.getText().toString());
        params.put("author","5");
        httpManager.postForm(url,params,imgUrls,callback);
    }
}
