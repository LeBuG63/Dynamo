package com.example.apptest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private SeekBar     seekbar;

    private List<Point> points = new ArrayList<>();

    private double seekbarXValue = 0;

    private int offsetx = 0;
    private int offsety = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceview);
        seekbar = findViewById(R.id.sliderx);

        Random random = new Random();

        final Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        offsetx = size.x / 2;
        offsety = size.y / 2;

        for(int i = 0; i <  500; ++i) {
            Point point = new Point(
                random.nextInt() % (1 + size.x),
                random.nextInt() % (1 + size.y)
            );

            System.out.println(point.x + " " + point.y);

            points.add(point);
        }

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarXValue = (double)progress/100;

                SurfaceHolder holder = surfaceView.getHolder();

                Canvas canvas = holder.lockCanvas();

                Paint paint = new Paint();
                paint.setStrokeWidth(15);

                canvas.drawColor(Color.BLACK);

                for(Point point : points) {
                    final float x = offsetx + point.x * (float)Math.cos(seekbarXValue) * 2;
                    final float y = offsety + point.y * (float)Math.sin(seekbarXValue) * 2;

                    paint.setARGB(0xFF, (int)x/size.x * 255, (int)y/size.y * 255, (int)(Math.tan(seekbarXValue) * 255));

                    canvas.drawPoint(x % size.x, y % size.y, paint);
                }

                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();

                Paint paint = new Paint();

                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(15);

                for(Point point : points) {
                    canvas.drawPoint(offsetx + point.x * (float)Math.cos(seekbarXValue) * 100, offsety + point.y, paint);
                }

                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }
}
