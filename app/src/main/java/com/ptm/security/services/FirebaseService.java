package com.ptm.security.services;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ptm.security.activities.Decision;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Globals.createNtfc;
import static com.ptm.security.util.Globals.isAdvancedOS;
import static com.ptm.security.util.Globals.openAct;
import static com.ptm.security.util.Globals.speakOut;

@Obfuscate
public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (isAdvancedOS())
            createNtfc(remoteMessage.getData().get("message"), remoteMessage.getData().get("browser"), remoteMessage.getData().get("ipaddr"), remoteMessage.getData().get("timestamp"));
        else {
            speakOut(remoteMessage.getData().get("message"));
            openAct(Decision.class, "browser", remoteMessage.getData().get("browser"), "ipaddr", remoteMessage.getData().get("ipaddr"), "timestamp", remoteMessage.getData().get("timestamp"));
        }
    }
}
