package com.example.hilla.siwaves;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
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
        }else{
            v.vibrate(100);
        }
    }

    public void onVideoClick(View view) {
        VideoView videoview = (VideoView) findViewById(R.id.videoview);
        videoview.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
        videoview.start();
    }
}
