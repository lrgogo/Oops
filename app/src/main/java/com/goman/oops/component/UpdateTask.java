package com.goman.oops.component;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;


import com.goman.oops.App;
import com.goman.oops.FileUtils;
import com.goman.oops.IntentUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lr on 2016/7/7.
 */
public class UpdateTask implements Runnable, LifecycleManager.Lifecycle {

    private AtomicBoolean mDownloading = new AtomicBoolean(false);

    private String mApkName;

    private OkHttpClient mClient;

    private Handler mHandler;

    private UpdateCallback mCallback;

    public UpdateTask() {
        mHandler = new Handler(Looper.getMainLooper());

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
        mClient = builder.build();
    }

    public void update(String url, int versionCode) {
        mApkName = String.format("app_version_%d.apk", versionCode);

        if (isDownloadDone()) {
            if (mCallback != null) {
                mCallback.onDone();
            }
            installApk();
            return;
        }
        if (!mDownloading.get()) {
            mDownloading.set(true);
            if (mCallback != null) {
                mCallback.onStart();
            }
            initDownload(url);
            ThreadPool.getInstance().execute(this);
        }
    }

    private boolean isDownloadDone() {
        File file = FileUtils.getDownloadFile(mApkName);
        if (file.exists()) {
            try {
                PackageManager pm = App.getContext().getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
                if (info != null && info.applicationInfo != null) {
                    return true;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void installApk() {
        File file = FileUtils.getDownloadFile(mApkName);
        if (file.exists()) {
            try {
                IntentUtils.installAPK(file, App.getContext());
            } catch (Throwable e) {
                e.printStackTrace();
                file.delete();
            }
        }
    }


        public String url;

        private long current = 0;
        private boolean append = false;

        private boolean mSuccess = false;

    private void initDownload(String url) {
            this.url = url;
        current = 0;
        append = false;
        mSuccess = false;
        }


    @Override
        public void run() {
            try {
                download();
                if (mSuccess) {
                    onDone();
                } else {
                    onError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                onError();
            }
        }

        private void download() throws Exception {
            File file = FileUtils.getDownloadFile(mApkName);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.canWrite()) {
                current = file.length();
            }
            if (current > 0) {
                append = true;
            }

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Range", "bytes=" + current + "-")
                    .build();
            Response response = mClient.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
                getResponseData(response.body(), file);
            } else if (responseCode == HttpURLConnection.HTTP_OK) {
                append = false;
                current = 0;
                getResponseData(response.body(), file);
            } else if (responseCode == 416) {
                mSuccess = true;
            }
        }

        protected void getResponseData(ResponseBody body, File file) throws Exception {
            InputStream inputStream = body.byteStream();
            if (inputStream != null) {
                long contentLength = body.contentLength() + current;
                FileOutputStream outputStream = new FileOutputStream(file, append);
                try {
                    byte[] tmp = new byte[4096];
                    int l = 0;
                    long old = System.currentTimeMillis();
                    while ((l = inputStream.read(tmp)) != -1) {
                        current += l;
                        outputStream.write(tmp, 0, l);

                        long curr = System.currentTimeMillis();
                        if (curr - old >= 1000) {
                            onProgress(current, contentLength);
                            old = curr;
                        }
                    }
                    outputStream.flush();
                    if (isDownloadDone()) {
                        mSuccess = true;
                    }
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
            }
        }

    private void onProgress(long bytesWritten, long totalSize) {
        final int progress = (int) (bytesWritten * 100 / totalSize);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onProgress(progress);
                }
            }
        });
    }

    private void onDone() {
        mDownloading.set(false);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onDone();
                }
                installApk();
            }
        });
    }

    private void onError() {
        mDownloading.set(false);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        });
    }

    public void setCallback(UpdateCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onDestroy() {
        mCallback = null;
    }

    public interface UpdateCallback {

        void onStart();

        void onDone();

        void onProgress(int progress);

        void onError();

    }

}
