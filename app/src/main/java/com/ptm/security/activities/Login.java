package com.ptm.security.activities;

import android.view.View;
import android.widget.EditText;

import com.ptm.security.R;
import com.ptm.security.util.BaseActivity;

import java.util.HashMap;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.ACTIVATION_URL;
import static com.ptm.security.util.Globals.hideKeyboard;
import static com.ptm.security.util.Globals.openClearAct;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;
import static com.ptm.security.util.Globals.svBPr;
import static com.ptm.security.util.Globals.svPr;
import static com.ptm.security.util.Globals.toast;
import static com.ptm.security.util.Prefs.PREF_ACTIVATION_ID;
import static com.ptm.security.util.Prefs.PREF_LOGIN_STATE;
import static com.ptm.security.util.Prefs.PREF_USER_NAME;

@Obfuscate
public class Login extends BaseActivity {

    public void onParent(View v) {
        hideKeyboard();
    }

    public void onLogin(View v) {
        String activationId = ((EditText) findViewById(R.id.et1)).getText().toString();
        if (activationId.length() < 4)
            showAlert(true, getString(R.string.please_enter_a_valid_activation_id));
        else {
            svPr(PREF_ACTIVATION_ID, activationId);
            pstPrms(true, ACTIVATION_URL, new HashMap<>(), (status, parse, msg, response) -> {
                if (!status) showAlert(true, msg);
                else {
                    svBPr(PREF_LOGIN_STATE, true);
                    svPr(PREF_USER_NAME, response.optString("name"));
                    toast(msg);
                    openClearAct(Profile.class);
                }
            });
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }
}
