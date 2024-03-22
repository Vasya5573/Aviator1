package com.example.aviator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    public ImageView imageview3;
    public ImageView imageView2;
    public ImageView imageView4;
    public TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        imageview3 = findViewById(R.id.imageView3);
        imageView2 = findViewById(R.id.imageView2);
        imageView4 = findViewById(R.id.imageView4);
        textView4 = findViewById(R.id.textView4);
        textView4.setText("0");
        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, StarterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}