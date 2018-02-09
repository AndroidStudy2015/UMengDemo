package com.umeng.www.umengdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class MainActivity3 extends AppCompatActivity {

    private Context mContext = MainActivity3.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);

        Log.e("ccc", getDeviceInfo(this));

//        Button touzhu = (Button) findViewById(R.id.touzhu);
//        Button watchMovie = (Button) findViewById(R.id.watch_movie);
//        Button playGame = (Button) findViewById(R.id.play_game);
//
//
//        touzhu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int conut = 10;
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("moview", "全球风暴");
//                map.put("count", "3");
//
//                MobclickAgent.onEventValue(mContext, "touzhu", map, 10);
//            }
//        });
//
//        watchMovie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "watchMovie");
//
//            }
//        });
//
//        playGame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                MobclickAgent.onEvent(mContext, "playGame");
//
//
//                int duration = 12000; //开发者需要自己计算音乐播放时长
//                Map<String, String> map_value = new HashMap<String, String>();
//                map_value.put("type", "极品飞车");
//                map_value.put("artist", "暴雪");
//                map_value.put("time", "2018-01-07");
//                MobclickAgent.onEventValue(mContext, "playGame", map_value, duration);
//            }
//        });
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;


            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
