package com.kotdroid.testapp;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etMessage;
    EditText etTitle;
    private NotificationUtils notificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notificationUtils = new NotificationUtils(this);

        etMessage = findViewById(R.id.etMessage);
        etTitle = findViewById(R.id.etTitle);

        findViewById(R.id.btnFailureOther).setOnClickListener(this);
        findViewById(R.id.btnMediaPlayBackOther).setOnClickListener(this);
        findViewById(R.id.btnGrouChat).setOnClickListener(this);
        findViewById(R.id.btnMessageChat).setOnClickListener(this);
        findViewById(R.id.btnMessageChat).setAutofillHints(View.AUTOFILL_HINT_USERNAME);
    }

    @Override public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnFailureOther:
                NotificationCompat.Builder nb1 = notificationUtils.getFailureNotification(
                        etTitle.getText().toString().trim(), etMessage.getText().toString().trim());
                notificationUtils.getNotificationManager().notify(1, nb1.build());
                break;
            case R.id.btnMediaPlayBackOther:
                NotificationCompat.Builder nb2 = notificationUtils.getMediaPlaybackNotification(
                        etTitle.getText().toString().trim(), etMessage.getText().toString().trim());
                notificationUtils.getNotificationManager().notify(2, nb2.build());
                break;
            case R.id.btnGrouChat:
                NotificationCompat.Builder nb3 = notificationUtils.getGroupNotification(
                        etTitle.getText().toString().trim(), etMessage.getText().toString().trim());
                notificationUtils.getNotificationManager().notify(3, nb3.build());
                break;
            case R.id.btnMessageChat:
                NotificationCompat.Builder nb4 = notificationUtils.getMessageNotification(
                        etTitle.getText().toString().trim(), etMessage.getText().toString().trim());
                notificationUtils.getNotificationManager().notify(4, nb4.build());
                break;
        }
    }

//    @OnClick({R.id.btnFailureOther,R.id.btnMediaPlayBackOther,R.id.btnGrouChat,R.id.btnMessageChat})
//    public void clickEvents(View view){
//
//    }
}
