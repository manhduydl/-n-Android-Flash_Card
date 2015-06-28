package com.example.duy.flashcard_v10;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        List<info_parcel> list_word = intent.getParcelableArrayListExtra("list_word");
        Calendar now = Calendar.getInstance();
        DateFormat formater= SimpleDateFormat.getTimeInstance();

        NotificationManager mNoti= (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        int icon = R.drawable.ic_action;

        Intent i= new Intent(context, MainActivity.class);

        Intent test = new Intent("com.example.duy.flashcard_v10.MY_UNIQUE_ACTION");
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0,
                test, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent contentIntent= PendingIntent.getActivity(context,0,i,0);
        PendingIntent cancelIntent=PendingIntent.getActivity(context,0,i,0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        //Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("Flash Card")
                //.setContentText(formater.format(now.getTime()))
                .setContentText(list_word.get(random_word(list_word.size())).Word)
                .setSmallIcon(icon)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setLights(Color.RED, 3000, 3000)
                .addAction(icon, "Click to open FlashCard", cancelIntent).build();
        notification.flags =Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        int notificationRef=1;
        mNoti.notify(notificationRef,notification);
    }

    //hàm chọn ngẫu nhiên
    public int random_word(int l){
        Random r = new Random();
        int n = r.nextInt(l*10)%l;
        if(n<0)
            return 0;
        return n;
    }

}
