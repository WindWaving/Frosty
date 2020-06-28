package com.wind.frosty;

import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public abstract class BaseCallback {
    public abstract void onFailure(Call call, IOException e);
    public abstract void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException;
    public abstract void onServerError(Call call,String reason);
}
