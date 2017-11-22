package com.sadi.offerian;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sadi.offerian.fragment.TabFragmentEdit;
import com.sadi.offerian.fragment.TabFragmentMain;
import com.sadi.offerian.utils.AppConstant;
import com.sadi.offerian.utils.PersistData;

/**
 * Created by Sadi on 11/22/2017.
 */

public class EditProfileActivity extends AppCompatActivity {

    Context con;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
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

    }
}
