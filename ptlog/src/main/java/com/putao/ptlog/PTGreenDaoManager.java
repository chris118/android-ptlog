package com.putao.ptlog;

import android.content.Context;

import com.putao.ptlog.gen.DaoMaster;
import com.putao.ptlog.gen.DaoSession;
import com.putao.ptlog.gen.PTLogBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaopeng on 2017/7/28.
 */

public class PTGreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static class SingleInstanceHolder
    {
        private static final PTGreenDaoManager INSTANCE = new PTGreenDaoManager();
    }

    public static PTGreenDaoManager getInstance()
    {
        return SingleInstanceHolder.INSTANCE;
    }

    public void init(Context context)
    {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,
                "ptlog");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoMaster getmDaoMaster()
    {
        return mDaoMaster;
    }
    public DaoSession getmDaoSession()
    {
        return mDaoSession;
    }
    public DaoSession getNewSession()
    {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    public void insertLog(int priority, String message){
        PTLogBean entity = new PTLogBean();
        entity.setContent(message);
        entity.setDate(new Date());
        entity.setLevel(priority);
        this.getmDaoSession().getPTLogBeanDao().insert(entity);
    }

    public List<PTLogBean> queryLog(int priority){
        QueryBuilder<PTLogBean> qb = this.getmDaoSession().getPTLogBeanDao().queryBuilder();
        if(priority != 0){ //all = 0
            qb.where(PTLogBeanDao.Properties.Level.eq(priority));
        }
        List<PTLogBean> list = qb.list();
        return list;
    }

    public List<PTLogBean> queryLog(int priority, Date begin, Date end){
        QueryBuilder<PTLogBean> qb = this.getmDaoSession().getPTLogBeanDao().queryBuilder();
        if(priority != 0){ //all = 0
            qb.where(PTLogBeanDao.Properties.Level.eq(priority));
        }
        qb.where(PTLogBeanDao.Properties.Date.between(begin.getTime(), end.getTime()));
        qb.limit(5);
        List<PTLogBean> list = qb.list();
        return list;
    }

    public List<PTLogBean> queryLog(int priority, Date begin, Date end, int limit){
        QueryBuilder<PTLogBean> qb = this.getmDaoSession().getPTLogBeanDao().queryBuilder();

        if(priority != 0){ //all = 0
            qb.where(PTLogBeanDao.Properties.Level.eq(priority));
        }
        qb.where(PTLogBeanDao.Properties.Date.between(begin.getTime(), end.getTime()));
        if(limit > 0){
            qb.orderDesc(PTLogBeanDao.Properties.Id).limit(limit);
            List<PTLogBean> list = qb.list();
            List<?> shallowCopy = list.subList(0, list.size());

            Collections.reverse(shallowCopy);
            return list;
        }else {
            List<PTLogBean> list = qb.list();
            return list;
        }
    }
}
