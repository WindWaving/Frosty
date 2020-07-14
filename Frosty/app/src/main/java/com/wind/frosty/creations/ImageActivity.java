package com.wind.frosty.creations;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;
import com.wind.frosty.network.NetImageAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ImageActivity extends AppCompatActivity {
    GridView gridView;
    //List<String> imgUrls;
    HttpManager httpManager;
    String url;
    int offset=0,limit=10,total=0;
    ImageCallback callback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_display);
        //imgUrls=new ArrayList<>();
        gridView=findViewById(R.id.img_gridview);
        httpManager=HttpManager.getInstance();
        url=HttpManager.apiUrl+"picture?offset="+offset+"&limit="+limit;
        callback=new ImageCallback();

        initData();
    }

    private void initData(){
        httpManager.get(url,callback);
    }
    private void initView(JSONArray data){
        ImageAdapter imageAdapter=new ImageAdapter(this,data);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ImageDetail.class);
                TextView tv=view.findViewById(R.id.pics_pid);
                intent.putExtra("pid",Integer.parseInt(tv.getText().toString()));
            }
        });
    }
    //预览大图
    public void preview(View view){
        Intent intent=new Intent(this,ImagePreview.class);
        //TextView tv=
        //intent.putExtra("path",tv.getText().toString());
        startActivity(intent);

    }

    class ImageCallback extends BaseCallback{
        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            JSONArray data=jsonObject.getJSONArray("data");
            total=jsonObject.getInt("total");
            initView(data);
        }

        @Override
        public void onServerError(Call call, String reason) {
            Toast.makeText(getApplicationContext(), "服务器错误:"+reason, Toast.LENGTH_SHORT).show();
        }
    }
}

 class ImageAdapter extends BaseAdapter {

     private Context context;
     private JSONArray data;

     public ImageAdapter(Context context,JSONArray data){
         this.context=context;
         this.data=data;
     }
     @Override
     public int getCount() {
         return data.length();
     }

     @Override
     public Object getItem(int position) {
         try {
             return data.get(position);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return null;
     }

     @Override
     public long getItemId(int position) {
         return position;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder vh;
         if (convertView == null) {
             convertView = LayoutInflater.from(context).inflate(R.layout.picture_display_item, null);
             vh = new ViewHolder();
             vh.img=convertView.findViewById(R.id.pics_img);
             vh.words = convertView.findViewById(R.id.pics_words);
             vh.pid=convertView.findViewById(R.id.pics_pid);
             vh.url=convertView.findViewById(R.id.pics_url);
             convertView.setTag(vh);
         }
         vh=(ViewHolder)convertView.getTag();

         //写入数据
         try {
             String imgUrl=data.getJSONObject(position).getString("imgUrl");
             new PicNetImage(vh.img).execute(imgUrl);
             vh.words.setText(data.getJSONObject(position).getString("content"));
             vh.pid.setText(data.getJSONObject(position).getInt("pid"));
             vh.url.setText(imgUrl);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return convertView;
     }
     class ViewHolder{
         ImageView img;
         TextView words;
         TextView pid;
         TextView url;
     }

     class PicNetImage extends NetImageAsync{
         private ImageView view;
         public PicNetImage(ImageView view){
             this.view=view;
         }
         @Override
         protected void onPostExecute(Bitmap bitmap) {
             this.view.setImageBitmap(bitmap);
         }
     }

 }



