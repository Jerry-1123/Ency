package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.OneContract;
import com.xxx.ency.model.bean.OneBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/11/1.
 */

public class OnePresenter extends RxPresenter<OneContract.View> implements OneContract.Presenter {

    @Inject
    public OnePresenter() {

    }

    @Override
    public void getData(String url) {
        addSubscribe(Flowable.just(url)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, OneBean>() {
                    @Override
                    public OneBean apply(String s) throws Exception {
                        OneBean oneBean = new OneBean();
                        StringBuffer buffer = new StringBuffer();
                        Document doc = Jsoup.connect(s).get();
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
                        return oneBean;
                    }
                })
                .subscribeWith(new ResourceSubscriber<OneBean>() {
                    @Override
                    public void onNext(OneBean oneBean) {
                        mView.showOneBean(oneBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}