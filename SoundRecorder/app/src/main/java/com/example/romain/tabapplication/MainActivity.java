package com.example.romain.tabapplication;

import android.Manifest;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.os.Environment;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    private MediaRecorder _recorder;
    private State _state;
    private File _fileName = null;
    private MediaPlayer _player;
    private static final String LOG_TAG = "SoundRecorder";


    private boolean isRecording;
    private boolean isPlaying;

    enum State {
        Idle,
        Paused
    }

    protected void addCapturedAudioToMediaStore(){
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();

        values.put(MediaStore.Audio.Media.TITLE, "Audio: " + _fileName.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int)(current/1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, _fileName.getAbsolutePath());

        ContentResolver cResolver = getContentResolver();

        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = cResolver.insert(base, values);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }



    public void record() {
        if (!isRecording) {
            File sample = Environment.getExternalStorageDirectory();

            try {
                _fileName = File.createTempFile("sound", ".3gp", sample);
            } catch (IOException e) {
                e.printStackTrace();
            }
            _recorder = new MediaRecorder();
            _recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            _recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            _recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            _recorder.setOutputFile(_fileName.getAbsolutePath());
            try {
                _recorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Failed to prepare");
                _recorder.reset();
            }
            _recorder.start();
            isRecording = true;
        }
    }
    public void recStop() {
            _recorder.stop();
            _recorder.release();
            _recorder = null;
            isRecording = false;

    }
    /*
    public boolean isRecord(){
        if(The functionality on the current
        screen is the recording screen then it is true){
            return true;
        }
    }
    */
    /*
    public void saveRecording(){
        _recorder.
    }
    */

    public void play(){
        _player = new MediaPlayer();
        if(_state != State.Paused) {
            try {
                _player.setDataSource(_fileName.getAbsolutePath());
                _player.prepare();
                _player.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        }
        else{
            _player.start();
            System.out.println(_player.getDuration());
        }
    }
    public void playStop(){
        _player.stop();
        _player.release();
        _player = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] PERMISSION_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new HomeFragment(), "Record");
        viewPagerAdapter.addFragments(new TopFreeFragment(), "Play");
        viewPagerAdapter.addFragments(new TopPaidFragment(), "Settings");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, 200);
        _state = State.Idle;
    }

    @Override
    public void onPause(){
        super.onPause();
        if(_recorder != null){
            _recorder.release();
            _recorder = null;
        }

        if(_player != null){
            _player.release();
            _player = null;
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        if(_player != null){
            System.out.println(_player.getCurrentPosition());
        }
    }

    public void playPause(){
        _player.pause();
    }

    public void recordOnClick(View v){
        System.out.println("record");
        record();
    }

    public void pauseOnClick(View v){
        System.out.println("pause");
    }

    public void cancelOnClick(View v){
        if(isRecording) {
            recStop();
            System.out.println("cancel");
        }
    }

    public void saveOnClick(View v){
        if(isRecording) {
            System.out.println("save");
            recStop();
            addCapturedAudioToMediaStore();
        }
    }

    public void playOnClick(View v){
        if(!isPlaying && _fileName != null) {
            if(isRecording){
                recStop();
                addCapturedAudioToMediaStore();
            }
            play();
            isPlaying = true;
        }else{
            playPause();
            isPlaying = false;
            _state = State.Paused;
        }
    }

}
