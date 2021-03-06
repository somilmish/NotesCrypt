package com.example.sid.NotesCrypt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.sid.NotesCrypt.utils.AuthenticationHelper;
import com.example.sid.NotesCrypt.R;
import com.example.sid.NotesCrypt.fragments.ChangePasswordFragment;


public class SettingsActivity extends AppCompatActivity {

    public  Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        final SharedPreferences mSharedPreferences = getApplicationContext().
                getSharedPreferences(getApplicationContext().getString(R.string.shred_preference),
                Context.MODE_PRIVATE);
        aSwitch = findViewById(R.id.fingerprintToggle);


        if(!mSharedPreferences.getBoolean(getString(R.string.fingerprint),false)){
            aSwitch.setEnabled(false);
            findViewById(R.id.hardwareMissing).setVisibility(View.VISIBLE);
        }

        if(mSharedPreferences.getBoolean(getString(R.string.use_fingerprint_future),false)){
            aSwitch.setChecked(true);
        }

        else
            aSwitch.setChecked(false);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aSwitch.isChecked()){
                    AuthenticationHelper authenticationHelper = new AuthenticationHelper(SettingsActivity.this);
                    authenticationHelper.listener(getApplicationContext().getString(R.string.fp_true), -1);
                }
                else{
                    AuthenticationHelper authenticationHelper = new AuthenticationHelper(SettingsActivity.this);
                    authenticationHelper.listener(getApplicationContext().getString(R.string.fp_false), -1);
                }
            }
        });

        findViewById(R.id.changePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                changePasswordFragment.setCancelable(false);
                changePasswordFragment.show(SettingsActivity.this.getFragmentManager(), changePasswordFragment.getTag());

            }
        });

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,About.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default: return false;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}