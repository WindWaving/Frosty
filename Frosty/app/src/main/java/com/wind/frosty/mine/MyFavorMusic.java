package com.wind.frosty.mine;

import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

public class MyFavorMusic extends MyWorkMusic {
    @Override
    public void initData() {
        myRcyView=getActivity().findViewById(R.id.my_recycle_view);
        url= HttpManager.apiUrl+"favor/5/music?offset="+offset+"&limit="+limit;

    }
}
