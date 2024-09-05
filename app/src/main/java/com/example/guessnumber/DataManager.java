package com.example.guessnumber;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {

    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_LAST_COUNT_TRIES = "lastCountTries"; // Changed to String

    private SharedPreferences sharedPreferences;
    private static DataManager instance;

    private DataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context.getApplicationContext());
        }
        return instance;
    }

    public int getLastCountTries() {
        return sharedPreferences.getInt(KEY_LAST_COUNT_TRIES, 0); // Added default value
    }

    public void setLastCountTries(int lastCountTries) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LAST_COUNT_TRIES, lastCountTries);
        editor.apply();
    }
}
