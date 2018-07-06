package com.fivefivelike.androidprojectdemo.entity;

import java.util.List;

/**
 * Created by liugongce on 2017/3/8.
 */

public class NewsEntity {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : string  新闻id
         * name : string 新闻标题
         * path : array 新闻图片
         * descr : 新闻简介
         * hits : string 点击次数
         * ctime : 创建时间
         * isvideo : 是否是视频
         * source : 来源
         * gpath : string 广告图片 没有就不输出有就要在后面跟随
         * links : string 广告链接地址
         */

        private String id;
        private String name;
        private List<String> path;
        private String descr;
        private String hits;
        private String ctime;
        private String isvideo;
        private String source;
        private String gpath;
        private String advertising;//1不是广告2是广告
        private String url;//广告url
        private String title;//广告标题
        private String links;//广告链接地址
        private String right;//1在右边   其他不是
        private String issatin;//1是段子
        private String types;//news 新闻  train 培训视频
        private String isimg;//1是美女频道 2不是
        private List<ImageBean>images;

        public String getIsimg() {
            return isimg;
        }

        public void setIsimg(String isimg) {
            this.isimg = isimg;
        }

        public List<ImageBean> getImages() {
            return images;
        }

        public void setImages(List<ImageBean> images) {
            this.images = images;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getIssatin() {
            return issatin;
        }

        public void setIssatin(String issatin) {
            this.issatin = issatin;
        }

        public String getAdvertising() {
            return advertising;
        }

        public void setAdvertising(String advertising) {
            this.advertising = advertising;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getPath() {
            return path;
        }

        public void setPath(List<String> path) {
            this.path = path;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getIsvideo() {
            return isvideo;
        }

        public void setIsvideo(String isvideo) {
            this.isvideo = isvideo;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getGpath() {
            return gpath;
        }

        public void setGpath(String gpath) {
            this.gpath = gpath;
        }

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }
    }
    public static class ImageBean{
        private String path;
        private int width;
        private int height;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

}
