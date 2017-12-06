package com.goman.oops.component;

import com.goman.oops.DataException;
import com.goman.oops.NetworkException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by lr on 2016/5/30.
 */
public class TaskError {

    public static final int CODE_NO_LOGIN = 1002;

    public int code;
    public String msg;
    public Throwable throwable;
    public String strCode;

    public TaskError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public TaskError(String msg) {
        this(0, msg);
    }

    public TaskError(String strCode, String msg) {
        this.strCode = strCode;
        this.msg = msg;
    }

    public TaskError(Throwable e) {
        this.throwable = e;
        String msg = "";
        if (e instanceof SocketTimeoutException) {
            msg = "连接服务器超时";
        } else if (e instanceof ConnectException || e instanceof SocketException || e instanceof UnknownHostException) {
            msg = "连接服务器失败";
        } else if (e instanceof NetworkException) {
            msg = "网络不可用";
        } else if (e instanceof JSONException || e instanceof org.json.JSONException) {
            msg = "JSON格式错误";
        } else if (e instanceof DataException) {
            msg = "数据错误";
        }
        this.msg = msg;
    }

}
