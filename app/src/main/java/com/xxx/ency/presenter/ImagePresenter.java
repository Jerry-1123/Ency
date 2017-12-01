package com.xxx.ency.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.ImageContract;
import com.xxx.ency.model.http.ImageApi;
import com.xxx.ency.util.AppFileUtil;

import org.reactivestreams.Publisher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by xiarh on 2017/11/29.
 */

public class ImagePresenter extends RxPresenter<ImageContract.View> implements ImageContract.Presenter {

    private ImageApi imageApi;

    private Context context;

    @Inject
    public ImagePresenter(ImageApi imageApi, Context context) {
        this.imageApi = imageApi;
        this.context = context;
    }

    @Override
    public void download(final String url) {
        addSubscribe(imageApi.download(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ResponseBody>(context, mView) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String fileName = url.substring(url.lastIndexOf("/") + 1) + ".png";
                        File parentFile = AppFileUtil.createFileDir(context, "Ency");
                        File imageFile = new File(parentFile, fileName);
                        if (responseBody == null) {
                            mView.showMsg("保存图片失败~");
                            return;
                        }
                        if (imageFile.exists()) {
                            mView.showMsg("图片已经存在~");
                            return;
                        }
                        try {
                            saveToDisk(imageFile, responseBody);
                            mView.showMsg("成功保存" + imageFile.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            mView.showMsg("保存图片失败~");
                        }
                    }
                }));
    }

    @Override
    public void setWallpaper(final String url) {
        addSubscribe(imageApi.download(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        String fileName = url.substring(url.lastIndexOf("/") + 1) + ".png";
                        File parentFile = AppFileUtil.createFileDir(context, "Ency");
                        File imageFile = new File(parentFile, fileName);
                        if (!imageFile.exists()) {
                            try {
                                saveToDisk(imageFile, responseBody);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return imageFile.getAbsolutePath();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<String>(context, mView) {
                    @Override
                    public void onNext(String filePath) {
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        WallpaperManager manager = WallpaperManager.getInstance(context);
                        try {
                            manager.setBitmap(bitmap);
                            mView.showMsg("设置壁纸成功~");
                        } catch (IOException e) {
                            e.printStackTrace();
                            mView.showMsg("设置壁纸失败~");
                        }
                    }
                }));
    }

    private void saveToDisk(File imageFile, ResponseBody body) throws IOException {
        InputStream is = body.byteStream();
        FileOutputStream fos = new FileOutputStream(imageFile);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
        fos.close();
        bis.close();
        is.close();
    }
}
