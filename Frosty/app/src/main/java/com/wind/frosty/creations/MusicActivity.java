package com.wind.frosty.creations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import androidx.annotation.Nullable;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;
import com.wind.frosty.services.MusicService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicActivity extends LoadListView {
    MyReceiver receiver;
    public static final String MUSIC_LIST="MUSICLIST";
    ImageButton playMusic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_display);

        //初始化父类值
        listView=findViewById(R.id.music_listview);
        httpManager= HttpManager.getInstance();
        callback=new getCallback();
        data=new JSONArray();
        limit=10;
        url=httpManager.apiUrl+"music";

        //注册广播，接受服务发送的广播来更新ui
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(MusicActivity.MUSIC_LIST);
        registerReceiver(receiver,filter);
        //启动音乐播放服务
        Intent service=new Intent(MusicActivity.this, MusicService.class);
        startService(service);

        loadData();
    }

    @Override
    void initListView(JSONArray data) throws JSONException {
        String[] itemNames=new String[]{"music_name","music_desc"};
        int[] itemIds=new int[]{R.id.m_name,R.id.m_desc};
        ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<data.length();++i){
            HashMap<String,Object> map=new HashMap<String,Object>();
            JSONObject carddata=data.getJSONObject(i);
            map.put(itemNames[0],carddata.getString("name"));
            map.put(itemNames[1],carddata.getString("words"));
            listItem.add(map);
        }
        final SimpleAdapter mAdapter=new MusicAdapter(this,listItem,R.layout.music_display_item,itemNames,itemIds);
        listView.setAdapter(mAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                handleScrollState(mAdapter,scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                handleOnScroll(firstVisibleItem,visibleItemCount);
            }
        });
    }

    @Override
    void toCreate(View view) {
        Intent intent=new Intent(this,MusicCreate.class);
        startActivity(intent);
    }


    private class MyReceiver extends BroadcastReceiver{

        //更改播放按钮的背景图片
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isPlay=intent.getBooleanExtra("playing",true);
            if(isPlay){
                playMusic.setImageDrawable(getDrawable(R.drawable.music_off));
            }else{
                playMusic.setImageDrawable(getDrawable(R.drawable.music_on));
            }
        }
    }

    private class MusicAdapter extends SimpleAdapter {

        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         */
        public MusicAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=super.getView(position,convertView,parent);
            playMusic=view.findViewById(R.id.music_play);
            playMusic.setTag(position);
            playMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent broadIntent=new Intent(MusicService.MUSIC_SERVICE);
                    String uri="http://hanzi2019.oss-cn-hangzhou.aliyuncs.com/mp3/audio_6211_sentence.mp3";
                    broadIntent.putExtra("path",uri);
                    sendBroadcast(broadIntent);
                }
            });

            return view;
        }

    }
}
