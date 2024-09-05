package com.example.guessnumber;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
    README: The Interval is set by me as [0, x], so you can make mutable as you like
 */

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerClick, mediaPlayerStart;
    private TextView scoreNumber;

    private DataManager dataManager;
    private int lastScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(Color.parseColor("#FFD517")); // golden_yellow

        // Get an instance of DataManager
        dataManager = DataManager.getInstance(this);
        lastScore =  dataManager.getLastCountTries();

        // Initialize mediaPlayerClick
        mediaPlayerStart = MediaPlayer.create(this, R.raw.start);
        mediaPlayerStart.start();
        // Initialize mediaPlayerClick
        mediaPlayerClick = MediaPlayer.create(this, R.raw.click);

        scoreNumber = findViewById(R.id.scoreNumber);
        // Convert the current text to an integer safely

        if (lastScore <= 0) {
            scoreNumber.setText(R.string.start_new_game);
            //scoreNumber.setVisibility(View.GONE); // Hide the TextView
        } else {
            scoreNumber.setText(String.valueOf(lastScore));
            //scoreNumber.setVisibility(View.VISIBLE); // Show the TextView
        }

        // Find the button by its ID
        Button buttonStart = findViewById(R.id.buttonStart);

        // Set an OnClickListener to the button
        buttonStart.setOnClickListener(v -> {
            // Play the sound effect
            if (mediaPlayerClick != null) {
                mediaPlayerClick.start();
            }
            // Generate number before starting new activity
            int min, max;
            min = 0; max = 1024;
            RandomNumberGen randomGen = new RandomNumberGen(min,max);
            int randomNumber = randomGen.generate();
            // Handle the button click action here
            Intent intent = new Intent(MainActivity.this, BeginActivity.class);
            intent.putExtra("RANDOM_NUMBER", randomNumber);
            intent.putExtra("RANDOM_NUMBER_MIN", min);
            intent.putExtra("RANDOM_NUMBER_MAX", max);
            startActivity(intent);

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release mediaPlayerClick resources
        if (mediaPlayerStart != null) {
            mediaPlayerStart.release();
            mediaPlayerStart = null;
        }
        // Release mediaPlayerClick resources
        if (mediaPlayerClick != null) {
            mediaPlayerClick.release();
            mediaPlayerClick = null;
        }
    }

    // Method to refresh data
    public void refresh() {
        lastScore =  dataManager.getLastCountTries();
        if (lastScore <= 0) {
            scoreNumber.setText(R.string.start_new_game);
            //scoreNumber.setVisibility(View.GONE); // Hide the TextView
        } else {
            scoreNumber.setText(String.valueOf(lastScore));
            //scoreNumber.setVisibility(View.VISIBLE); // Show the TextView
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Optionally refresh when the activity resumes
        refresh();
    }
}