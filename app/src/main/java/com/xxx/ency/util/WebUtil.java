package com.xxx.ency.util;

import android.content.Context;
import android.content.Intent;

import com.xxx.ency.view.web.WebActivity;

/**
 * Created by xiarh on 2017/11/10.
 */

public class WebUtil {

    /**
     * @param context
     * @param guid       Guid
     * @param imageUrl   图片地址
     * @param type       类型
     * @param url        文章地址
     * @param title      标题
     * @param isShowLike 是否显示收藏
     */
    public static void openUrl(Context context, String guid, String imageUrl, int type
            , String url, String title, boolean isShowLike) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("guid", guid);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("type", type);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("isshow", isShowLike);
        context.startActivity(intent);
    }
}