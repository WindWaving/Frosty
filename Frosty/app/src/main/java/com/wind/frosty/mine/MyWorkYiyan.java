package com.wind.frosty.mine;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;


public class MyWorkYiyan extends MyRecycleViewFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url= HttpManager.apiUrl+"yiyan/5?offset="+offset+"&limit="+limit;
    }

    @Override
    public void setAdapter() {
        myRcyView.setAdapter(new yiyanRecyclerAdapter(data));
    }


}

class yiyanRecyclerAdapter extends RecyclerView.Adapter {

    JSONArray data;
    public yiyanRecyclerAdapter(JSONArray data){
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.yiyan_display_item,parent,false);
        myHolder holder=new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)  {
        try {
            ((myHolder)holder).author.setText(data.getJSONObject(position).getJSONObject("user").getString("nickname"));
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
        public TextView author;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.yiyan_content);
            author=itemView.findViewById(R.id.yiyan_author);
        }

    }
}
