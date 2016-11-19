package com.b2b.home.servicecenter;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMSHandler extends Service {
    public SMSHandler() {
    }


    private PowerManager.WakeLock mWakeLock;







    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String msg=intent.getExtras().getString("msg");
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TAG" );
        mWakeLock.acquire();
        Log.i("Handling","SMS");

        new AsyncTask<String,Void,Void>(){
            @Override
            protected Void doInBackground(String... params) {
                HttpURLConnection connection = null;
                String s = "";

                String surl=params[0];
                URL url = null;
                try {
                    url = new URL(surl);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    char c;
                    while ((c = (char) input.read()) != (char) -1)
                        s += c;

                    Log.i("Response",s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,msg);








        return Service.START_STICKY;
    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}
