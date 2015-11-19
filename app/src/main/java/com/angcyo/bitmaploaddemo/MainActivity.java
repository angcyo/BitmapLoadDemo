package com.angcyo.bitmaploaddemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    AppCompatImageView img1, img2;
    LargeImageView img3;
    Bitmap bitmap, bitmap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initViews();
        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                initBitmaps();
            }
        }, 200);
    }

    private void initBitmaps() {
        try {
            float density = getResources().getDisplayMetrics().density;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, options);

            int sample1 = calcSampleSize(options, img1.getWidth() / density, img1.getHeight() / density);
            options.inSampleSize = sample1;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, options);
            img1.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap2, img1.getWidth(), img1.getHeight()));


            int sample2 = calcSampleSize(options, img2.getWidth() / density, img2.getHeight() / density);
            options.inSampleSize = sample2;
            bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, options);
            img2.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap2, img2.getWidth(), img2.getHeight()));

//            int sample3 = calcSampleSize(options, img3.getWidth() / density, img3.getHeight() / density);
//            options.inSampleSize = sample3;
//            bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, options);
//            img3.setImageBitmap(bitmap2);
            try {
                img3.setInputStream(getAssets().open("bmp.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private int calcSampleSize(BitmapFactory.Options options, float refWidth, float refHeight) {
        float width = options.outWidth;
        float height = options.outHeight;
        int sample = 1;
        if (width > refWidth || height > refHeight) {
            int widthRatio = Math.round(width / refWidth);
            int heightRatio = Math.round(height / refHeight);

            sample = Math.max(widthRatio, heightRatio);
        }
        return sample;
    }

    private void initViews() {
        img1 = (AppCompatImageView) findViewById(R.id.img1);
        img2 = (AppCompatImageView) findViewById(R.id.img2);
        img3 = (LargeImageView) findViewById(R.id.img3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
