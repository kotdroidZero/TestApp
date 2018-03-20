package com.kotdroid.testapp;

import android.annotation.SuppressLint;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.support.v4.widget.TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM;

public class AutoSizingTextViewsActivity extends AppCompatActivity {

    TextView tvAutoSize;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_sizing_text_views);

        tvAutoSize=findViewById(R.id.tvAutoSize);
        tvAutoSize.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_UNIFORM);


    }
}
