package com.example.hilla.siwaves;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user touches the button */
    public void startVibrate(View view) {
        // Do something in response to button click
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(500);
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
