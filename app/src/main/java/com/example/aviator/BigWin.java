package com.example.aviator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BigWin extends AppCompatActivity {
    public ImageView imageView;
    public ImageView imageView5;
    public ImageView imageView7;
    public TextView textView6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_win);
        imageView = findViewById(R.id.imageView);
        imageView5 = findViewById(R.id.imageView5);
        textView6 = findViewById(R.id.textView6);
        textView6.setText("50");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BigWin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BigWin.this, StarterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}