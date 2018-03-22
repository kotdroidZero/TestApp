package com.kotdroid.testapp;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
    public static final String KEY_MESSAGE_ID = "messageId";
    public static final String KEY_TEXT_REPLY = "key_text_reply";
    public static final String KEY_NOTIFICATION_ID = "notification";
    private String REPLY_ACTION = "reply";

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

    public NotificationCompat.Builder getGroupNotification(String title, String message) {

        return new android.support.v4.app.NotificationCompat.Builder(this, GROUP_NOTIFICATION_CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(GROUP_NOTIFICATION_CHANNEL_ID)
                .setGroup(CHATS_GROUP_CATEGORY_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp);
    }

    public NotificationCompat.Builder getMessageNotification(String title, String message) {

        return new android.support.v4.app.NotificationCompat.Builder(this, MESSAGE_NOTIFICATION_CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(MESSAGE_NOTIFICATION_CHANNEL_ID)
                .setGroup(CHATS_GROUP_CATEGORY_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp);
    }

    public NotificationCompat.Builder getFailureNotification(String title, String message) {

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

    public NotificationCompat.Builder getMediaPlaybackNotification(String title, String message) {




        //1. creating remote_input for replying via notification only
        String replyLabel = getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        //2. now create action from that remote input
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.drawable.ic_send_black_24dp, replyLabel, getReplyPendingIntent())
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        //3. build notification builder
        return new NotificationCompat.Builder(this, MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_ID)
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

                /**
                 * this below method is for make your notification draggable and in-larging
                 * you can add your custom long title ,body as well as you can also provide images to be applied in large area
                 * */
                .setStyle(new NotificationCompat.BigPictureStyle().setBigContentTitle(getString(R.string.text_notification_large_title)).setSummaryText(getString(R.string.text_notification_large)).bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcherbg)))
                .setBadgeIconType(R.drawable.ic_notifications_black_24dp)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                ;
    }

    public PendingIntent getReplyPendingIntent() {
        Intent intent;
        intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_NOTIFICATION_ID, 12);
        intent.putExtra(KEY_MESSAGE_ID, 15);
        return PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
