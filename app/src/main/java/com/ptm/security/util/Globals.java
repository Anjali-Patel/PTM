package com.ptm.security.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ptm.security.R;
import com.ptm.security.activities.Decision;
import com.ptm.security.interfaces.BtnClk;
import com.ptm.security.interfaces.Delay;
import com.ptm.security.interfaces.RqLis;
import com.ptm.security.services.DecisionReceiver;
import com.ptm.security.services.ForegroundService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import io.michaelrocks.paranoid.Obfuscate;

import static com.ptm.security.util.Prefs.PREF_ACTIVATION_ID;
import static com.ptm.security.util.Prefs.PREF_FIREBASE_TOKEN;
import static com.ptm.security.util.Prefs.PREF_HEIGHT;
import static com.ptm.security.util.Prefs.PREF_NTFC_ID;
import static com.ptm.security.util.Prefs.PREF_WIDTH;

@Obfuscate
public class Globals {

    public static Context global_ctx;
    public static String BASE_URL = "http://serviceswala.com/";
    public static String PATH_LOGIN = BASE_URL + "Login/";
    public static String PATH_DETAILS = BASE_URL + "mobileapp/";
    public static String PATH_IMAGES = BASE_URL + "media/";
    public static String ACTIVATION_URL = PATH_LOGIN + "app_usercode";
    public static String DECISION_URL = PATH_LOGIN + "app_decision";
    public static String DETAILS_URL = PATH_DETAILS + "details";
    public static String SORTING_URL = PATH_DETAILS + "sorting";
    public static String IMAGES_EQUIPMENT = PATH_IMAGES + "equipment/";
    public static String IMAGES_PROJECT = PATH_IMAGES + "projects/";
    public static String IMAGES_COMPANIES = PATH_IMAGES + "companies/";
    public static String IMAGES_ADDRESS_BOOK = PATH_IMAGES + "addressbook/";
    public static String DB_TABLE = "PTM";
    public static TextToSpeech tts;
    public static boolean TTS_AVAILABLE = true;
    public static NotificationCompat.Builder mBuilder;
    public static Dialog dialog = null;
    public static String SEARCH_URL=PATH_DETAILS+"search";

    public static void showAlert(boolean a, String message) {
        if (!message.isEmpty()) {
            dialog = new Dialog(global_ctx, R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.custom_alert);
            dialog.setCancelable(false);
            TextView tv1 = dialog.findViewById(R.id.tv1);
            if (message.length() > 200)
                tv1.setTextSize(12f);
            Button b1 = dialog.findViewById(R.id.b1);
            tv1.setText(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));
            b1.setOnClickListener(v -> {
                if (a) dialog.dismiss();
                else close();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showAlertIntface(String message, final BtnClk btnClk) {
        dialog = new Dialog(global_ctx, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        Button b1 = dialog.findViewById(R.id.b1);
        tv1.setText(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));
        b1.setOnClickListener(v -> {
            dialog.dismiss();
            btnClk.btnclk(0);
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlertIntfaces(String message, final BtnClk btnClk) {
        dialog = new Dialog(global_ctx, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        if (message.length() > 200)
            tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(global_ctx.getString(R.string.yes));
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setVisibility(View.VISIBLE);
        tv1.setText(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));
        b1.setOnClickListener(v -> {
            dialog.dismiss();
            btnClk.btnclk(1);
        });
        b2.setOnClickListener(v -> {
            dialog.dismiss();
            btnClk.btnclk(2);
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlertIntfacesOpt(String b1Txt, String b2Txt, String message, final BtnClk btnClk) {
        dialog = new Dialog(global_ctx, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(true);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        if (message.length() > 200)
            tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(b1Txt);
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setText(b2Txt);
        b2.setVisibility(View.VISIBLE);
        tv1.setText(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));
        b1.setOnClickListener(v -> {
            dialog.dismiss();
            btnClk.btnclk(1);
        });
        b2.setOnClickListener(v -> {
            dialog.dismiss();
            btnClk.btnclk(2);
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void svPr(String key, String value) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String ldPr(String key) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        String val;
        try {
            if (key.length() > 0) key = key.trim();
            val = sharedPreferences.getString(key, "");
        } catch (Exception e) {
            val = "";
        }
        return val;
    }

    public static void svIPr(String key, int value) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int ldIPr(String key) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        int val;
        try {
            val = sharedPreferences.getInt(key, 0);
        } catch (Exception e) {
            val = 0;
        }
        return val;
    }




    public static void svLPr(String key, long value) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long ldLPr(String key) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        long val;
        try {
            val = sharedPreferences.getLong(key, 0);
        } catch (Exception e) {
            val = 0;
        }
        return val;
    }

    public static void svWPr(String key, float value) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static Float ldWPr(String key) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        Float val;
        try {
            val = sharedPreferences.getFloat(key, 0.0f);
        } catch (Exception e) {
            val = 0.0f;
        }
        return val;
    }

    public static void svBPr(String key, boolean value) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean ldBPr(String key) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        boolean val;
        try {
            val = sharedPreferences.getBoolean(key, false);
        } catch (Exception e) {
            val = false;
        }
        return val;
    }

    public static void delay(long millis, final Delay delay) {
        new Handler().postDelayed(delay::done, millis);
    }

    public static void openAct(Class c, String... code) {
        try {
            Intent intent = new Intent(global_ctx, c);
            if (code.length > 0) {
                for (int i = 0; i < code.length; i++) {
                    intent.putExtra(code[i], code[++i]);
                }
            }
            global_ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openClearAct(Class c, Context... contexts) {
        try {
            Intent intent = new Intent(contexts.length > 0 ? contexts[0] : global_ctx, c);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            global_ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            ((Activity) global_ctx).finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeApp() {
        ((Activity) global_ctx).finishAffinity();
    }

    public static void restartApp() {
        Intent i = global_ctx.getPackageManager().
                getLaunchIntentForPackage(global_ctx.getPackageName());
        if (i != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            global_ctx.startActivity(i);
        }
    }

    public static void restartActivity() {
        ((Activity) global_ctx).finish();
        global_ctx.startActivity(((Activity) global_ctx).getIntent());
    }

    public static boolean checkMinOSVer(int a) {
        return Build.VERSION.SDK_INT >= a;
    }

    public static void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) global_ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    0);
        }
    }

    public static void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) global_ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getRootView().getWindowToken(), 0);
    }

    private static ViewGroup getRootView() {
        return (ViewGroup) ((ViewGroup) ((Activity) global_ctx)
                .findViewById(android.R.id.content)).getChildAt(0);
    }

    public static void speakOut(String text) {
        if (TTS_AVAILABLE)
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, text);
    }

    public static void setCursor(EditText editText) {
        editText.setSelection(editText.getText().toString().length());
        editText.requestFocus();
    }

    public static void createNotificationChannel(String ntfcChannel) {
        CharSequence channelName = "Login Alerts";
        String channelDesc = "This Notification channels keeps you alarmed about the Login Attempts.";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ntfcChannel, channelName, importance);
            channel.setDescription(channelDesc);
            NotificationManager notificationManager = global_ctx.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            NotificationChannel currChannel = notificationManager.getNotificationChannel(ntfcChannel);
            if (currChannel == null)
                notificationManager.createNotificationChannel(channel);
        }
    }

    public static void createNtfc(String message, String browser, String ipaddr, String timestamp) {
        String ntfcChannel = "1001";
        createNotificationChannel(ntfcChannel);
        speakOut(message);
        Intent intent = new Intent(global_ctx, Decision.class);
        intent.putExtra("browser", browser);
        intent.putExtra("ipaddr", ipaddr);
        intent.putExtra("timestamp", timestamp);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(global_ctx, 0, intent, 0);
        mBuilder = new NotificationCompat.Builder(global_ctx, ntfcChannel)
                .setSmallIcon(R.drawable.ic_notification)
//                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        RemoteViews notificationView = new RemoteViews(global_ctx.getPackageName(), R.layout.custom_ntfc_buttons);
        Intent intentApprove = new Intent(global_ctx, DecisionReceiver.class);
        intentApprove.putExtra("flag", "1");
        intentApprove.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent approveIntent = PendingIntent.getBroadcast(global_ctx, 0, intentApprove, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentDeny = new Intent(global_ctx, DecisionReceiver.class);
        intentDeny.putExtra("flag", "2");
        intentDeny.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent denyIntent = PendingIntent.getBroadcast(global_ctx, 0, intentDeny, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationView.setTextViewText(R.id.tvNtfc, message);
        notificationView.setOnClickPendingIntent(R.id.btnApprove, approveIntent);
        notificationView.setOnClickPendingIntent(R.id.btnDeny, denyIntent);
        mBuilder.setCustomBigContentView(notificationView);
        svIPr(PREF_NTFC_ID, (int) (System.currentTimeMillis() / 4));
        NotificationManagerCompat.from(global_ctx).notify(ldIPr(PREF_NTFC_ID), mBuilder.build());
    }

    public static void cancelNtfc() {
        ((NotificationManager) global_ctx.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(ldIPr(PREF_NTFC_ID));
    }

    public static void toast(String msg) {
        toastExecutor(Toast.LENGTH_LONG, msg);
    }

    public static void toastShort(String msg) {
        toastExecutor(Toast.LENGTH_SHORT, msg);
    }

    public static void toastExecutor(int a, String msg) {
        try {
            if (msg.length() == 0) return;
            if (global_ctx instanceof Activity)
                ((Activity) global_ctx).runOnUiThread(() -> Toast.makeText(global_ctx, msg, a).show());
            else Toast.makeText(global_ctx, msg, a).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isRooted() {
        boolean f1 = android.os.Build.TAGS != null && android.os.Build.TAGS.contains("test-keys");
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        boolean f2 = false;
        for (String path : paths)
            if (new File(path).exists()) f2 = true;
        Process process = null;
        boolean f3 = false;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) f3 = true;
        } catch (Throwable ignored) {
        } finally {
            if (process != null) process.destroy();
        }
        return f1 || f2 || f3;
    }

    public static void getHeightAndWidth() {
        if (ldIPr(PREF_HEIGHT) == 0 || ldIPr(PREF_WIDTH) == 0) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) global_ctx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            svIPr(PREF_HEIGHT, displaymetrics.heightPixels);
            svIPr(PREF_WIDTH, displaymetrics.widthPixels);
        }
    }

    public static AlertDialog progress() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(global_ctx, R.style.AlertDialogLoader);
        alertDialog.setCancelable(false);
        ProgressBar pb = new ProgressBar(global_ctx);
        TextView tv = new TextView(global_ctx);
        tv.setText(global_ctx.getString(R.string.loading));
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.leftMargin = 60;
        params.rightMargin = 60;
        params.topMargin = 40;
        params.bottomMargin = 20;
        pb.setLayoutParams(params);
        tv.setLayoutParams(params);
        LinearLayout ll = new LinearLayout(global_ctx);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(pb);
        ll.addView(tv);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(25);
        shape.setColor(global_ctx.getResources().getColor(R.color.colorTransparentPartial3));
        ll.setBackground(shape);
        alertDialog.setView(ll);
        AlertDialog alert = alertDialog.create();
        alert.setOnShowListener(dialog -> {
            WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            alert.getWindow().setAttributes(lp);
            alert.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
        return alert;
    }

    public static boolean iO(boolean... backgrnd) {
        NetworkInfo info = ((ConnectivityManager) global_ctx.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            if (backgrnd.length > 0) if (backgrnd[0]) return false;
            showAlert(true, global_ctx.getString(R.string.no_internet));
            return false;
        }
        if (info.isRoaming())
            return true;
        return true;
    }

    public static void hideAlert(AlertDialog a) {
        try {
            if (a != null && a.isShowing())
                a.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String str2Js(HashMap<String, String> params) {
        StringBuilder result = new StringBuilder();
        result.append("{\"");
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("\"");
            try {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("\":\"");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("\",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.deleteCharAt(result.length() - 1);
        result.append("}");
        String temp = result.toString();
        temp = temp.replace("%7B", "{");
        temp = temp.replace("%22", "\"");
        temp = temp.replace("%3A", ":");
        temp = temp.replace("%2C", ",");
        temp = temp.replace("%7D", "}");
        temp = temp.replace("}\"", "}");
        temp = temp.replace("\"{", "{");
        temp = temp.replace("%2F", "/");
        temp = temp.replace("%40", "@");
        temp = temp.replace("+", " ");
        temp = temp.replace("%2B", "+");
        temp = temp.replace("%5B", "[");
        temp = temp.replace("%5D", "]");
        temp = temp.replace("\"[", "[");
        temp = temp.replace("]\"", "]");
        temp = temp.replace("%3D", "=");
        temp = temp.replace("%0A", "\n");
        temp = temp.replace("%21", "!");
        temp = temp.replace("%23", "#");
        temp = temp.replace("%24", "$");
        temp = temp.replace("%25", "%");
        temp = temp.replace("%26", "&");
        temp = temp.replace("%27", "'");
        temp = temp.replace("%28", "(");
        temp = temp.replace("%29", ")");
        temp = temp.replace("%2A", "*");
        temp = temp.replace("%2D", "-");
        temp = temp.replace("%2E", ".");
        temp = temp.replace("%3B", ";");
        temp = temp.replace("%3C", "<");
        temp = temp.replace("%3E", ">");
        temp = temp.replace("%3F", "?");
        temp = temp.replace("%5C", "\\");
        temp = temp.replace("%5E", "^");
        temp = temp.replace("%5F", "_");
        temp = temp.replace("%60", "`");
        temp = temp.replace("%7C", "|");
        temp = temp.replace("%7E", "~");
        return temp;
    }

    public static void basicGetReq(String requestURL, final RqLis rqLis) {
        RequestQueue queue = Volley.newRequestQueue(global_ctx);
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    try {
                        if (rqLis != null)
                            rqLis.reqEnd(false, false, response, null);
                        queue.stop();
                        queue.cancelAll("tag1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
            toast("Error - " + error.networkResponse);
            queue.stop();
            queue.cancelAll("tag1");
        });
        stringRequest.setTag("tag1");
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public static void pstPrms(boolean progress, String requestURL,
                               HashMap<String, String> postDataParams, final RqLis rqLis) {
        Log.d("Volley URL", requestURL + "z");
        try {
            if (iO()) {
                final AlertDialog a = progress ? progress() : null;
                if (progress) a.show();
                RequestQueue requestQueue = Volley.newRequestQueue(global_ctx);
                requestQueue.getCache().clear();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL,
                        response -> {
                            hideAlert(a);
                            try {
                                Log.d("Volley Response", response + "z");
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.optInt("status");
                                String msg = jsonObject.optString("message");
                                jsonObject.remove("status");
                                jsonObject.remove("message");
                                boolean parse = jsonObject.length() > 0;
                                rqLis.reqEnd(status == 0, parse, msg, jsonObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                                toast("Something went wrong. Please try after sometime.");
                            }
                            requestQueue.stop();
                            requestQueue.cancelAll("tag1");
                        },
                        error -> {
                            Log.d("Volley Err Response", error.toString() + "z");
                            requestQueue.stop();
                            requestQueue.cancelAll("tag1");
                            hideAlert(a);
                            toast("Something went wrong. Please try later.");
                        }
                ) {
                    @Override
                    public Map<String, String> getParams() {
                        postDataParams.put("code", ldPr(PREF_ACTIVATION_ID));
                        postDataParams.put("token", ldPr(PREF_FIREBASE_TOKEN));
                        Log.d("Volley Params", postDataParams.toString() + "z");
                        return postDataParams;
                    }
                };
                stringRequest.setTag("tag1");
                stringRequest.setShouldCache(false);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "getInstanceId failed", task.getException());
                        return;
                    }
                    svPr(PREF_FIREBASE_TOKEN, task.getResult().getToken());
                });
    }

    public static boolean isAdvancedOS() {
        String s1 = Build.VERSION.RELEASE;
        if (s1.contains("."))
            s1 = s1.substring(0, s1.indexOf("."));
        return !(s1.equals("4") || s1.equals("5") || s1.equals("6") || s1.equals("7") || s1.equals("8") || s1.equals("9"));
    }

    public static boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) global_ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

    public static void startForeground() {
//        if (!isServiceRunning(ForegroundService.class))
        ContextCompat.startForegroundService(global_ctx, new Intent(global_ctx, ForegroundService.class));
    }

    public static void stopForeground() {
        if (isServiceRunning(ForegroundService.class))
            global_ctx.stopService(new Intent(global_ctx, ForegroundService.class));
    }

    public static void makeDecision(String decision, boolean appOpen) {
        HashMap<String, String> values = new HashMap<>();
        values.put("approve", decision);
        pstPrms(appOpen, DECISION_URL, values, (status, parse, msg, response) -> {
            if (!status) {
                if (appOpen) showAlert(true, msg);
                else toast(msg);
            } else {
                toast(msg);
                if (appOpen)
                    closeApp();
                else
                    cancelNtfc();
            }
        });
    }
}
