package com.example.guessnumber;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class FinishActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView textViewNumber;
    private MediaPlayer mediaPlayerStart;
    private static final int DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finish);

        // Apply window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set navigation bar color
        getWindow().setNavigationBarColor(Color.parseColor("#FFD517")); // golden_yellow
        Intent intent = getIntent();
        int tries = intent.getIntExtra("TRIES", 0);

        DataManager dataManager = DataManager.getInstance(this);

        // Initialize mediaPlayerClick
        mediaPlayerStart = MediaPlayer.create(this, R.raw.start);
        mediaPlayerStart.start();

        textViewNumber = findViewById(R.id.textViewNumber);
        dataManager.setLastCountTries(tries);
        textViewNumber.setText("Number is " + intent.getIntExtra("NUMBER", -1));


        // Create a Handler to finish the activity after a delay
        new Handler().postDelayed(() -> {
            Intent intent2 = new Intent("com.example.REFRESH_ACTION");
            LocalBroadcastManager.getInstance(FinishActivity.this).sendBroadcast(intent);
            finish();
        }, DELAY_MILLIS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayerStart != null) {
            mediaPlayerStart.release();
            mediaPlayerStart = null;
        }
    }

}
