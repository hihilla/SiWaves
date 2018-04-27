package com.example.hilla.siwaves;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import android.net.Uri;
import android.widget.MediaController;


public class PlayActivity extends AppCompatActivity {

    private VideoView vidView;
    private int length;
    private boolean paused;
    private boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        vidView = (VideoView)findViewById(R.id.videoview);
        String vidAddress = "http://18.218.124.172:8000/JGwWNGJdvx8.mp4";
        vidView.setVideoPath(vidAddress);
        length = 0;
//        VideoView vidView = (VideoView)findViewById(R.id.videoview);
//        String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
//        //String vidAddress = "http://18.218.124.172:4000/video?id=rp4UwPZfRis.mp4";
//        Uri vidUri = Uri.parse(vidAddress);
//        vidView.setVideoURI(vidUri);
//        vidView.start();
        //vidView.start();

    }

    /** Called when the user touches the button */
    public void startVibrate(View view) {
        // Do something in response to button click
        long[] mVibratePattern = new long[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        int[] mAmplitudes = new int[]{78, 128, 255, 114, 125, 102, 121, 123, 122, 99, 103, 113, 107, 106, 111, 98, 83, 47, 151, 112, 88, 83, 89, 86, 77, 65, 44, 37, 30, 40, 99, 117, 105, 78, 100, 130, 145, 126, 60, 136, 119, 69, 51, 54, 73, 75, 49, 20, 66, 41, 50, 37, 38, 43, 42, 49, 18, 37, 29, 34, 26, 23, 17, 5, 4, 2, 4, 33, 35, 35, 16, 11, 26, 8, 22, 72, 38, 39};

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
            v.vibrate(effect);
        }else {
            v.vibrate(100);
        }
    }

    public void onPlayVideoClick(View view) {
        vidView.seekTo(length);
        vidView.start();
    }

    public void onPauseVideoClick(View view) {
        length = vidView.getCurrentPosition();
        vidView.resume();
    }

    /*@Override
    public void onPause() {
        //Log.d(TAG, "onPause called");
        super.onPause();
        length = vidView.getCurrentPosition(); //stopPosition is an int
        vidView.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume called");
        vidView.seekTo(length);
        vidView.start(); //Or use resume() if it doesn't work. I'm not sure
    }*/
}
