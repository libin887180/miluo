package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/14.
 */

public class InfomationNote {


    /**
     * articleid : string
     * articletitle : string
     * articleurl : string
     * id : 0
     * releasetime : string
     * thumbnail : string
     */

    private String articleid;
    private String articletitle;
    private String articleurl;
    private int id;
    private String releasetime;
    private String thumbnail;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }

    public String getArticleurl() {
        return articleurl;
    }

    public void setArticleurl(String articleurl) {
        this.articleurl = articleurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
