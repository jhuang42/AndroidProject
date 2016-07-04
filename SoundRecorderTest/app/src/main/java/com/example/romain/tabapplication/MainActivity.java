package com.example.romain.tabapplication;

import android.Manifest;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    //STATES
    private boolean isPlaying;


    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    MediaRecorder mMediaRecorder;
    MediaPlayer mMediaPlayer;

    private static final String TAG = "MainActivity";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        verifyStoragePermissions(this);

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
    }



    public static void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 200);
    }

    public void startRecordOnClick(View v) {
        try {
            RecordUtil.start("1");
            System.out.println("record start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //case pause record
    public void pauseRecordOnClick(View v) {
        RecordUtil.pause();
    }

    //case record stop:
    public void stopRecordOnClick(View v) {
        RecordUtil.stop();
        System.out.println("record stop");
    }

    // case play start
    public void startPlayOnClick(View v) {
        try {
            PlayerUtil.start("1");
            System.out.println("player start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //case play pause
    public void pausePlayOnClick(View v) {
        PlayerUtil.pause();
    }

        //case play stop

    public void stopPlayOnClick(View v) {
        System.out.println("player stop");
        PlayerUtil.stop();
    }


}
