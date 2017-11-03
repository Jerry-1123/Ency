package com.xxx.ency.model.bean;

/**
 * Bing随机壁纸
 * Created by xiarh on 2017/11/3.
 */

public class BingBean {

    /**
     * data : {"enddate":"20161112","url":"http://s.cn.bing.net/az/hprichbg/rb/IgelHerbst_ZH-CN7813320285_1920x1080.jpg","bmiddle_pic":"http://ww2.sinaimg.cn/bmiddle/006qRazejw1f9oxblh7l0j30sg0g0adq.jpg","original_pic":"http://ww2.sinaimg.cn/large/006qRazejw1f9oxblh7l0j30sg0g0adq.jpg","thumbnail_pic":"http://ww2.sinaimg.cn/thumbnail/006qRazejw1f9oxblh7l0j30sg0g0adq.jpg"}
     * status : {"code":200,"message":""}
     */

    private DataBean data;
    private StatusBean status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * enddate : 20161112
         * url : http://s.cn.bing.net/az/hprichbg/rb/IgelHerbst_ZH-CN7813320285_1920x1080.jpg
         * bmiddle_pic : http://ww2.sinaimg.cn/bmiddle/006qRazejw1f9oxblh7l0j30sg0g0adq.jpg
         * original_pic : http://ww2.sinaimg.cn/large/006qRazejw1f9oxblh7l0j30sg0g0adq.jpg
         * thumbnail_pic : http://ww2.sinaimg.cn/thumbnail/006qRazejw1f9oxblh7l0j30sg0g0adq.jpg
         */

        private String enddate;
        private String url;
        private String bmiddle_pic;
        private String original_pic;
        private String thumbnail_pic;

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBmiddle_pic() {
            return bmiddle_pic;
        }

        public void setBmiddle_pic(String bmiddle_pic) {
            this.bmiddle_pic = bmiddle_pic;
        }

        public String getOriginal_pic() {
            return original_pic;
        }

        public void setOriginal_pic(String original_pic) {
            this.original_pic = original_pic;
        }

        public String getThumbnail_pic() {
            return thumbnail_pic;
        }

        public void setThumbnail_pic(String thumbnail_pic) {
            this.thumbnail_pic = thumbnail_pic;
        }
    }

    public static class StatusBean {
        /**
         * code : 200
         * message :
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
