package com.example.hilla.siwaves;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        TextView textview = (TextView) findViewById(R.id.textLogo);
        Shader myShader = new LinearGradient(
                0, 50, 0, 128,
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.windowBackground),
                Shader.TileMode.CLAMP );
        textview.getPaint().setShader( myShader );
    }

//    private void requestSong() {
//        TextView textView = (TextView) findViewById(R.id.text);
//        String songName = textView.getText().toString();
//        songName.replaceAll(" ", "+");
//        String urlString = "http://18.218.124.172:4000/get_song?word_search=" + songName;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(urlString);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            String jsonString = convertStreamToString(in);
//            JSONObject obj = new JSONObject(jsonString);
//            if (!obj.get("status").toString().equals("SUCCESS")) {
//                // bad response!!!
//                return;
//            }
//            List<Integer> amplitudes = parseInts(obj.getJSONArray("amplitudes"));
//            List<Long> timing = parseLongs(obj.getJSONArray("timing"));
//            String title = obj.getString("title");
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//        }
//    }
//
//    private List<Integer> parseInts(JSONArray jsonArray) {
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        if (jsonArray != null) {
//            int len = jsonArray.length();
//            for (int i = 0; i < len; i++) {
//                try {
//                    list.add((Integer) jsonArray.get(i));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return list;
//    }
//
//    private List<Long> parseLongs(JSONArray jsonArray) {
//        ArrayList<Long> list = new ArrayList<Long>();
//        if (jsonArray != null) {
//            int len = jsonArray.length();
//            for (int i = 0; i < len; i++) {
//                try {
//                    list.add((Long) jsonArray.get(i));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return list;
//    }
//
//    private String convertStreamToString(InputStream is) {
//        Scanner s = new Scanner(is).useDelimiter("\\A");
//        return s.hasNext() ? s.next() : "";
//    }


    public void onSearchClicked(View view) {
//        requestSong();
        TextView textView = (TextView) findViewById(R.id.text);
        String songName = textView.getText().toString();
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("song name", songName);
        startActivity(intent);
    }
}
