package com.kotdroid.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AutofillActivity extends AppCompatActivity {

    EditText etEmail,etPassword;

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autofill);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);

        btnSubmit=findViewById(R.id.btnSubmit);


    }
}
