package com.wind.frosty.creations;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wind.frosty.BaseCallback;
import com.wind.frosty.network.HttpManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;

/**
 * 包含listview列表类的抽象父类
 * 子类继承方式：
 * onCreate（）方法初始化父类变量
 * onCreate（）方法添加loadData（）方法
 * 实现initListView方法并监听listview的滚动事件，实现数据懒加载
 */
public abstract class LoadListView extends AppCompatActivity {
    HttpManager httpManager;
    ListView listView;
    getCallback callback;
    JSONArray data;
    int total=0;
    int offset=0;
    int limit=15;
    String url;
    //数据懒加载
    int lastVisible;//最后的可见项

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //加载数据
    void loadData(){
        String newUrl=url+"?offset="+offset+"&limit="+limit;
        System.out.println("load more"+newUrl);
        httpManager.get(newUrl,callback);
    }

    /**
     * 滚动事件监听响应
     * @param firstVisibleItem 第一个可见项
     * @param visibleItemCount 可见项的数量
     */
    void handleOnScroll(int firstVisibleItem, int visibleItemCount){
        lastVisible=firstVisibleItem+visibleItemCount-1;
        //System.out.println("最后可见"+lastVisible);
    }

    /**
     * 滚动状态监听
     * @param adapter listview的适配器
     * @param scrollState 滚动状态，如下：
     *
     *scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
     *SCROLL_STATE_IDLE是当屏幕停止滚动时
     *SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时（The user is scrolling using touch, and their finger is still on the screen）
     *SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时（The user had previously been scrolling using touch and had performed a fling）
     *
     */
    void handleScrollState(BaseAdapter adapter,int scrollState){
        int lastItem=adapter.getCount()-1;//适配器中最后一项数据索引
        //System.out.println("最后一项"+lastItem);
        if(lastVisible==lastItem){
            if(offset<total-1){
                offset+=limit;
                loadData();
            }else{
                Toast.makeText(getApplicationContext(),"到底了哦",Toast.LENGTH_SHORT).show();
            }
        }
    }

    abstract void initListView(JSONArray data) throws JSONException;
    abstract void toCreate(View view);

    class getCallback extends BaseCallback{
        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println("出错了"+e);
        }

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            JSONArray arr=jsonObject.getJSONArray("data");
            total=jsonObject.getInt("total");
            System.out.println("总共"+total+"项");
            for(int i=0;i<arr.length();++i){
                data.put(arr.getJSONObject(i));
            }
            initListView(data);
        }

        @Override
        public void onServerError(Call call, String reason) {
            System.out.println("服务器错误 "+reason);
        }

    }
}
