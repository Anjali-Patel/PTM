package com.ptm.security.activities;

import android.view.View;

import com.ptm.security.R;
import com.ptm.security.util.BaseActivity;

import static com.ptm.security.util.Globals.openAct;

public class SelectAccount extends BaseActivity {

    public void onAddPTMAccount(View v) {
        openAct(Login.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_select_account;
    }
}
