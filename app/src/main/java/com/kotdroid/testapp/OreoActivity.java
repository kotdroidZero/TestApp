package com.kotdroid.testapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.MediaController;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OreoActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btnPipMode,R.id.btnNotification,R.id.btnAutoSize})
    public void clickEvents(View view) {
        switch (view.getId()) {
            case R.id.btnPipMode:
                break;
            case R.id.btnNotification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btnAutofill:
                startActivity(new Intent(this,AutofillActivity.class));
                break;
            case R.id.btnAutoSize:
                startActivity(new Intent(this,AutoSizingTextViewsActivity.class));
                break;


        }
    }


    @Override public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
        } else {
            // Restore the full-screen UI.
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N) private void pipMode() {
        enterPictureInPictureMode();
    }

    @RequiresApi(api = Build.VERSION_CODES.N) @Override protected void onUserLeaveHint() {
        enterPictureInPictureMode();
    }
}
