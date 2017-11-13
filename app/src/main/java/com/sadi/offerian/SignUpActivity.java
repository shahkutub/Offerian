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

public class SignUpActivity extends AppCompatActivity {
    Context con;
    private TextView tvSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        con  = this;
        initialization();
    }

    private void initialization() {
        tvSubmit = (TextView)findViewById(R.id.tvSubmit);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,MainActivity.class));
            }
        });
    }
}