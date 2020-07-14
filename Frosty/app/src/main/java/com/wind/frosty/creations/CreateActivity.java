package com.wind.frosty.creations;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.goyourfly.multi_picture.ImageLoader;
import com.goyourfly.multi_picture.MultiPictureView;
import com.goyourfly.vincent.Vincent;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.wind.frosty.BaseCallback;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public abstract class CreateActivity extends AppCompatActivity {
    HttpManager httpManager;
    String url;
    postCallback callback;
    MultiPictureView multiPictureView;
    List<Uri> imgList;
    Uri photoUri;
    List<String>imgUrls;

    static final int TAKE_PHOTO = 1;
    static final int CHOOSE_PHOTO = 2;

    public abstract void toPost(View view);

    public void getImagePaths(){
        imgUrls=new ArrayList<>();
        for(int i=0;i<imgList.size();++i){
            imgUrls.add(getRealFilePath(getApplicationContext(),imgList.get(i)));
        }
    }
    /**
     * uri地址转换为绝对路径
     * @param context
     * @param uri
     * @return
     */
    private String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    /**
     * 图片点击事件
     */
    public void initPicView(){
        MultiPictureView.setImageLoader(new ImageLoader() {
            @Override
            public void loadImage(@NotNull ImageView imageView, @NotNull Uri uri) {
                Vincent.with(imageView.getContext())
                        .load(uri)
                        .placeholder(R.drawable.cardbkg)
                        .error(R.drawable.settings)
                        .into(imageView);
            }
        });
        //添加相机和相册图片
        multiPictureView.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(@NotNull View view) {
                popMenu();
            }
        });
        multiPictureView.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
            @Override
            public void onDeleted(View view,int index) {
                imgList.remove(index);
                multiPictureView.setList(imgList);
            }
        });
        multiPictureView.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
            @Override
            public void onItemClicked(View view, int index, ArrayList<Uri> uris) {
                Intent intent=new Intent(getApplicationContext(),ImagePreview.class);
                intent.putExtra("path",uris.get(index).toString());
                startActivity(intent);
            }
        });
    }

    /**
     * 底部弹出菜单
     */
    public void popMenu(){
        List<String> menuList=new ArrayList<String>();
        menuList.add("拍照");
        menuList.add("相册");
        final OptionBottomDialog dialog=new OptionBottomDialog(CreateActivity.this,menuList);
        dialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                switch (position){
                    case 0:
                        System.out.println("调用了拍照");

                        //判断权限
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CreateActivity.this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                        }else{
                            takePhoto();
                        }
                        break;
                    case 1:
                        System.out.println("调用了相册");
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CreateActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                        }else{
                            choosePhoto();
                        }
                        break;
                    default:
                        System.err.println("发生了错误"+position);
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(this,"您没有打开相机的权限",Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePhoto();
                } else {
                    Toast.makeText(this,"您没有选择相册的权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                System.err.println("权限发生了错误"+requestCode+permissions+grantResults);
        }
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {

        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            ContentValues values = new ContentValues();
            photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            System.out.println("正在拍照"+photoUri);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,photoUri);

            startActivityForResult(intent, TAKE_PHOTO);
        } else {
            Toast.makeText(getApplicationContext(), "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从相册选择
     */
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * 调用相册和相机的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    System.out.println("相机响应的图片地址"+photoUri);
                    imgList.add(photoUri);
                    multiPictureView.setList(imgList);
//                    File file = new File(Environment.getExternalStorageDirectory()+"/frosty",System.currentTimeMillis()+".jpg");
//                    try {
//                        if(!file.exists()){
//                            file.mkdirs();
//                        }
//                        file.createNewFile();
//                        System.out.println("创建文件");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        System.out.println("无法创建");
//                    }

                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    System.out.println("相册响应的图片地址"+uri);
                    imgList.add(uri);
                    System.out.println("imglist"+imgList);
                    multiPictureView.setList(imgList);

                }
                break;
        }
    }

    class postCallback extends BaseCallback{
        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println("发送失败 "+e.getMessage());
        }

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            System.out.println("保存成功");
            finish();
        }

        @Override
        public void onServerError(Call call, String reason) {
            System.out.println("服务器错误 "+reason);
        }
    }
}
