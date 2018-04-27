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
import android.widget.VideoView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String songName = intent.getStringExtra("song name").replaceAll(" ", "+");
        String urlString = "http://18.218.124.172:4000/get_song?word_search=" + songName;
        Log.d("Hilla", urlString);
        startRequest(urlString);
        setContentView(R.layout.activity_play);
    }

    private void startRequest(final String urlString) {
        new Thread(new Runnable() {
            public void run() {
                Log.d("Hilla", "starting...");
                new GetRequestTask().execute(urlString);
            }
        }).start();
    }

    /**
     * Called when the user touches the button
     */
    public void startVibrate(View view) {
        // Do something in response to button click
//        long[] mVibratePattern = new long[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
//        int[] mAmplitudes = new int[]{78, 128, 255, 114, 125, 102, 121, 123, 122, 99, 103, 113, 107, 106, 111, 98, 83, 47, 151, 112, 88, 83, 89, 86, 77, 65, 44, 37, 30, 40, 99, 117, 105, 78, 100, 130, 145, 126, 60, 136, 119, 69, 51, 54, 73, 75, 49, 20, 66, 41, 50, 37, 38, 43, 42, 49, 18, 37, 29, 34, 26, 23, 17, 5, 4, 2, 4, 33, 35, 35, 16, 11, 26, 8, 22, 72, 38, 39};

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(mTiming, mAmps, -1);
            v.vibrate(effect);
        } else {
            v.vibrate(100);
        }
    }

    public void onVideoClick(View view) {
        VideoView videoview = (VideoView) findViewById(R.id.videoview);
        videoview.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
        videoview.start();
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
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                mTitle = json.getString("title");
                mUrl = json.getString("url");
                mTiming = jsnoArrayToLongs(json.getJSONArray("timing"));
                mAmps = jsnoArrayToInts(json.getJSONArray("amplitudes"));
                Log.d("Hilla", json.getString("title"));
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
