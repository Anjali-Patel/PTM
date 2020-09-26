package com.ptm.security.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.ptm.security.util.Globals.makeDecision;

public class DecisionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("flag"))
            makeDecision(intent.getStringExtra("flag"), false);
    }
}
