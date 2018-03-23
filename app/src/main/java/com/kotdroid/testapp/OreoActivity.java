package com.kotdroid.testapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OreoActivity extends AppCompatActivity {


    private ShortcutManager mShortcutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //initializing shortcut manager
        mShortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);

        createDynamicShortcut();

    }

    private void createDynamicShortcut() {
        ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "myDyanamicShortcut")
                .setShortLabel("Send notification")
                .setLongLabel("This is the website using ...")
                .setIcon(Icon.createWithResource(this, R.drawable.shortcut_test_app_24dp))
                .setIntent(new Intent(this, NotificationActivity.class).setAction(Intent.ACTION_VIEW))
                .build();


        mShortcutManager.setDynamicShortcuts(Arrays.asList(shortcutInfo));

    }


    @OnClick({R.id.btnPipMode, R.id.btnNotification, R.id.btnAutoSize, R.id.btnFontDownloadable,
            R.id.btnPinnedShortcut, R.id.btnAutofill, R.id.btnChangeShortcutLabel,
            R.id.btnColorSpace, R.id.btnNougat})
    public void clickEvents(View view) {
        switch (view.getId()) {
            case R.id.btnNougat:
                startActivity(new Intent(this, NougatActivity.class));
                break;
            case R.id.btnNotification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btnAutofill:
                startActivity(new Intent(this, AutofillActivity.class));
                break;
            case R.id.btnAutoSize:
                startActivity(new Intent(this, AutoSizingTextViewsActivity.class));
                break;
            case R.id.btnFontDownloadable:
                (findViewById(R.id.flContainerMain)).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain,
                        new DownloadableFontFragment(), "fragfont").commit();
                break;
            case R.id.btnPinnedShortcut:
                pinnedShortcut();
                break;
            case R.id.btnChangeShortcutLabel:
                changeDynamicShortcut();
                break;
            case R.id.btnColorSpace:
                (findViewById(R.id.flContainerMain)).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain,
                        new ColorSpaceFragment(), "fragcolor").commit();
                break;
        }
    }

    private void changeDynamicShortcut() {
        ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "myDyanamicShortcut")
                .setShortLabel("Send Custom Notification")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_notifications_black_24dp))
                .build();
        mShortcutManager.updateShortcuts(Arrays.asList(shortcutInfo));
    }

    private void pinnedShortcut() {


        if (mShortcutManager.isRequestPinShortcutSupported()) {
            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "myShortcut")
                    .setShortLabel("WebSite")
                    .setLongLabel("This is the website using ...")
                    .setIcon(Icon.createWithResource(this, R.drawable.shortcut_test_app_24dp))
                    .setIntent(new Intent(this, NotificationActivity.class).setAction(Intent.ACTION_VIEW))
                    .build();
            Intent shortcutInfoIntent = mShortcutManager.createShortcutResultIntent(shortcutInfo);


            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the shortcut to be pinned. Note that, if the
            // pinning operation fails, your app isn't notified. We assume here that the
            // app has implemented a method called createShortcutResultIntent() that
            // returns a broadcast intent.

            PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
                    shortcutInfoIntent, 0);

            mShortcutManager.requestPinShortcut(shortcutInfo, successCallback.getIntentSender());

        }
    }


    @Override public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode,
                                                        Configuration newConfig) {
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.

        } else {
            // Restore the full-screen UI.
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N) @Override protected void onUserLeaveHint() {
        this.enterPictureInPictureMode();
    }
}
