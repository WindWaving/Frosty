package com.wind.frosty;

import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public abstract class BaseCallback {
    public void onFailure(Call call, IOException e){
        System.out.println("响应错误: "+e.getMessage());
    }
    public abstract void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException;
    public void onServerError(Call call,String reason){
        System.out.println("服务器错误 "+reason);
    }
}
