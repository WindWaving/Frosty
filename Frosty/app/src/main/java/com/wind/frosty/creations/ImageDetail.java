package com.wind.frosty.creations;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;
import com.wind.frosty.network.NetImageAsync;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

public class ImageDetail extends AppCompatActivity {

    HttpManager httpManager;
    String url;
    ImageView picture;
    TextView content;
    TextView author;
    String pid;
    ImageCallback callback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail);

        httpManager=HttpManager.getInstance();

        picture=findViewById(R.id.img_detail_pic);
        content=findViewById(R.id.img_detail_desc);
        author=findViewById(R.id.img_detail_author);

        callback=new ImageCallback();

        pid=getIntent().getStringExtra("pid");
        url=HttpManager.apiUrl+"picture/detail/"+pid;

        getDetail();
    }

    private void getDetail(){
        httpManager.get(url,callback);
    }

    class PicDetNetImage extends NetImageAsync {
        private ImageView view;
        public PicDetNetImage(ImageView view){
            this.view=view;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            this.view.setImageBitmap(bitmap);
        }
    }

    class ImageCallback extends BaseCallback{
        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println("响应错误"+e.getMessage());
        }

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            System.out.println("数据："+jsonObject.getJSONObject("data"));
            JSONObject data=jsonObject.getJSONObject("data");
            content.setText(data.getString("content"));
            author.setText(data.getJSONObject("user").getString("nickname"));

            new PicDetNetImage(picture).execute(data.getString("imgUrl"));
        }

        @Override
        public void onServerError(Call call, String reason) {
            Toast.makeText(getApplicationContext(),"服务器发生错误",Toast.LENGTH_SHORT).show();

        }
    }
}
