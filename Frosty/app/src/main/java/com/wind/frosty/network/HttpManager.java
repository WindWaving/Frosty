package com.wind.frosty.network;

import android.os.Looper;

import com.wind.frosty.BaseCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpManager {
    private static HttpManager instance;
    public static String apiUrl="http://192.168.56.1:3000/frosty/api/";
    private OkHttpClient client;
    private Handler mainHandler;
    //单例模式
    public static HttpManager getInstance(){
        if(instance==null){
            return new HttpManager();
        }
        return instance;
    }
    HttpManager(){
        client=new OkHttpClient();
    }

    /*
    @params:请求地址,回调函数
     */
    public void get(String url, final BaseCallback callback){
        String newUrl=url;
        Request request=new Request.Builder()
                .url(newUrl)
                .build();
        doRequest(request,callback);
    }

    public void post(String url, Map<String,String> params, final BaseCallback callback){
        FormBody.Builder builder=new FormBody.Builder();
        if(params!=null){
            for(Map.Entry<String,String>entry:params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        doRequest(request,callback);
    }

    public void patch(String url,Map<String,String>params,final BaseCallback callback){
        FormBody.Builder builder=new FormBody.Builder();
        if(params!=null){
            for(Map.Entry<String,String>entry:params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        doRequest(request,callback);
    }

    public void delete(String url,final BaseCallback callback){
        FormBody body=new FormBody.Builder().build();
        Request request=new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        doRequest(request,callback);
    }
    /*
    异步请求并响应
    @params: 请求，回调函数用于响应请求的数据
     */
    private void doRequest(Request request, final BaseCallback callback){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
                if(response.isSuccessful()){
                    String str=response.body().string();
                    JSONObject obj= null;
                    try {
                        obj = new JSONObject(str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("响应错误"+e.getMessage());
                    }
                    final JSONObject finalObj = obj;
                    //返回数据给ui线程
                    new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(finalObj.getInt("err")==0){
                                    JSONObject info = null;
                                    try{
                                        info=finalObj.getJSONObject("info");
                                    }catch (Exception e){
                                        info=new JSONObject("{\"info\":\""+finalObj.getString("info")+"\"}");
                                    }
                                    callback.onSuccess(call, info);
                                }else{
                                    callback.onServerError(call, finalObj.getString("info"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
}
