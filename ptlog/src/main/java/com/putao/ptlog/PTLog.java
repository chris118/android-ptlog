package com.putao.ptlog;

import android.content.Context;
import android.content.Intent;

//import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
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
    private static PTSqliteHelper mSqliteHelper;

    public static void init(Context context, int diskLogLevel, int savedDay){
        mContext = context;
        mDisk_log_level = diskLogLevel;

        // init Logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("android-client")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        //Stetho
        //Stetho.initializeWithDefaults(context);

        //init sqlite helper
        mSqliteHelper = PTSqliteHelper.getInstance(context);

        //remove the history log
        mSqliteHelper.deleteLogWithDay(savedDay);
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
            mSqliteHelper.insertLog(priority, formatMessage);
        }
    }

    public static List<PTLogBean> queryLog(int priority, Date begin, Date end, int limit){
        List<PTLogBean> logList = mSqliteHelper.queryLog(priority, begin, end, limit);
        return logList;
    }

    public static void showViewer(){
        Intent intent = new Intent(mContext, PTLogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
