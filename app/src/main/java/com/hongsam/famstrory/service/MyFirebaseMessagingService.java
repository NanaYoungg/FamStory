package com.hongsam.famstrory.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.Emotion;
import com.hongsam.famstrory.data.LetterContants;
import com.hongsam.famstrory.database.DBFamstory;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "MyFirebaseMessagingService";

    public MyFirebaseMessagingService() { }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String fcmType = remoteMessage.getData().get("fcmType");

        if (fcmType.equals("Emotion")) {
            Log.d(TAG, "Emotion 받아옴!!");
            String sender = remoteMessage.getData().get("sender");
            String message = remoteMessage.getData().get("message");
            String sendDate = remoteMessage.getData().get("sendDate");

            Emotion emotion = new Emotion(sender, message, sendDate);
            DBFamstory.getInstance(this).insertEmotion(emotion);

            sendNotification(sender, message);
        } else if (fcmType.equals("LetterContants")) {
            Log.d(TAG, "LetterContants 받아옴!!");
            String sender = remoteMessage.getData().get("sender");
            String contants = remoteMessage.getData().get("contants");
            String date = remoteMessage.getData().get("date ");

            LetterContants letterContants = new LetterContants(sender, contants, date);
            // db insert하는 코드 들어가야됨

            sendNotification("편지 도착", sender + "로부터 편지가 도착했습니다!");
        } else if (fcmType.equals("Member")) {
            Log.d(TAG, "Member 받아옴!!");
            String name = remoteMessage.getData().get("name");
            String relation = remoteMessage.getData().get("relation");

            sendNotification("가족참여알림", name+"(" + relation + ") 이 참여했습니다.");
        }

    }

    private void sendNotification(String title, String contents)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String chId = "test";
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, chId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(contents)
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