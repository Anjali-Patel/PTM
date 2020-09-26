package com.ptm.security.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ptm.security.R;
import com.ptm.security.util.BaseActivity;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.makeDecision;

@Obfuscate
public class Decision extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.tvBrowser)).setText(getIntent().getStringExtra("browser"));
        ((TextView) findViewById(R.id.tvIpAddress)).setText(getIntent().getStringExtra("ipaddr"));
        ((TextView) findViewById(R.id.tvTimeStamp)).setText(getIntent().getStringExtra("timestamp"));
    }

    public void onApprove(View v) {
        makeDecision("1", true);
    }

    public void onDeny(View v) {
        makeDecision("2", true);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_decision;
    }
}
