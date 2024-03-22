package com.example.aviator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.aviator.databinding.ActivityMainBinding;

public class StarterActivity extends AppCompatActivity {
    public ImageView imageView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imageView8 = findViewById (R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StarterActivity", "ImageView8 clicked");
                Intent intent = new Intent(StarterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}