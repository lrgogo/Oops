package com.goman.oops.component;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.goman.oops.App;

/**
 * Created by tnitf on 2016/3/11.
 */
public class BroadcastManager {

    private static volatile BroadcastManager sInstance;

    public static BroadcastManager getInstance() {
        if (sInstance == null) {
            synchronized (BroadcastManager.class) {
                if (sInstance == null) {
                    sInstance = new BroadcastManager();
                }
            }
        }
        return sInstance;
    }

    private LocalBroadcastManager mManager;

    private BroadcastManager() {
        mManager = LocalBroadcastManager.getInstance(App.getContext());
    }

    public void send(Intent intent) {
        mManager.sendBroadcast(intent);
    }

    public void sendSync(Intent intent) {
        mManager.sendBroadcastSync(intent);
    }

    public void register(BroadcastReceiver receiver, String... actions) {
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        mManager.registerReceiver(receiver, filter);
    }

    public void register(BroadcastReceiver receiver, IntentFilter filter) {
        mManager.registerReceiver(receiver, filter);
    }


    public void unregister(BroadcastReceiver receiver) {
        mManager.unregisterReceiver(receiver);
    }

}
