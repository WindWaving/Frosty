package com.wind.frosty.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.InputStream;
import java.net.URL;

//异步获取网络图片，子类重写onPostExcute方法显示图片和构造函数
public abstract class NetImageAsync extends AsyncTask<String,Void,Bitmap> {

    @Override
    public Bitmap doInBackground(String... params) {
        String url= params[0];
        Bitmap bitmap = null;
        try {
            //加载一个网络图片
            InputStream is = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//        this.view.setImageBitmap(bitmap);
//    }
}
