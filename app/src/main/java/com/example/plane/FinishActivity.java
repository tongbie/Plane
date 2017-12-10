package com.example.plane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {
    private String score;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Intent intent = getIntent();
        score=intent.getStringExtra("score");
        textView=(TextView)findViewById(R.id.textView);
        textView.setText("得分: "+score);
    }

    public void restart(View view) {
        Intent intent=new Intent(FinishActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
