package com.ptm.security.util;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptm.security.R;
import com.ptm.security.interfaces.BtnClk;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.closeApp;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.hideDialog;
import static com.ptm.security.util.Globals.showAlertIntfaces;
import static com.ptm.security.util.Globals.showAlertIntfacesOpt;

@Obfuscate
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global_ctx = this;
        setContentView(getLayoutResource());
    }

    @Override
    protected void onResume() {
        super.onResume();
        global_ctx = this;
    }

    protected abstract int getLayoutResource();

    @Override
    public void onBackPressed() {
        String activeClassName=global_ctx.getClass().getSimpleName().toLowerCase();
        if (activeClassName.contains("splash")) {
        } else if (activeClassName.contains("decision") || activeClassName.contains("profile")) {
            showAlertIntfaces(getString(R.string.are_you_sure_you_want_to_exit_app), a -> {
                if(a==1) closeApp();
                else hideDialog();
            });
        } else
            super.onBackPressed();
    }
}