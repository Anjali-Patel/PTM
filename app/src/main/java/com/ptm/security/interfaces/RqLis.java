package com.ptm.security.interfaces;

import org.json.JSONObject;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public interface RqLis {

    void reqEnd(boolean status, boolean parse, String msg, JSONObject response);

}