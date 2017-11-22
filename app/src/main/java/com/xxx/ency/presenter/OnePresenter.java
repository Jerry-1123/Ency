package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.OneContract;
import com.xxx.ency.model.bean.OneBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2017/11/1.
 */

public class OnePresenter extends RxPresenter<OneContract.View> implements OneContract.Presenter {

    private Context context;

    @Inject
    public OnePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getData(final String url) {
        addSubscribe(Flowable.create
                (new FlowableOnSubscribe<OneBean>() {
                    @Override
                    public void subscribe(FlowableEmitter<OneBean> emitter) throws Exception {
                        OneBean oneBean = new OneBean();
                        StringBuffer buffer = new StringBuffer();
                        Document doc = Jsoup.connect(url).get();
                        // 解析标题
                        Element article_show = doc.getElementById("article_show");
                        oneBean.setTitle(article_show.select("h1").text());
                        Elements elements = article_show.select("p");
                        for (int i = 0; i < elements.size(); i++) {
                            // 解析作者
                            if (i == 0) {
                                oneBean.setAuthor(elements.get(i).text());
                            }
                            // 解析内容
                            else {
                                buffer.append("\u3000\u3000");
                                buffer.append(elements.get(i).text() + "\n");
                                buffer.append("\n");
                            }
                        }
                        oneBean.setContent(buffer.toString());
                        emitter.onNext(oneBean);
                        emitter.onComplete();
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OneBean>(context, mView) {
                    @Override
                    public void onNext(OneBean oneBean) {
                        mView.showOneBean(oneBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetData();
                    }
                }));
    }
}