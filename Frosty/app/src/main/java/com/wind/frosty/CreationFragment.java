package com.wind.frosty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wind.frosty.composites.CardComponent;
import com.wind.frosty.creations.ImageActivity;
import com.wind.frosty.creations.MusicActivity;
import com.wind.frosty.creations.TopicActivity;
import com.wind.frosty.creations.YiyanActivity;


public class CreationFragment extends Fragment {
    View rootView;
    CardComponent wordCard;
    CardComponent topicCard;
    CardComponent musicCard;
    CardComponent imageCard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.creation_fragment,container,false);
        }else{
            ViewGroup parent=(ViewGroup) rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordCard=getActivity().findViewById(R.id.word_card);
        topicCard=getActivity().findViewById(R.id.topic_card);
        musicCard=getActivity().findViewById(R.id.music_card);
        imageCard=getActivity().findViewById(R.id.image_card);
        wordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), YiyanActivity.class);
                startActivity(intent);
            }
        });
        topicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), TopicActivity.class);
                startActivity(intent);
            }
        });
        musicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), MusicActivity.class);
                startActivity(intent);
            }
        });
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), ImageActivity.class);
                startActivity(intent);
            }
        });
    }

}
