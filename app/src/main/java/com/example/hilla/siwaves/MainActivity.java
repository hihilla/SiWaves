package com.example.hilla.siwaves;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
