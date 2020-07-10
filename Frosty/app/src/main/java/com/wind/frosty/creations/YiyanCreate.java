package com.wind.frosty.creations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import java.util.HashMap;
import java.util.Map;

public class YiyanCreate extends CreateActivity {
    EditText content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yiyan_create);
        httpManager=HttpManager.getInstance();
        url=httpManager.apiUrl+"yiyan";
        callback=new postCallback();
        content=findViewById(R.id.edit_yiyan);
    }

    @Override
    public void toPost(View view) {
        String value=content.getText().toString();
        Map<String,String>params=new HashMap<>();
        params.put("content",value);
        params.put("authorId","5");
        httpManager.post(url, params,callback);
    }
}
