package com.xxx.ency.view.main;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.xxx.ency.util.AppFileUtil;

import java.io.File;

/**
 * 版本更新
 * Created by codeest on 16/10/10.
 */

public class UpdateService extends Service {

    // 下载器
    private DownloadManager dm;
    // 下载Id
    private long downloadId;
    // 下载地址
    private String downloadUrl;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(receiver);
            checkStatus(context);
            stopSelf();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downloadUrl = intent.getStringExtra("downloadurl");
        startDownload();
        // 注册广播接收者，监听下载状态
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return Service.START_STICKY;
    }

    private void startDownload() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ency.apk");
        AppFileUtil.delFile(file, true);
        // 创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        // 显示下载信息
        request.setTitle("Ency");
        request.setDescription("新版本下载中");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setMimeType("application/vnd.android.package-archive");
        // 设置下载路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ency.apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        // 获取DownloadManager
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = dm.enqueue(request);
        Toast.makeText(this, "后台下载中，请稍候...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 检查下载状态
     */
    private void checkStatus(Context context) {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = dm.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ency.apk");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    }
                    context.startActivity(intent);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        c.close();
    }
}