package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.ImageContract;
import com.xxx.ency.model.http.ImageApi;
import com.xxx.ency.util.AppFileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
                        downloadToDisk(url, responseBody);
                    }
                }));
    }

    private void downloadToDisk(String url, ResponseBody body) {
        String fileName = url.substring(url.lastIndexOf("/") + 1) + ".png";
        File parentFile = AppFileUtil.createFileDir(context, "Ency");
        File imageFile = new File(parentFile, fileName);
        if (body == null) {
            mView.showMsg("保存图片失败~");
            return;
        }
        if (imageFile.exists()) {
            mView.showMsg("图片已经存在~");
            return;
        }
        try {
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
            mView.showMsg("成功保存在" + imageFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            mView.showMsg("保存图片失败~");
        }
    }
}
