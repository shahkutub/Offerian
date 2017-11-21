package com.sadi.offerian;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sadi on 11/21/2017.
 */

public class SMScodeVarifyActivity extends AppCompatActivity{

    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_veryfication);

        //init views
        final EditText etSmsCode = (EditText) findViewById(R.id.etSmsCode);
        final Button BtnSubmitCode = (Button) findViewById(R.id.BtnSubmitCode);

        //init SmsVerifyCatcher
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                etSmsCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        //set phone number filter if needed
        smsVerifyCatcher.setPhoneNumberFilter("777");
        //smsVerifyCatcher.setFilter("regexp");

        //button for sending verification code manual
        BtnSubmitCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification code to server
            }
        });
    }

    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
