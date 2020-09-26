package com.ptm.security.util;

import android.app.Application;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.TTS_AVAILABLE;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.initFirebase;
import static com.ptm.security.util.Globals.tts;

@Obfuscate
public class ParseApplication extends Application implements TextToSpeech.OnInitListener {

    @Override
    public void onCreate() {
        super.onCreate();
        global_ctx = this;
        initFirebase();
        tts = new TextToSpeech(global_ctx, ParseApplication.this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED)
                TTS_AVAILABLE = false;
        } else
            TTS_AVAILABLE = false;
    }
}