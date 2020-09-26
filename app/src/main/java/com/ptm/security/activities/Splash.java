package com.ptm.security.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.ptm.security.R;
import com.ptm.security.util.BaseActivity;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.checkMinOSVer;
import static com.ptm.security.util.Globals.closeApp;
import static com.ptm.security.util.Globals.delay;
import static com.ptm.security.util.Globals.getHeightAndWidth;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.initFirebase;
import static com.ptm.security.util.Globals.isRooted;
import static com.ptm.security.util.Globals.ldBPr;
import static com.ptm.security.util.Globals.ldIPr;
import static com.ptm.security.util.Globals.ldPr;
import static com.ptm.security.util.Globals.openAct;
import static com.ptm.security.util.Globals.showAlertIntface;
import static com.ptm.security.util.Globals.speakOut;
import static com.ptm.security.util.Globals.startForeground;
import static com.ptm.security.util.Prefs.PREF_FIREBASE_TOKEN;
import static com.ptm.security.util.Prefs.PREF_HEIGHT;
import static com.ptm.security.util.Prefs.PREF_LOGIN_STATE;
import static com.ptm.security.util.Prefs.PREF_WIDTH;

@Obfuscate
public class Splash extends BaseActivity {

    private ImageView iv1;
    private Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global_ctx = this;
        try {
            ProviderInstaller.installIfNeeded(global_ctx);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        initFirebase();
        Log.d("firebase_token", ldPr(PREF_FIREBASE_TOKEN));
        getHeightAndWidth();
        getSupportActionBar().hide();
        iv1 = findViewById(R.id.iv1);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams((int) (ldIPr(PREF_WIDTH) * 0.65), (int) (ldIPr(PREF_HEIGHT) * 0.65));
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        iv1.setLayoutParams(rlp);
        delay(250, () -> {
            iv1.startAnimation(fadeIn);
            iv1.setVisibility(View.VISIBLE);
            speakOut(getString(R.string.welcome_to_ptm_security));
            delay(2500, () -> {
                if (isRooted() && false)
                    showAlertIntface(getString(R.string.rooted_device_detected), a -> closeApp());
                else nextMeth();
            });
        });
        checkBattery();
        startForeground();
    }

    private void checkBattery() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (checkMinOSVer(Build.VERSION_CODES.M)) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
                try {
                    startActivity(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getPackageName())));
                } catch (Exception e) {
                    mySwitch();
                }
            } else mySwitch();
        } else mySwitch();
    }

    private void mySwitch() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    private void nextMeth() {
        openAct(ldBPr(PREF_LOGIN_STATE) ? Profile.class : Welcome.class);
        finish();
    }
}