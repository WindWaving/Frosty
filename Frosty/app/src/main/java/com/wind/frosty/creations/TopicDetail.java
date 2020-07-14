package com.wind.frosty.creations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

public class TopicDetail extends AppCompatActivity {
    TextView topicDetail;
    TextView topicAuthor;
    HttpManager httpManager;
    TopicCallback callback;
    String url;
    int tid;
    int offset=0,limit=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_detail);

        tid=getIntent().getIntExtra("tid",0);
        httpManager=HttpManager.getInstance();
        url=HttpManager.apiUrl+"topic/detail/"+tid;
        topicDetail=findViewById(R.id.topic_detail);
        topicAuthor=findViewById(R.id.topic_author);
        callback=new TopicCallback();

        getDetail();
    }

    private void getDetail(){
        httpManager.get(url,callback);
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

        }

        @Override
        public void onServerError(Call call, String reason) {
            Toast.makeText(getApplicationContext(),"服务器发生错误",Toast.LENGTH_SHORT).show();
        }
    }
}
