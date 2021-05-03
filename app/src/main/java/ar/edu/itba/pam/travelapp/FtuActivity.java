package ar.edu.itba.pam.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.pam.travelapp.main.MainActivity;

public class FtuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftu);
        Button button = (Button) findViewById(R.id.button_ftu);
        button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }

}
