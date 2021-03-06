package com.wind.frosty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wind.frosty.R;
import com.wind.frosty.creations.MusicCreate;
import com.wind.frosty.creations.MusicDetail;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;

public class MyWorkMusic extends MyRecycleViewFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.my_work_music,container,false);

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
        myRcyView=getActivity().findViewById(R.id.my_recycle_view);
        url= HttpManager.apiUrl+"music/5?offset="+offset+"&limit="+limit;
    }

    @Override
    public void setAdapter() {
        myRcyView.setAdapter(new musicRecyclerAdapter(data));
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
                ((myHolder) holder).itemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), MusicDetail.class);
                        TextView tvId=v.findViewById(R.id.m_id);
                        intent.putExtra("mid",tvId.getText().toString());
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
            public TextView name;
            public TextView itemId;
            public LinearLayout itemCard;
            public ImageButton likebtn;
            public ImageButton favorbtn;
            public myHolder(@NonNull View itemView) {
                super(itemView);
                word=itemView.findViewById(R.id.m_desc);
                name=itemView.findViewById(R.id.m_name);
                itemId=itemView.findViewById(R.id.m_id);
                itemCard=itemView.findViewById(R.id.music_text);
                likebtn=itemView.findViewById(R.id.like_icon);
                favorbtn=itemView.findViewById(R.id.fav_icon);
            }

        }
    }

}

