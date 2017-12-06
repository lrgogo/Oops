package com.goman.oops;

import android.os.Environment;

import com.goman.oops.App;

import java.io.File;

public class FileUtils {

    //文件夹名称
    private static final String FILE_ROOT_DIR_NAME = "Fordeal";

    private static final String DOWNLOAD_NAME = "download";
    private static final String IMAGE_NAME = "image";

    //文件夹路径
    private static File FILE_ROOT_DIR = null;
    private static File CACHE_ROOT_DIR = null;

    private static File DOWNLOAD_DIR = null;
    private static File IMAGE_DIR = null;


    public static void initRootDir() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            FILE_ROOT_DIR = new File(Environment.getExternalStorageDirectory(), FILE_ROOT_DIR_NAME);
            CACHE_ROOT_DIR = App.getContext().getExternalCacheDir();
        } else {
            FILE_ROOT_DIR = App.getContext().getFilesDir();
            CACHE_ROOT_DIR = App.getContext().getCacheDir();
        }
        DOWNLOAD_DIR = new File(FILE_ROOT_DIR, DOWNLOAD_NAME);
        IMAGE_DIR = new File(FILE_ROOT_DIR, IMAGE_NAME);

        mkdirs();
    }

    private static void mkdirs() {
        if (!DOWNLOAD_DIR.exists()) {
            DOWNLOAD_DIR.mkdirs();
        }

        if (!IMAGE_DIR.exists()) {
            IMAGE_DIR.mkdirs();
        }
    }

    public static File getDownloadDir() {
        if (!DOWNLOAD_DIR.exists()) {
            DOWNLOAD_DIR.mkdirs();
        }
        return DOWNLOAD_DIR;
    }

    public static File getImageDir() {
        if (!IMAGE_DIR.exists()) {
            IMAGE_DIR.mkdirs();
        }
        return IMAGE_DIR;
    }

    public static File getDownloadFile(String name) {
        if (!DOWNLOAD_DIR.exists()) {
            DOWNLOAD_DIR.mkdirs();
        }
        File file = new File(DOWNLOAD_DIR, name);
        return file;
    }

    public static File getImageFile(String name) {
        if (!IMAGE_DIR.exists()) {
            IMAGE_DIR.mkdirs();
        }
        File file = new File(IMAGE_DIR, name);
        return file;
    }

    public static File getCacheFile(String name) {
        File file = new File(CACHE_ROOT_DIR, name);
        return file;
    }

}
