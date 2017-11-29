package com.bynotech.offerian;

/**
 * Created by NanoSoft on 11/23/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bynotech.offerian.fragment.TabFragmentEdit;
import com.bynotech.offerian.utils.AppConstant;
import com.bynotech.offerian.utils.PersistData;

/**
 * Created by Sadi on 11/22/2017.
 */

public class EditProfileActivity extends AppCompatActivity {

    Context con;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        con  = this;
        initialization();
    }

    private void initialization() {

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        TabFragmentEdit fragment = new TabFragmentEdit();

        Bundle bundle = new Bundle();
        bundle.putInt("pos", 0);
        PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 0);
        fragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.containerViewProfile, fragment).commit();

        imgBack = (ImageView)findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}