package com.wind.frosty.creations;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.goyourfly.multi_picture.ImageLoader;
import com.goyourfly.multi_picture.MultiPictureView;
import com.goyourfly.vincent.Vincent;
import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;
import com.wind.frosty.network.NetImageAsync;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class TopicDetail extends AppCompatActivity {
    TextView topicDetail;
    TextView topicAuthor;
    HttpManager httpManager;
    TopicCallback callback;
    MultiPictureView multiPictureView;
    String url;
    int tid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_detail);

        tid=getIntent().getIntExtra("tid",0);
        httpManager=HttpManager.getInstance();
        url=HttpManager.apiUrl+"topic/detail/"+tid;
        topicDetail=findViewById(R.id.topic_detail_desc);
        topicAuthor=findViewById(R.id.topic_detail_author);
        callback=new TopicCallback();
        multiPictureView=findViewById(R.id.topic_detail_image);

        initPicView();
        getDetail();
    }

    private void getDetail(){
        httpManager.get(url,callback);
    }

    public void initPicView(){
        MultiPictureView.setImageLoader(new ImageLoader() {
            @Override
            public void loadImage(@NotNull ImageView imageView, @NotNull Uri uri) {
                Vincent.with(imageView.getContext())
                        .load(uri)
                        .placeholder(R.drawable.cardbkg)
                        .error(R.drawable.settings)
                        .into(imageView);
            }
        });
        //添加相机和相册图片
        multiPictureView.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(@NotNull View view) {
            }
        });
        multiPictureView.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
            @Override
            public void onDeleted(View view,int index) {
            }
        });
        multiPictureView.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
            @Override
            public void onItemClicked(View view, int index, ArrayList<Uri> uris) {
                Intent intent=new Intent(getApplicationContext(),ImagePreview.class);
                intent.putExtra("path",uris.get(index).toString());
                startActivity(intent);
            }
        });
    }


    class TopicCallback extends BaseCallback{
        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println("响应错误"+e.getMessage());
        }

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            JSONObject data=jsonObject.getJSONObject("data");
            System.out.println("详情"+data);
            topicDetail.setText(data.getString("content"));
            topicAuthor.setText(data.getJSONObject("user").getString("nickname"));
            String[] tmp=data.getString("images").split(";");
            List<Uri> imgUrls=new ArrayList();
            for(int i=0;i<tmp.length;i+=2){
                imgUrls.add(Uri.parse(tmp[i]));
            }
            System.out.println("图片地址们，"+imgUrls);
            multiPictureView.setList(imgUrls);

        }

        @Override
        public void onServerError(Call call, String reason) {
            Toast.makeText(getApplicationContext(),"服务器发生错误",Toast.LENGTH_SHORT).show();
        }
    }
}
