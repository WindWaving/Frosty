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

public class YiyanActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yiyan_display);
        listView=findViewById(R.id.yiyan_listview);
        String str="[{\"content\":\"test conetnt\",\"author\":\"test author\"},{\"content\":\"test conetnt1\",\"author\":\"test author1\"}]";
        try {
            JSONArray test=new JSONArray(str);
            initListView(test);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void initListView(JSONArray data) throws JSONException {
        String[] itemNames=new String[]{"yiyan","author"};
        int[] itemIds=new int[]{R.id.pick_item_yiyan,R.id.pick_item_author};
        ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<data.length();++i){
            HashMap<String,Object> map=new HashMap<String,Object>();
            JSONObject carddata=data.getJSONObject(i);
            map.put("yiyan",carddata.getString("content"));
            map.put("author",carddata.getString("author"));
            listItem.add(map);
        }
        SimpleAdapter mAdapter=new SimpleAdapter(this,listItem,R.layout.yiyan_display_item,itemNames,itemIds);
        listView.setAdapter(mAdapter);
    }
}
