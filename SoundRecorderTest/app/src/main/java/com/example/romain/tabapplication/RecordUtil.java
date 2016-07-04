package com.example.romain.tabapplication;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;

/**
 * Created by larry on 16/7/1.
 */
public class RecordUtil {
    private static MediaRecorder mediaRecorder;

    public static void start(String filename) throws Exception {
        String filePath = Environment.getExternalStorageDirectory()+"/"+filename+".amr";
        File savedFile = new File(filePath);
        if(savedFile.exists()){
            // TODO
            savedFile.delete();
        }
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.prepare();
        mediaRecorder.start();
    }

    public static void pause() {
        if (mediaRecorder != null)
        {
            //
        }
    }

    public static void stop(){
        if (mediaRecorder != null)
        {
            mediaRecorder.stop();
            killMediaRecorder();
        }
    }

    private static void killMediaRecorder(){
        if (mediaRecorder != null)
        {
            mediaRecorder.release();
        }
    }
}
