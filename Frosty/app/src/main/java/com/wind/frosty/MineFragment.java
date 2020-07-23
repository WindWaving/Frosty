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
import com.wind.frosty.mine.MyAccount;
import com.wind.frosty.mine.MyCreation;
import com.wind.frosty.mine.MyFavorite;

public class MineFragment extends Fragment {
    View rootView;

    CardComponent accountCard;
    CardComponent favCard;
    CardComponent creationCard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.mine_fragment,container,false);
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
        accountCard=getActivity().findViewById(R.id.account_card);
        favCard=getActivity().findViewById(R.id.fav_card);
        creationCard=getActivity().findViewById(R.id.mine_create_card);
        accountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), MyAccount.class);
                startActivity(intent);
            }
        });
        favCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), MyFavorite.class);
                startActivity(intent);
            }
        });
        creationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), MyCreation.class);
                startActivity(intent);
            }
        });
    }
}
