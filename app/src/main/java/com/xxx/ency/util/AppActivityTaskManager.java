package com.xxx.ency.util;

import android.app.Activity;

import java.util.Stack;

/**
 * 管理和回收Act
 * Created by xiarh on 2017/9/13.
 */

public class AppActivityTaskManager {

    //存储ActivityStack
    private static Stack<Activity> activityStack = new Stack<>();

    //单例模式
    private static AppActivityTaskManager instance;

    /**
     * 获得单例对象
     *
     * @return
     */
    public static AppActivityTaskManager getInstance() {
        if (instance == null) {
            synchronized (AppActivityTaskManager.class) {
                if (instance == null) {
                    instance = new AppActivityTaskManager();
                }
            }
        }
        return instance;
    }

    /**
     * 将Act放入堆栈集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        LogUtil.i("AppActivityTaskManager-->>addActivity", activity != null ? activity.toString() : "");
        if (null == activityStack) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除指定的Act
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        LogUtil.i("AppActivityTaskManager-->>removeActivity", activity != null ? activity.toString() : "");
        if (null != activity) {
            // 为与系统Activity栈保持一致，
            // 且考虑到手机设置项里的"不保留活动"选项引起的Activity生命周期调用onDestroy()方法所带来的问题,此处需要作出如下修正
            if (activity.isFinishing()) {
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 获取当前Act对象
     *
     * @return Activity 当前Act
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        LogUtil.i("AppActivityTaskManager-->>currentActivity", activity + "");
        return activity;
    }

    /**
     * 栈中销毁并移除所有Act对象
     */
    public void removeAllActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            //创建临时集合对象
            Stack<Activity> activityTemp = new Stack<>();
            for (Activity activity : activityStack) {
                if (null != activity) {
                    //添加到临时集合中
                    activityTemp.add(activity);
                    //结束Activity
                    activity.finish();
                }
            }
            activityStack.removeAll(activityTemp);
        }
        LogUtil.i("AppActivityTaskManager-->>removeAllActivity", "removeAllActivity");
        System.gc();
        System.exit(0);
    }
}
