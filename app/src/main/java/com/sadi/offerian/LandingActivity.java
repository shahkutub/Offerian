package com.sadi.offerian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sadi on 11/12/2017.
 */

public class LandingActivity extends AppCompatActivity {
    Context con;
    private TextView tvSignUp,tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        con  = this;
        initialization();
    }

    private void initialization() {
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        tvSignUp = (TextView)findViewById(R.id.tvLogin);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,SignUpActivity.class));
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,SignUpActivity.class));
            }
        });

    }
}
