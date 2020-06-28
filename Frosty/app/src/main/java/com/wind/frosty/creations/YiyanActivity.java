package com.wind.frosty.creations;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class YiyanActivity extends LoadListView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yiyan_display);
        listView=findViewById(R.id.yiyan_listview);
        httpManager=HttpManager.getInstance();
        callback=new getCallback();
        data=new JSONArray();
        total=0;
        offset=0;
        limit=15;
        url=httpManager.apiUrl+"yiyan";
        loadData();
    }


    //实现initListView
    void initListView(JSONArray data) throws JSONException {
        String[] itemNames=new String[]{"yiyan","author"};
        int[] itemIds=new int[]{R.id.yiyan_content,R.id.yiyan_author};
        ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<data.length();++i){
            HashMap<String,Object> map=new HashMap<String,Object>();
            JSONObject carddata=data.getJSONObject(i);
            map.put(itemNames[0],carddata.getString("content"));
            map.put(itemNames[1],carddata.getJSONObject("user").getString("nickname"));
            listItem.add(map);
        }
        final SimpleAdapter mAdapter=new SimpleAdapter(this,listItem,R.layout.yiyan_display_item,itemNames,itemIds);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                handleScrollState(mAdapter,scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                handleOnScroll(firstVisibleItem,visibleItemCount);
            }
        });
    }
}
