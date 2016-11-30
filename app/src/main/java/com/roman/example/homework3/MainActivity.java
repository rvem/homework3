package com.roman.example.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.roman.example.homework3.Service.WallpaperChangedReciever;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://cs6.pikabu.ru/images/previews_comm/2014-12_6/14199298517854.jpg";
    public static final String destFile = "image.jpg";
    public static final String INTENT_FILTER = "BROADCAST";

    private ImageView imageView;
    private TextView errorView;

    BroadcastReceiver wallpaperChangedReciever, imageShowReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.picture);
        errorView = (TextView) findViewById(R.id.error);
        File file = new File(getFilesDir(), destFile);
        if (!file.exists()) {
            imageView.setVisibility(View.GONE);
            errorView.setText(R.string.not_downloaded);
            errorView.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            imageView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }
        wallpaperChangedReciever = new WallpaperChangedReciever();
        imageShowReciever = new BroadcastReceiver() {
            private static final String TAG = "imageShowReciever";
            @Override
            public void onReceive(Context context, Intent intent) {
                File file = new File(getFilesDir(), destFile);
                if (file.exists()) {
                    Log.d(TAG, "file existed");
                    imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    imageView.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                }
            }
        };
        registerReceiver(wallpaperChangedReciever, new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED));
        registerReceiver(imageShowReciever, new IntentFilter(INTENT_FILTER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wallpaperChangedReciever);
        unregisterReceiver(imageShowReciever);
    }

}
