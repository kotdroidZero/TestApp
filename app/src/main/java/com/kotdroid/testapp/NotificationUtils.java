package com.kotdroid.testapp;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

/**
 * Created by user12 on 20/3/18.
 */

public class NotificationUtils extends ContextWrapper {

    public static final String GROUP_NOTIFICATION_CHANNEL_ID = "groupNotification";
    public static final String GROUP_NOTIFICATION_CHANNEL_NAME = "Group Notification";
    public static final String MESSAGE_NOTIFICATION_CHANNEL_ID = "messageNotification";
    public static final String MESSAGE_NOTIFICATION_CHANNEL_NAME = "Message Notification";
    public static final String FAILURE_NOTIFICATION_CHANNEL_ID = "failureNotification";
    public static final String FAILURE_NOTIFICATION_CHANNEL_NAME = "Failure Notification";
    public static final String MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_ID = "mediaPLaybackNotification";
    public static final String MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_NAME = "Media PlayBack";

    public static final String CHATS_GROUP_CATEGORY_ID = "messageNotification";
    public static final String CHATS_GROUP_CATEGORY_NAME = "Message Notification";
    public static final String OTHER_GROUP_CATEGORY_ID = "otherNotification";
    public static final String OTHER_GROUP_CATEGORY_NAME = "Other Notification";


    // Key for the string that's delivered in the action's intent.
    private static final String KEY_TEXT_REPLY = "key_text_reply";

    @RequiresApi(api = Build.VERSION_CODES.O) public NotificationUtils(Context base) {
        super(base);
        createChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O) private void createChannel() {
        //creating channel objects with unique id
        NotificationChannel groupNotificationChannel = new NotificationChannel(GROUP_NOTIFICATION_CHANNEL_ID, GROUP_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        groupNotificationChannel.setLightColor(Color.GREEN);


        NotificationChannel messageNotificationChannel = new NotificationChannel(MESSAGE_NOTIFICATION_CHANNEL_ID, MESSAGE_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        messageNotificationChannel.setLightColor(Color.GREEN);

        NotificationChannel failureNotificationChannel = new NotificationChannel(FAILURE_NOTIFICATION_CHANNEL_ID, FAILURE_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        failureNotificationChannel.setLightColor(Color.GREEN);

        NotificationChannel mediaPlayBackNotificationChannel = new NotificationChannel(MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_ID, MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        mediaPlayBackNotificationChannel.setLightColor(Color.GREEN);

        getNotificationManager().createNotificationChannel(groupNotificationChannel);
        getNotificationManager().createNotificationChannel(messageNotificationChannel);
        getNotificationManager().createNotificationChannel(failureNotificationChannel);
        getNotificationManager().createNotificationChannel(mediaPlayBackNotificationChannel);


        getNotificationManager().createNotificationChannelGroup(new NotificationChannelGroup(CHATS_GROUP_CATEGORY_ID, CHATS_GROUP_CATEGORY_NAME));
        getNotificationManager().createNotificationChannelGroup(new NotificationChannelGroup(OTHER_GROUP_CATEGORY_ID, OTHER_GROUP_CATEGORY_NAME));

        mediaPlayBackNotificationChannel.setGroup(OTHER_GROUP_CATEGORY_ID);
        failureNotificationChannel.setGroup(OTHER_GROUP_CATEGORY_ID);
//        groupNotificationChannel.setGroup(CHATS_GROUP_CATEGORY_ID);
//        messageNotificationChannel.setGroup(CHATS_GROUP_CATEGORY_ID);
    }

    public NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public android.support.v4.app.NotificationCompat.Builder getGroupNotification(String title, String message) {

        return new android.support.v4.app.NotificationCompat.Builder(this, GROUP_NOTIFICATION_CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(GROUP_NOTIFICATION_CHANNEL_ID)
                .setGroup(CHATS_GROUP_CATEGORY_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp);
    }

    public android.support.v4.app.NotificationCompat.Builder getMessageNotification(String title, String message) {

        return new android.support.v4.app.NotificationCompat.Builder(this, MESSAGE_NOTIFICATION_CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(MESSAGE_NOTIFICATION_CHANNEL_ID)
                .setGroup(CHATS_GROUP_CATEGORY_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp);
    }

    public android.support.v4.app.NotificationCompat.Builder getFailureNotification(String title, String message) {

        Intent intent = new Intent(this, OreoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, 0);
        return new android.support.v4.app.NotificationCompat.Builder(this, FAILURE_NOTIFICATION_CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(FAILURE_NOTIFICATION_CHANNEL_ID)
                .setGroup(OTHER_GROUP_CATEGORY_ID)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setContentIntent(pendingIntent)    //set notifications tap action
                .setSmallIcon(R.drawable.ic_other_black_24dp)
                .addAction(R.drawable.ic_send_black_24dp, "Reply", null);//adding action button for instant action via notification only like snoozing

    }

    public android.support.v4.app.NotificationCompat.Builder getMediaPLaybackNotification(String title, String message) {


        String replyLabel = getResources().getString(R.string.reply_label);

        //creating remote_input for replying via notification only
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();
        //now create action from that remote input
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.drawable.ic_send_black_24dp, replyLabel, null)
                .addRemoteInput(remoteInput)
                .build();


        return new android.support.v4.app.NotificationCompat.Builder(this, MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_ID)
                .setGroup(OTHER_GROUP_CATEGORY_ID)
                .setSmallIcon(R.drawable.ic_other_black_24dp)
                /**
                 * now adding reply button
                 *
                 *follow this link
                 * https://developer.android.com/training/notify-user/build-notification.html
                 */
                //now add that action in your notification builder
                .addAction(replyAction)
                //.setTimeoutAfter(3000) //after what time the notification will be auto removed
                .setColor(Color.RED)
                .setBadgeIconType(R.drawable.ic_notifications_black_24dp)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                ;
    }
}
