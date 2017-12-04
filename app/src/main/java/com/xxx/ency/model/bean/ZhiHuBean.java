package com.xxx.ency.model.bean;

import java.util.List;

/**
 * Created by xiarh on 2017/12/4.
 */

public class ZhiHuBean {

    /**
     * date : 20171204
     * stories : [{"images":["https://pic2.zhimg.com/v2-88f36e36780f9d7e90e8d6ee597b8c1d.jpg"],"type":0,"id":9659668,"ga_prefix":"120416","title":"人生中第一次「吃土」，咂咂嘴\u2026\u2026没想到还治好了闹肚子"},{"images":["https://pic1.zhimg.com/v2-2a4a81a237dbb4ce6b79ea30ab244588.jpg"],"type":0,"id":9659617,"ga_prefix":"120415","title":"硬装 7 万，软装 6 万，耗时 2 个月，这是我和媳妇装修的全过程"},{"images":["https://pic2.zhimg.com/v2-39b59e595ecdc61d30ed2d46e68be501.jpg"],"type":0,"id":9659644,"ga_prefix":"120414","title":"好担心啊，和他结婚的时候，房产证上没我的名字\u2026\u2026"},{"images":["https://pic1.zhimg.com/v2-505257944735e129abcdb391d4a3d628.jpg"],"type":0,"id":9659625,"ga_prefix":"120414","title":"许多人口中的「口水歌」到底是什么？"},{"images":["https://pic3.zhimg.com/v2-ec4385fcf62d2393c3d5552e4fa997aa.jpg"],"type":0,"id":9659544,"ga_prefix":"120412","title":"大误 · 同学，你听说过中华魔法学院吗？"},{"images":["https://pic4.zhimg.com/v2-719ebe8e2da71d79d009282498ae1133.jpg"],"type":0,"id":9659611,"ga_prefix":"120411","title":"我现在想当一名室内设计师，还来得及吗？"},{"images":["https://pic2.zhimg.com/v2-a40f95beca213406ff3724783366c869.jpg"],"type":0,"id":9659359,"ga_prefix":"120409","title":"法律法律，为什么「律师」不叫「法师」？"},{"images":["https://pic3.zhimg.com/v2-d330ae2a5afb2588e84e18d05c4adc96.jpg"],"type":0,"id":9659578,"ga_prefix":"120408","title":"怕孩子自卑，我一直没和他沟通他残疾的事，却没想到这是最糟的做法"},{"images":["https://pic2.zhimg.com/v2-08858081069f823a34ab73d4a1e55d01.jpg"],"type":0,"id":9659569,"ga_prefix":"120407","title":"- 生活中有哪些常见的悖论？\r\n- 什么都别想就能睡着"},{"images":["https://pic4.zhimg.com/v2-b2d28449e23ba5f32de44d33b8e0af3f.jpg"],"type":0,"id":9659559,"ga_prefix":"120407","title":"遇到「黄淑芬式」耍赖，我们该怎么办？"},{"images":["https://pic3.zhimg.com/v2-5f3d6e9fed5818346b0c3bffe3531dfe.jpg"],"type":0,"id":9659325,"ga_prefix":"120407","title":"你说的那种无法融入的人，就是我"},{"images":["https://pic1.zhimg.com/v2-c46ccf278f41ad0c2144d2808888e694.jpg"],"type":0,"id":9659266,"ga_prefix":"120406","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic1.zhimg.com/v2-6e4738906f26c3bb2264032032597114.jpg","type":0,"id":9659668,"ga_prefix":"120416","title":"人生中第一次「吃土」，咂咂嘴\u2026\u2026没想到还治好了闹肚子"},{"image":"https://pic1.zhimg.com/v2-5dc29782df4ce8f2e4f3d6adac377ea4.jpg","type":0,"id":9659617,"ga_prefix":"120415","title":"硬装 7 万，软装 6 万，耗时 2 个月，这是我和媳妇装修的全过程"},{"image":"https://pic1.zhimg.com/v2-d4894e7a1a20de687c55d1263637ca0c.jpg","type":0,"id":9659625,"ga_prefix":"120414","title":"许多人口中的「口水歌」到底是什么？"},{"image":"https://pic3.zhimg.com/v2-b084e14f02d4deea0aebe3f801c2209a.jpg","type":0,"id":9659559,"ga_prefix":"120407","title":"遇到「黄淑芬式」耍赖，我们该怎么办？"},{"image":"https://pic1.zhimg.com/v2-3f834011b91e92f7a715652191d8ebd0.jpg","type":0,"id":9659359,"ga_prefix":"120409","title":"法律法律，为什么「律师」不叫「法师」？"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * images : ["https://pic2.zhimg.com/v2-88f36e36780f9d7e90e8d6ee597b8c1d.jpg"]
         * type : 0
         * id : 9659668
         * ga_prefix : 120416
         * title : 人生中第一次「吃土」，咂咂嘴……没想到还治好了闹肚子
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : https://pic1.zhimg.com/v2-6e4738906f26c3bb2264032032597114.jpg
         * type : 0
         * id : 9659668
         * ga_prefix : 120416
         * title : 人生中第一次「吃土」，咂咂嘴……没想到还治好了闹肚子
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
