package com.ptm.security.activities;

import android.view.View;

import com.ptm.security.R;
import com.ptm.security.util.BaseActivity;

import static com.ptm.security.util.Globals.openAct;
import static com.ptm.security.util.Globals.toastShort;

public class Welcome extends BaseActivity {

    public void onNew(View v) {
        openAct(SelectAccount.class);
    }

    public void onOld(View v) {
        toastShort("Coming Soon...");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_welcome;
    }
}
