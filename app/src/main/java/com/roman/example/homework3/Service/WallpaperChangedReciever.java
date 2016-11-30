package com.roman.example.homework3.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by roman on 22.11.16.
 */

public class WallpaperChangedReciever extends BroadcastReceiver {
    private static final String TAG = WallpaperChangedReciever.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, LoadService.class);
        context.startService(intent1);
        Log.d(TAG, "onReceive: " + intent.getAction());
    }
}
