package com.xxx.ency.view.one;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPFragment;
import com.xxx.ency.contract.OneContract;
import com.xxx.ency.model.bean.OneBean;
import com.xxx.ency.presenter.OnePresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/1.
 */

public class OneFragment extends BaseMVPFragment<OnePresenter> implements OneContract.View {

    @BindView(R.id.txt)
    TextView txt;

    StringBuffer title = new StringBuffer();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initialize() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://meiriyiwen.com/").get();
                    // 解析标题
                    Element article_show = doc.getElementById("article_show");
                    title.append(article_show.select("h1").text());
                    title.append("\n");
                    Elements elements = article_show.select("p");
                    for (int i = 0; i < elements.size(); i++) {
                        title.append(elements.get(i).text());
                        title.append("\n");
                    }
                    Message message = handler.obtainMessage();
                    message.obj = title;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Log.i("xrhtest", e.toString());
                }
            }
        }).start();
    }

    @Override
    public void showOneBean(OneBean oneBean) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StringBuffer buffer = (StringBuffer) msg.obj;
            txt.setText(buffer);
        }
    };
}
