package com.wind.frosty.creations;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.BaseCallback;
import com.wind.frosty.network.HttpManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

public abstract class CreateActivity extends AppCompatActivity {
    HttpManager httpManager;
    String url;
    postCallback callback;
    public abstract void toPost(View view);

    class postCallback extends BaseCallback{
        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println("发送失败 "+e.getMessage());
        }

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            System.out.println("保存成功");
            finish();
        }

        @Override
        public void onServerError(Call call, String reason) {
            System.out.println("服务器错误 "+reason);
        }
    }
}
