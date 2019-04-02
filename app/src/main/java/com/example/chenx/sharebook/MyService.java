package com.example.chenx.sharebook;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.chenx.sharebook.gson.SimpleBack;
import com.example.chenx.sharebook.util.HttpUtil;
import com.example.chenx.sharebook.util.OverAllObject;
import com.example.chenx.sharebook.util.Utility;

import java.io.IOException;
import java.nio.channels.Channel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyService extends Service {

    private NotificationManager manager;
    private NotificationChannel channel;
    private String channel_id="channel_1";
    private Notification notification;



    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("wrew", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String url=OverAllObject.getAddress()+"?type=getlove&&id="+OverAllObject.getName();
                        HttpUtil.sendOkHttpRequest(url, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("后台服务错误", "onFailure: ");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseText=response.body().string();
                                Log.d("erer", " "+responseText);
                                SimpleBack simpleBack=Utility.handleSimpleResponse(responseText);
                                if(simpleBack.state.equals("1")&&simpleBack.type.equals("getlove")&&!simpleBack.data.equals("")){
                                    MyNotification(simpleBack.data);
                                }


                            }
                        });

                    }
                });
            }
        }).start();

        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour=1000*5;//每五分钟执行一次
        long triggerAtTime=SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,MyService.class);
        PendingIntent Pi=PendingIntent.getService(this,0,i,0);
        if(Build.VERSION.SDK_INT>=19) {
            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, Pi);
        }else {
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, Pi);
        }

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("wrew", "onDestroy: ");
    }


    private void MyNotification(String Str){

        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=26) {
            channel = new NotificationChannel(channel_id, "channel_1", NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
        }

        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        notification=new NotificationCompat.Builder(this,channel_id)
                .setContentTitle("GetLove")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Str))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.good))
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
    }




}



