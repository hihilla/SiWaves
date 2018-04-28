package com.example.hilla.siwaves;


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
import android.widget.TextView;
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
    private TextView textViewId;
    private boolean resultOK = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String songName = (intent.getStringExtra("song name") + "").replaceAll(" ", "+");
        String urlString = "http://18.218.124.172:4000/get_song?word_search=" + songName;
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        startRequest(urlString);
        setContentView(R.layout.activity_play);
        vidView = (VideoView) findViewById(R.id.videoview);
        textViewId = (TextView)  findViewById(R.id.textViewId);
        length = 0;
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
            int newIndex = -1;
            if (length != -1) {
                newIndex = length / 1000;
            }

            VibrationEffect effect = VibrationEffect.createWaveform(mTiming, mAmps, newIndex);
            vibrator.vibrate(effect);
        }
    }

    public void onPlayVideoClick(View view) {
        Log.d("Bar", "Total - "+vidView.getDuration());
        Log.d("Bar", "Seconds - "+vidView.getDuration() / 1000);
        Log.d("Bar", "min - "+(vidView.getDuration() / 1000) / 60);
        vidView.seekTo(length);

        if (length == 0) {
            vidView.start();
            startVibrate();
        } else {
            vidView.start();
        }
    }

    public void onPauseVideoClick(View view) {
        vidView.pause();
        length = vidView.getCurrentPosition();
        vibrator.cancel();
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
                textViewId.setText(mTitle);
                mTiming = jsnoArrayToLongs(json.getJSONArray("timing"));
                mAmps = jsnoArrayToInts(json.getJSONArray("amplitudes"));
                Log.d("Galllll", json.getString("url"));
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
