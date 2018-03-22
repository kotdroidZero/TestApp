package com.kotdroid.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AutofillActivity extends AppCompatActivity {

    EditText etEmail,etPassword;

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autofill);

        etEmail=findViewById(R.id.etPhone);
        etPassword=findViewById(R.id.etPassword);

        etEmail.setAutofillHints(View.AUTOFILL_HINT_PHONE);
        etPassword.setAutofillHints(View.AUTOFILL_HINT_PASSWORD);

        btnSubmit=findViewById(R.id.btnSubmit);


    }
}
