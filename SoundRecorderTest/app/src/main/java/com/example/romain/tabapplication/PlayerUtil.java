package com.example.romain.tabapplication;

import android.media.MediaPlayer;
import android.os.Environment;

/**
 * Created by larry on 16/7/1.
 */
public class PlayerUtil {
    private static MediaPlayer mediaPlayer;

    public static void start(String filename) throws Exception{
        String filePath = Environment.getExternalStorageDirectory()+"/"+filename+".amr";
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(filePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
//        killMediaPlayer();
        System.out.println(mediaPlayer);
    }

    public static void pause() {
        if (mediaPlayer != null)
        {
            //
        }
    }

    public static void stop(){
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            killMediaPlayer();
        }
    }

    private static void killMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }
}
