package com.wind.frosty.mine;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

/**
 * 子类继承方法：
 * 实现initData方法，给url和myRcyView赋值
 * 实现setAdapter方法
 * 实现适配器类
 */
public abstract class MyRecycleViewFragment extends Fragment {
    View rootView;
    RecyclerView myRcyView;
    JSONArray data;
    int total=0,offset=0,limit=10;
    HttpManager httpManager;
    String url;
    myCallback callback;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        httpManager=HttpManager.getInstance();
        data=new JSONArray();
        callback=new myCallback();
        loadData();

    }

    public abstract void initData();
    private void loadData(){
        initData();
        System.out.println("url: "+url);
        httpManager.get(url,callback);
    }

    public abstract void setAdapter();



    class myCallback extends BaseCallback{
        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            JSONArray arr=jsonObject.getJSONArray("data");
            total=jsonObject.getInt("total");
            for(int i=0;i<arr.length();++i){
                data.put(arr.getJSONObject(i));
            }

            //初始化列表
            myRcyView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myRcyView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
            setAdapter();

            myRcyView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                    //得到lastChildView的bottom坐标值
                    int lastChildBottom = lastChildView.getBottom();
                    //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                    int recyclerBottom =  recyclerView.getBottom()-recyclerView.getPaddingBottom();
                    //通过这个lastChildView得到这个view当前的position值
                    int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);

                    //判断lastChildView的bottom值跟recyclerBottom
                    //判断lastPosition是不是最后一个position
                    //如果两个条件都满足则说明是真正的滑动到了底部
                    if(lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1 ){
                        Toast.makeText(getContext(), "到底了o", Toast.LENGTH_SHORT).show();
                        offset+=limit;
                        loadData();
                    }
                }
            });
        }
    }

}
