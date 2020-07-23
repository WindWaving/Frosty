package com.wind.frosty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wind.frosty.R;
import com.wind.frosty.creations.TopicDetail;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;

public class MyFavorTopic extends MyRecycleViewFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.my_work_topic,container,false);

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
        myRcyView=getActivity().findViewById(R.id.my_topic_recycle);
        url= HttpManager.apiUrl+"favor/5/topic?offset="+offset+"&limit="+limit;

    }

    @Override
    public void setAdapter() {
        topicRecyclerAdapter adapter=new topicRecyclerAdapter(data);
        myRcyView.setAdapter(adapter);
    }

    class topicRecyclerAdapter extends RecyclerView.Adapter{

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
                ((myHolder) holder).itemCard.setTag(position);
                ((myHolder) holder).likebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点赞
                        System.out.println("like");
                    }
                });
                ((myHolder) holder).favorbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //收藏
                        System.out.println("favor");
                    }
                });
                ((myHolder) holder).itemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), TopicDetail.class);
                        TextView idView=v.findViewById(R.id.topic_id);
                        intent.putExtra("tid",Integer.parseInt(idView.getText().toString()));
                        startActivity(intent);
                    }
                });
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
            public ImageButton likebtn;
            public ImageButton favorbtn;
            public RelativeLayout itemCard;
            public myHolder(@NonNull View itemView) {
                super(itemView);
                word=itemView.findViewById(R.id.topic_title);
                itemId=itemView.findViewById(R.id.topic_id);
                likebtn=itemView.findViewById(R.id.like_icon);
                favorbtn=itemView.findViewById(R.id.fav_icon);
                itemCard=itemView.findViewById(R.id.topic_item);
            }

        }
    }
}
