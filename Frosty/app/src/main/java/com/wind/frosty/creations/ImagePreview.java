package com.wind.frosty.creations;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.chrisbanes.photoview.PhotoView;
import com.wind.frosty.R;
import com.wind.frosty.network.NetImageAsync;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImagePreview extends AppCompatActivity {
    PhotoView pv;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_preview);
        pv=findViewById(R.id.preview_pic);
        toolbar=findViewById(R.id.preview_bar);
        String path=getIntent().getStringExtra("path");
        NetImage net=new NetImage(pv);
        net.execute(path);
        goback();
    }

    //返回按钮
    private void goback(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class NetImage extends NetImageAsync{
        private PhotoView view;

        public NetImage(PhotoView photoView) {
            this.view=photoView;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            this.view.setImageBitmap(bitmap);
        }
    }

//    //获取网络图片，使用异步任务
//    class NetImage extends AsyncTask<String,Void,Bitmap>{
//
//        private PhotoView view;
//
//        public NetImage(PhotoView photoView) {
//            this.view=photoView;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            String url= params[0];
//            Bitmap bitmap = null;
//            try {
//                //加载一个网络图片
//                InputStream is = new URL(url).openStream();
//                bitmap = BitmapFactory.decodeStream(is);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            this.view.setImageBitmap(bitmap);
//        }
//    }
}
