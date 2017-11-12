package com.sadi.offerian;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Sadi on 11/12/2017.
 */

public class SignUpActivity extends AppCompatActivity {
    Context con;
    private TextView tvSignUp,tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        con  = this;
        initialization();
    }

    private void initialization() {

    }
}