package com.wind.frosty.creations;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageCreate extends CreateActivity {

    TextInputEditText content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_create);

        imgList=new ArrayList<>();
        multiPictureView=findViewById(R.id.pic_create_img);
        httpManager=HttpManager.getInstance();
        multiPictureView.setList(imgList);
        content=findViewById(R.id.pic_create_desc);
        callback=new postCallback();
        url= HttpManager.apiUrl+"picture";

        initPicView();
    }

    @Override
    public void toPost(View view) {
        getImagePaths();
        System.out.println("开始上传"+imgUrls);
        if(imgUrls.size()==0){
            Toast.makeText(this,"必须选择一张图片",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> params=new HashMap();
        params.put("content",content.getText().toString());
        params.put("author","5");
        httpManager.postForm(url,params,imgUrls,callback);
    }
}
