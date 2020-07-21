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

public class MyWorkMusic extends MyRecycleViewFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url= HttpManager.apiUrl+"music/5?offset="+offset+"&limit="+limit;
    }

    @Override
    public void setAdapter() {
        myRcyView.setAdapter(new musicRecyclerAdapter(data));
    }
}

class musicRecyclerAdapter extends RecyclerView.Adapter {

    JSONArray data;
    public musicRecyclerAdapter(JSONArray data){
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_display_item,parent,false);
        myHolder holder=new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)  {
        try {
            ((myHolder)holder).itemId.setText(data.getJSONObject(position).getString("mid"));
            ((myHolder)holder).word.setText(data.getJSONObject(position).getString("words"));
            ((myHolder)holder).name.setText(data.getJSONObject(position).getString("name"));
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
        public TextView name;
        public TextView itemId;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.m_desc);
            name=itemView.findViewById(R.id.m_name);
            itemId=itemView.findViewById(R.id.m_id);
        }

    }
}
