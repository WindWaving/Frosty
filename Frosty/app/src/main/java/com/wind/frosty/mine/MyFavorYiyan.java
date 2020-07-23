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

public class MyFavorYiyan extends MyRecycleViewFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.my_work_yiyan,container,false);

        }else{
            ViewGroup parent=(ViewGroup) rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void initData() {
        myRcyView=getActivity().findViewById(R.id.my_yiyan_recycle);
        url= HttpManager.apiUrl+"favor/5/yiyan?offset="+offset+"&limit="+limit;

    }

    @Override
    public void setAdapter() {
        myRcyView.setAdapter(new yiyanRecyclerAdapter(data));
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
                ((myHolder)holder).author.setText(data.getJSONObject(position).getString("nickname"));
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
}
