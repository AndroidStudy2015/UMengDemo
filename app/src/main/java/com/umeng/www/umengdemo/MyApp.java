package com.umeng.www.umengdemo;

import android.app.Application;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by apple on 2018/2/8.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonUmnegInit();
//         场景类型设置接口 EScenarioType.E_UM_NORMAL 普通统计场景类型
        UAppInit();


        UPushInit();


    }

    private void UPushInit() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("tokenenenene",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    private void UAppInit() {
//        http://dev.umeng.com/sdk_integate/android_sdk/analytics_doc
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void CommonUmnegInit() {
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "a4f45d55b83c1d9db1df9cde3f5b78c9");//清单文件配置好了appkey,用第一个初始化方法，
        UMConfigure.init(this, "5a7bb5b1f29d9840e800007a", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "a4f45d55b83c1d9db1df9cde3f5b78c9");//这里可以直接代码配置appkey

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);

        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
    }
}
