package com.wind.frosty.creations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.wind.frosty.R;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    GridView gridView;
    List<String> imgUrls;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_display);
        imgUrls=new ArrayList<>();
        gridView=findViewById(R.id.img_gridview);
        initView();
    }

    private void initView(){
        imgUrls.add("1");
        imgUrls.add("2");
        imgUrls.add("3");
        imgUrls.add("4");
        ImageAdapter imageAdapter=new ImageAdapter(this,imgUrls);
        gridView.setAdapter(imageAdapter);

    }
    //预览大图
    public void preview(View view){
        Intent intent=new Intent(this,ImagePreview.class);
        startActivity(intent);

    }
}

 class ImageAdapter extends BaseAdapter {

     private Context context;
     private List imgUrls;

     public ImageAdapter(Context context,List urls){
         this.context=context;
         this.imgUrls=urls;
     }
     @Override
     public int getCount() {
         return imgUrls.size();
     }

     @Override
     public Object getItem(int position) {
         return imgUrls.get(position);
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
//             PhotoView pv=convertView.findViewById(R.id.pics_img);
//             vh.img = pv;
             vh.img=convertView.findViewById(R.id.pics_img);
             vh.words = convertView.findViewById(R.id.pics_words);
             convertView.setTag(vh);
         }
         vh=(ViewHolder)convertView.getTag();
         //写入图片
         vh.img.setImageResource(R.drawable.pick_title);
         vh.words.setText("测试用的文字");
//         vh.img.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 System.out.println("do it");
//                 Intent intent=new Intent();
//                 context.startActivity(intent);
//             }
//         });
         return convertView;
     }
 }

 class ViewHolder{
    ImageView img;
    TextView words;
 }