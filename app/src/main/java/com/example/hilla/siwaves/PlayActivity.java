package com.example.hilla.siwaves;

;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;
import android.net.Uri;
import android.widget.MediaController;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    long[] mTiming;
    int[] mAmps;
    String mTitle;
    String mUrl;
    ProgressBar spinner;
    GetRequestTask task;
    Vibrator vibrator;

    private VideoView vidView;
    private int length;
    private boolean paused;
    private boolean started;
    private boolean resultOK = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String songName = intent.getStringExtra("song name") + " lyric".replaceAll(" ", "+");
        String urlString = "http://18.218.124.172:4000/get_song?word_search=" + songName;
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        startRequest(urlString);
        setContentView(R.layout.activity_play);
        vidView = (VideoView) findViewById(R.id.videoview);
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

    private void startRequest(final String urlString) {
        new Thread(new Runnable() {
            public void run() {
                Log.d("Hilla", "starting...");
                task = new GetRequestTask();
                task.execute(urlString);
            }
        }).start();
    }

    private void startVibrate() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(mTiming, mAmps, -1);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(100);
        }
    }

    public void onPlayVideoClick(View view) {
        vidView.seekTo(length);
        if (length == 0) {
            vidView.start();
            startVibrate();
        }
    }

    public void onPauseVideoClick(View view) {
        vidView.pause();
        vibrator.cancel();
        length = vidView.getDuration();
        Log.d("Position", "" + length);
    }

    private class GetRequestTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {
            int responseCode = 0;
            String res = "";
            HttpURLConnection con = null;
            try {
                for (String urlString : urls) {
                    URL url = new URL(urlString);
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
                    String responseMsg = con.getResponseMessage();
                    responseCode = con.getResponseCode();
                    Log.d("Hilla", "response code " + responseCode + responseMsg);
                    if (responseCode != 200) {
                        // bad response
                        resultOK = false;
                    }
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    res += response.toString();
                }
            } catch (Exception e) {

            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return res;
        }

        protected void onProgressUpdate(Integer... progress) {
            spinner.setVisibility(View.VISIBLE);

        }

        protected void onPostExecute(String result) {
            ((ProgressBar) findViewById(R.id.progressBar1)).setVisibility(View.GONE);
            try {
                JSONObject json = new JSONObject(result);
                mTitle = json.getString("title");
                mUrl = json.getString("url");
                vidView.setVideoPath(mUrl);
                mTiming = jsnoArrayToLongs(json.getJSONArray("timing"));
                mAmps = jsnoArrayToInts(json.getJSONArray("amplitudes"));
                Log.d("Hilla", json.getString("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private long[] jsnoArrayToLongs(JSONArray jsonArray) {
            int size = jsonArray.length();
            long[] longs = new long[size];
            try {
                for (int i = 0; i < size; i++) {
                    Object o = jsonArray.get(i);
                    longs[i] = (int) o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return longs;
        }

        private int[] jsnoArrayToInts(JSONArray jsonArray) {
            int size = jsonArray.length();
            int[] ints = new int[size];
            try {
                for (int i = 0; i < size; i++) {
                    Object o = jsonArray.get(i);
                    ints[i] = (int) o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ints;
        }
    }
}
