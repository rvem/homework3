package com.roman.example.homework3.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.roman.example.homework3.MainActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by roman on 26.11.16.
 */

public class LoadService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File destFile = new File(getFilesDir(), MainActivity.destFile);
        if (!destFile.exists()) {
            new Thread(new ImageLoader(MainActivity.url, destFile)).start();
        }
        return START_STICKY;
    }

    public class ImageLoader implements Runnable {
        private String url;
        private File destFile;



        public ImageLoader(String url, File destFile) {
            this.url = url;
            this.destFile = destFile;
        }

        private static final String TAG = "Image loader";

        @Override
        public void run() {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                URL url_ = new URL(url);
                in = new BufferedInputStream(url_.openStream());
                out = new FileOutputStream(destFile);
                int tmp;
                byte[] buffer = new byte[1024];
                while ((tmp = in.read(buffer)) != -1) {
                    out.write(buffer, 0, tmp);
                }
                Log.d(TAG, "downloaded");
                sendBroadcast(new Intent(MainActivity.INTENT_FILTER));
            } catch (IOException e) {
                e.printStackTrace();
                destFile.delete();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Cannot close file: ", e);
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Cannot close connection: ", e);
                    }
                }
            }
        }
    }
}
