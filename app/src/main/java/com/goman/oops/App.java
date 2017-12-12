package com.goman.oops;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by lingrui on 2017/12/6.
 */

public class App extends Application {

    private static App sContext;

    FloatView mFloatView;

    Activity mResumeActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = (App) getApplicationContext();

        mFloatView = new FloatView();
        mFloatView.init(sContext);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mResumeActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                mResumeActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    public static App getContext(){
        return sContext;
    }

    public Activity getResumeActivity(){
        return mResumeActivity;
    }

    public void backPressResumeActivity(){
        if (mResumeActivity != null){
            mResumeActivity.onBackPressed();
        }
    }


}
