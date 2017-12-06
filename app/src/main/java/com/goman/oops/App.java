package com.goman.oops;

import android.app.Application;

/**
 * Created by lingrui on 2017/12/6.
 */

public class App extends Application {

    private static App sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = (App) getApplicationContext();
    }

    public static App getContext(){
        return sContext;
    }
}
