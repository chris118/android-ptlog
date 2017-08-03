package com.putao.ptlog;

import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.putao.ptlog.viewer.PTLogActivity;

import java.util.Date;
import java.util.List;

/**
 * Created by xiaopeng on 2017/7/28.
 */

public final class PTLog {
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int ERROR = 4;

    private static int mDisk_log_level = ERROR;
    private static Context mContext = null;

    public static void init(Context context, int diskLogLevel){
        mContext = context;
        mDisk_log_level = diskLogLevel;

        // init Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        // init GreenDAO
        PTGreenDaoManager.getInstance().init(context);
    }

    public static void d(String message, Object... args) {
        log(DEBUG, message, args);
    }

    public static void i(String message, Object... args) {
        log(INFO, message, args);
    }

    public static void e(String message, Object... args) {
        log(ERROR, message, args);
    }

    private static void log(int priority,String message, Object... args){
        switch (priority){
            case DEBUG:
                Logger.d(message,args);
                break;
            case INFO:
                Logger.i(message,args);
                break;
            case ERROR:
                Logger.e(message,args);
                break;
        }

        // save log into sqlite
        if(priority >= mDisk_log_level){
            String formatMessage = message;
            if( args != null && args.length != 0 ) {
                formatMessage = String.format(message, args);
            }
            PTGreenDaoManager.getInstance().insertLog(priority, formatMessage);
        }
    }

    public static void showViewer(){
        Intent intent = new Intent(mContext, PTLogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public static List<PTLogBean> queryLog(int priorty){
        List<PTLogBean> logList = PTGreenDaoManager.getInstance().queryLog(priorty);
        return logList;
    }

    public static List<PTLogBean> queryLog(int priority, Date begin, Date end, int limit){
        List<PTLogBean> logList = PTGreenDaoManager.getInstance().queryLog(priority, begin, end, limit);
        return logList;
    }
}
