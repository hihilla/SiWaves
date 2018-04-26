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
        long[] mVibratePattern = new long[]{400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400};
        int[] mAmplitudes = new int[]{30, 60, 90, 120, 150, 180, 210, 255, 240, 210, 180, 150, 120, 90, 60, 30};

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
//        Uri uri = Uri.parse("https://www.youtube.com/watch?v=Iq6gCapM9gk");
//        videoview.setVideoURI(uri);
        videoview.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
        videoview.start();
    }
}
