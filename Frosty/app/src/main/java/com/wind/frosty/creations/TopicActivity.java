package com.wind.frosty.creations;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wind.frosty.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView=findViewById(R.id.topic_listview);
//        initListView(data);
    }
    void initListView(JSONArray data) throws JSONException {
        String[] itemNames=new String[]{"topic"};
        int[] itemIds=new int[]{R.id.pick_item_yiyan,R.id.pick_item_author};
        ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<data.length();++i){
            HashMap<String,Object> map=new HashMap<String,Object>();
            JSONObject carddata=data.getJSONObject(i);
            map.put("topic",carddata.getString("topic"));
            listItem.add(map);
        }
        SimpleAdapter mAdapter=new SimpleAdapter(this,listItem,R.layout.topic_diplay_item,itemNames,itemIds);
        listView.setAdapter(mAdapter);
    }
}
