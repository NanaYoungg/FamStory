package com.hongsam.famstory.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.Emotion;
import com.hongsam.famstory.database.DBFamstory;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "MyFirebaseMessagingService";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sender = remoteMessage.getData().get("sender");
        String message = remoteMessage.getData().get("message");
        String sendDate = remoteMessage.getData().get("sendDate");

        Emotion emotion = new Emotion(sender, message, sendDate);
        DBFamstory.getInstance(this).insertEmotion(emotion);

        sendNotification(emotion);
    }

    private void sendNotification(Emotion emotion)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String chId = "test";
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, chId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(emotion.getSender())
                .setContentText(emotion.getMessage())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /* 안드로이드 오레오 버전 대응 */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String chName = "ch name";
            NotificationChannel channel = new NotificationChannel(chId, chName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, notiBuilder.build());

    }
}