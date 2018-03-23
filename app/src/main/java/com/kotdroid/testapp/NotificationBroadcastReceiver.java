package com.kotdroid.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.widget.Toast;

/**
 * Created by user12 on 22/3/18.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        CharSequence reply = getReplyMessage(intent);
        int messageId = intent.getIntExtra(NotificationUtils.KEY_MESSAGE_ID, 45);
        Toast.makeText(context, "message id : " + messageId + " \nmessage : " + reply,
                Toast.LENGTH_SHORT).show();
        int notifyId = intent.getIntExtra(NotificationUtils.KEY_NOTIFICATION_ID, 19);
        updateNotification(context, notifyId);
    }

    private CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationUtils.KEY_TEXT_REPLY);
        }
        return null;
    }

    private void updateNotification(Context context, int notifyId) {
        NotificationUtils utils = new NotificationUtils(context);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                NotificationUtils.MEDIA_PLAYBACK_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_done_black_24dp)
                .setTimeoutAfter(500)
                .setContentText(context.getString(R.string.notif_content_sent));

        utils.getNotificationManager().notify(notifyId, builder.build());
    }

}
