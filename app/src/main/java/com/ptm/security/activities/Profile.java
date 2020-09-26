package com.ptm.security.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ptm.security.R;
import com.ptm.security.util.BaseActivity;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.ldPr;
import static com.ptm.security.util.Globals.makeDecision;
import static com.ptm.security.util.Globals.openAct;
import static com.ptm.security.util.Prefs.PREF_USER_NAME;

@Obfuscate
public class Profile extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.tvName)).setText(getString(R.string.username) + ": " + ldPr(PREF_USER_NAME));
    }
    public void detailClick(View v) {
        openAct(Details2.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_profile;
    }
}
