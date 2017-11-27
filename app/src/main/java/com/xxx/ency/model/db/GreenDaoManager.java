package com.xxx.ency.model.db;

import android.content.Context;

import com.xxx.ency.model.bean.LikeBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by xiarh on 2017/11/10.
 */

public class GreenDaoManager {

    private DaoMaster mDaoMaster;

    private DaoSession mDaoSession;

    @Inject
    public GreenDaoManager(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "like", null);//此处为自己需要处理的表
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    public LikeBeanDao getLikeBeanDao() {
        return getSession().getLikeBeanDao();
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<LikeBean> queryAll() {
        return getLikeBeanDao()
                .queryBuilder()
                .orderDesc(LikeBeanDao.Properties.Time)
                .build()
                .list();
    }

    /**
     * 新增
     *
     * @param likeBean
     */
    public void insert(LikeBean likeBean) {
        getLikeBeanDao().insert(likeBean);
    }

    /**
     * 根据Guid查询
     *
     * @param guid
     * @return
     */
    public boolean queryByGuid(String guid) {
        LikeBean bean = getLikeBeanDao()
                .queryBuilder()
                .where(LikeBeanDao.Properties.Guid.eq(guid))
                .build()
                .unique();
        if (null == bean) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据guid删除
     */
    public void deleteByGuid(String guid) {
        LikeBean bean = getLikeBeanDao()
                .queryBuilder()
                .where(LikeBeanDao.Properties.Guid.eq(guid))
                .build()
                .unique();
        if (null != bean) {
            getLikeBeanDao().delete(bean);
        }
    }

    /**
     * 删除
     *
     * @param likeBean
     */
    public void delete(LikeBean likeBean) {
        getLikeBeanDao().delete(likeBean);
    }
}