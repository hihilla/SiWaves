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

    private void requestSong() {
        // todo : implement GET
    }

    public void onSearchClicked(View view) {
        requestSong();
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
}
