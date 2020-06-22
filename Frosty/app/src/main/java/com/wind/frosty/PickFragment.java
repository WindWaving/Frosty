package com.wind.frosty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PickFragment extends Fragment {
    ListView pickListView;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.pick_fragment,container,false);
            //init listview
            pickListView=rootView.findViewById(R.id.pick_listview);
            String str="[{\"content\":\"test conetnt\",\"author\":\"test author\"},{\"content\":\"test conetnt1\",\"author\":\"test author1\"}]";
            try {
                JSONArray test=new JSONArray(str);
                initListView(test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
        return rootView;
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
        SimpleAdapter mAdapter=new SimpleAdapter(getContext(),listItem,R.layout.yiyan_item,itemNames,itemIds);
        pickListView.setAdapter(mAdapter);
    }
}
