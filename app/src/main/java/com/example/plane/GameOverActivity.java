package com.example.plane;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#f2f2f2"));
        setContentView(R.layout.activity_finish);
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        textView = findViewById(R.id.textView);
        textView.setText("得分: " + score);
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
