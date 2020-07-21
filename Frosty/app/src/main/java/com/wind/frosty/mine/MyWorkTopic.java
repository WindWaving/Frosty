package com.wind.frosty.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;

public class MyWorkTopic extends MyRecycleViewFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url= HttpManager.apiUrl+"topic/5?offset="+offset+"&limit="+limit;
    }

    @Override
    public void setAdapter() {
        myRcyView.setAdapter(new topicRecyclerAdapter(data));
    }
}


class topicRecyclerAdapter extends RecyclerView.Adapter {

    JSONArray data;
    public topicRecyclerAdapter(JSONArray data){
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_diplay_item,parent,false);
        myHolder holder=new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)  {
        try {
            ((myHolder)holder).itemId.setText(data.getJSONObject(position).getString("tid"));
            ((myHolder)holder).word.setText(data.getJSONObject(position).getString("content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    class myHolder extends RecyclerView.ViewHolder{

        public TextView word;
        public TextView itemId;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.topic_title);
            itemId=itemView.findViewById(R.id.topic_id);
        }

    }
}