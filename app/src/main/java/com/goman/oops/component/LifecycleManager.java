package com.goman.oops.component;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LifecycleManager {

    private static final int MAX_COUNT = 10;

    private ArrayList<WeakReference<Lifecycle>> mRefList;

    public LifecycleManager() {
        mRefList = new ArrayList<>();
    }

    public void register(Lifecycle lifecycle) {
        if (mRefList.size() > MAX_COUNT) {
            int size = mRefList.size();
            for (int i = 0; i < size; i++) {
                Lifecycle c = mRefList.get(i).get();
                if (c == null) {
                    mRefList.set(i, null);
                }
            }
            mRefList.remove(null);
        }
        mRefList.add(new WeakReference<>(lifecycle));
    }

    public void onDestroy() {
        if (mRefList == null) {
            return;
        }
        if (mRefList.size() == 0) {
            mRefList = null;
            return;
        }
        for (WeakReference<Lifecycle> ref : mRefList) {
            Lifecycle lifecycle = ref.get();
            if (lifecycle != null) {
                lifecycle.onDestroy();
            }
        }
        mRefList.clear();
        mRefList = null;
    }

    public interface Lifecycle {
        void onDestroy();
    }

}
