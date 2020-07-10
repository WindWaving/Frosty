package com.wind.frosty.creations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicActivity extends LoadListView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_display);
        listView=findViewById(R.id.topic_listview);
        httpManager= HttpManager.getInstance();
        callback=new getCallback();
        data=new JSONArray();
        limit=15;
        url=httpManager.apiUrl+"topic";
        loadData();

    }
    void initListView(JSONArray data) throws JSONException {
        String[] itemNames=new String[]{"topic"};
        int[] itemIds=new int[]{R.id.topic_title};
        ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
        System.out.println("topic :"+data.length());
        for(int i=0;i<data.length();++i){
            HashMap<String,Object> map=new HashMap<String,Object>();
            JSONObject carddata=data.getJSONObject(i);
            map.put(itemNames[0],carddata.getString("content"));
            listItem.add(map);
        }
        final SimpleAdapter mAdapter=new SimpleAdapter(this,listItem,R.layout.topic_diplay_item,itemNames,itemIds);
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

    @Override
    void toCreate(View view) {
        Intent intent=new Intent(this,TopicCreate.class);
        startActivity(intent);
    }

}
