package ar.edu.itba.pam.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String FTU = "ftu";
    private static final String SP_ID = "travel-buddy-sp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sp = getSharedPreferences(SP_ID, MODE_PRIVATE);

        if (sp.getBoolean(FTU, true)) {
            sp.edit().putBoolean(FTU, false).apply();
            startActivity(new Intent(this, FtuActivity.class));
        }

    }
}