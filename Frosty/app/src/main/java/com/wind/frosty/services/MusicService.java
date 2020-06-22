package com.wind.frosty.services;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wind.frosty.creations.MusicActivity;

import java.io.IOException;

public class MusicService extends Service {
    MediaPlayer player;
    MusicReceiver receiver;
    public static String MUSIC_SERVICE="MUSICSERVICE";

    @Override
    public void onCreate() {
        super.onCreate();
        receiver=new MusicReceiver();
        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(MusicService.MUSIC_SERVICE);
        registerReceiver(receiver,filter);

        player=new MediaPlayer();
        //音乐播放完毕将暂停按钮改为播放按钮
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent broadIntent=new Intent(MusicActivity.MUSIC_LIST);
                sendBroadcast(broadIntent);
            }
        });
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                System.out.println("prepare");
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                System.out.println("error");
                return false;
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //接收前台的控制音乐广播
    private class MusicReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String path=intent.getStringExtra("path");
            Uri uri=Uri.parse(path);
            boolean isPlay;
            if(player.isPlaying()){
                player.pause();
                isPlay=false;
                System.out.println("pause");
            }else{
                try {
                    prepareAndPlay(context,uri);
                    System.out.print("prepare");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //player.start();
                isPlay=true;
            }
            //发送广播更改图标
            Intent broadIntent=new Intent(MusicActivity.MUSIC_LIST);
            broadIntent.putExtra("playing",isPlay);
            sendBroadcast(broadIntent);
        }

        private void prepareAndPlay(Context context, Uri uri) throws IOException {
            player.reset();
            player.setDataSource(context,uri);
            player.prepare();
            player.start();
        }
    }
}
