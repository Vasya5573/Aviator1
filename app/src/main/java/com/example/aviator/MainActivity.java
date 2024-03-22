package com.example.aviator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aviator.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    public ImageView imageView;
    public boolean isMoving = false;
    public int health = 10;
    public int points = 0;
    ArrayList<ImageView> planes;
    private Rect mainPlaneRect;
    private ArrayList<Rect> planeRects;
    public ImageView imageView9;
    public ImageView imageView11;
    public TextView textView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        planes = new ArrayList<>();
        forthImage();

        startCollisionThread();
//        mainPlaneRect = new Rect();
        planeRects = new ArrayList<>();

//        final ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setImageResource(R.drawable.bb3);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(100,100));
//        ViewGroup rootLayout = findViewById(android.R.id.content);
//        rootLayout.addView(imageView);
        binding.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StarterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.f16.getX() != 0) {
                    binding.f16.setX(binding.f16.getX() - 45);
                }
            }
        });
        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.f16.getX() <= binding.getRoot().getWidth() - binding.f16.getWidth()) {
                    binding.f16.setX(binding.f16.getX() + 45);
                }
            }
        });
        startMovingThread();
    }

    private void startMovingThread() {
        isMoving = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMoving) {
                    moveImage();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void forthImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMoving) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.getRoot().post(new Runnable() {
                                @Override
                                public void run() {
                                    Random randik = new Random();
                                    int x = randik.nextInt(binding.getRoot().getWidth());
                                    int b = randik.nextInt(9);
                                    final ImageView imageView = new ImageView(getApplicationContext());
                                    switch (b) {
                                        case 0:
                                            imageView.setImageResource(R.drawable.bb1);
                                            imageView.setTag("bb1");
                                            break;
                                        case 1:
                                            imageView.setImageResource(R.drawable.bb2);
                                            imageView.setTag("bb1");
                                            break;
                                        case 2:
                                            imageView.setImageResource(R.drawable.bb3);
                                            imageView.setTag("bb1");
                                            break;
                                        case 3:
                                            imageView.setImageResource(R.drawable.bb5);
                                            imageView.setTag("bb1");
                                        case 4:
                                            imageView.setImageResource(R.drawable.bb7);
                                            imageView.setTag("bb1");
                                            break;
                                        case 5:
                                            imageView.setImageResource(R.drawable.bb8);
                                            imageView.setTag("bb1");
                                            break;
                                        case 6:
                                            imageView.setImageResource(R.drawable.x1);
                                            imageView.setTag("x1");
                                            break;
                                        case 7:
                                            imageView.setImageResource(R.drawable.x2);
                                            imageView.setTag("x2");
                                            break;
                                        case 8:
                                            imageView.setImageResource(R.drawable.x4);
                                            imageView.setTag("x4");
                                            break;
                                    }
                                    imageView.setX(x);
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
                                    ViewGroup rootLayout = findViewById(android.R.id.content);
                                    rootLayout.addView(imageView);
                                    planes.add(imageView);
                                    Rect planeRect = new Rect((int) imageView.getX(), (int) imageView.getY(),
                                            (int) (imageView.getX() + imageView.getWidth()), (int) (imageView.getY() + imageView.getHeight()));
                                    planeRects.add(planeRect);
                                }
                            });
                                }
                            });

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void moveImage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < planes.size(); i++) {
                    ImageView plane = planes.get(i);
                    plane.setY(plane.getY() + 18);
                    Rect rect = planeRects.get(i);
                    rect.offset(0, 18);
                }
                updateMainPlaneRect();
            }

        });
    }

    private void updateMainPlaneRect() {
        float mainPlaneX = binding.f16.getX();
        float mainPlaneY = binding.f16.getY();
        float mainPlaneWidth = binding.f16.getWidth();
        float mainPlaneHeight = binding.f16.getHeight();
        Rect mainPlaneRect = new Rect((int) mainPlaneX, (int) mainPlaneY,
                (int) (mainPlaneX + mainPlaneWidth), (int) (mainPlaneY + mainPlaneHeight));

        synchronized (planeRects) {
            Iterator<Rect> iterator = planeRects.iterator();
            while (iterator.hasNext()) {
                Rect rect = iterator.next();
                if (Rect.intersects(mainPlaneRect, rect)) {
                    int index = planeRects.indexOf(rect);
                    if (index >= 0 && index < planes.size()) {
                        ImageView plane = planes.get(index);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ViewGroup rootLayout = findViewById(android.R.id.content);
                                rootLayout.removeView(plane);
                            }
                        });
                        recieveDamage(1, plane);
                        planes.remove(index);
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void startCollisionThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMoving) {
                    updateMainPlaneRect();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void recieveDamage(int damageAmount, ImageView imageView) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        switch (imageView.getTag().toString()){
            case "bb1":
                health -= damageAmount;
                binding.textView.setText(" " + health);
                break;
                case "x1":
                    points += 1;
                    binding.textView5.setText(" " + points);
                    break;
            case "x2":
                points += 2;
                binding.textView5.setText(" " + points);
                break;
            case "x4":
                points += 4;
                binding.textView5.setText(" " + points);
                break;


        }
        if (health <= 0){
            Intent gameOverIntent = new Intent(MainActivity.this, GameOver.class);
                    startActivity(gameOverIntent);
                    finish();
        }
        if (points >= 50){
            Intent intent = new Intent(MainActivity.this, BigWin.class);
                    startActivity(intent);
                    finish();
        }
            }
        });

//            } else {
//                health -= damageAmount;
//                binding.textView.setText(" " + health);
//                if (health <= 0) {
//                    Intent gameOverIntent = new Intent(MainActivity.this, GameOver.class);
//                    startActivity(gameOverIntent);
//                    finish();
//                }
//            }
        }

    private void destroyMainPlane(ImageView imageView) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.removeView(imageView);
            }
        });
        planes.remove(imageView);
    }

    public void onDestroy(){
        super.onDestroy();
        isMoving = false;
    }
}
