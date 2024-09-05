package com.example.guessnumber;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class BeginActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerClick;
    private EditText editTextNumber;
    private TextView textHint, textViewLowLabel, textViewHighLabel;
    ImageView imageViewLow;
    ImageView imageViewHigh;

    private List<String> categories;
    int randomNumber,min,max;

    private List<Integer> enteredNumbers = new ArrayList<>();
    private int tryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_begin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(Color.parseColor("#FFD517")); // golden_yellow
        // Retrieve the random number from the Intent
        Intent intent = getIntent();
        imageViewLow = findViewById(R.id.imageViewLow);
        imageViewHigh = findViewById(R.id.imageViewHigh);
        textViewLowLabel = findViewById(R.id.textViewLowLabel);
        textViewHighLabel = findViewById(R.id.textViewHighLabel);
        randomNumber = intent.getIntExtra("RANDOM_NUMBER", -1); // -1 is the default value if no extra is found
        min = intent.getIntExtra("RANDOM_NUMBER_MIN", -1);
        max = intent.getIntExtra("RANDOM_NUMBER_MAX", -1);
        editTextNumber = findViewById(R.id.editTextNumber);

        textHint = findViewById(R.id.textViewHint);
        categories = NumberCategorizer.categorizeNumber(randomNumber, min, max);
        textHint.setText(categories != null ? categories.toString() : "no hints");

        // Initialize mediaPlayerClick
        mediaPlayerClick = MediaPlayer.create(this, R.raw.click);

        // Find the button by its ID
        Button buttonClick = findViewById(R.id.buttonConfirm);

        // Set an OnClickListener to the button
        buttonClick.setOnClickListener(v -> {
            // Play the sound effect
            if (mediaPlayerClick != null) {
                mediaPlayerClick.start();
                check();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release mediaPlayerClick resources
        if (mediaPlayerClick != null) {
            mediaPlayerClick.release();
            mediaPlayerClick = null;
        }
    }

    private void check() {
        // Reset colors for both ImageViews and TextViews
        imageViewLow.setColorFilter(null);
        imageViewHigh.setColorFilter(null);
        textViewLowLabel.setTextColor(Color.BLACK); // Reset to default color
        textViewHighLabel.setTextColor(Color.BLACK); // Reset to default color

        int result;
        int inputNumber;

        inputNumber = Integer.parseInt(editTextNumber.getText().toString());

        // Check if the inputNumber is already counted
        if (enteredNumbers.contains(inputNumber)) {
            // Skip counting this number
            Toast.makeText(this, "Number already entered", Toast.LENGTH_SHORT).show();
        }else {
            // Add the number to the list
            enteredNumbers.add(inputNumber);
        }

        // Calculate the result
        result = randomNumber - inputNumber;

        // Increment the try count
        tryCount++;

        // Check the result and update ImageView and TextView properties
        if (result == 0) {
            Intent intent = new Intent(BeginActivity.this, FinishActivity.class);
            intent.putExtra("NUMBER", randomNumber);
            intent.putExtra("TRIES", tryCount);
            startActivity(intent);
            // Finish current activity
            finish();
        } else {
            if (result > 0) { // Low
                imageViewLow.setColorFilter(Color.parseColor("#F44336")); // Red color
                textViewLowLabel.setTextColor(Color.parseColor("#F44336")); // Red color
            } else { // High
                imageViewHigh.setColorFilter(Color.parseColor("#4CAF50")); // Green color
                textViewHighLabel.setTextColor(Color.parseColor("#4CAF50")); // Green color
            }
        }

        // Display the result and the number of tries
        Toast.makeText(this, result + " = " + randomNumber + " - " + inputNumber, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Try Count: " + tryCount, Toast.LENGTH_SHORT).show();
    }

}