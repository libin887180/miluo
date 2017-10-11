package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/10/9.
 */

public class FundNotice {

//    attachment (string, optional): 附件地址
//    content (string, optional): 文章正文
//    id (string, optional): 基金公告表主键id
//    noticeType (string, optional): 公告类型
//    pubDate (string, optional): 发布日期
//    sellFundId (string, optional): 基金表主键id
//    source (string, optional): 来源
//    title (string, optional): 文章标题

    private String attachment;
    private String content;
    private String id;
    private String noticeType;
    private String pubDate;
    private String sellFundId;
    private String source;
    private String title;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSellFundId() {
        return sellFundId;
    }

    public void setSellFundId(String sellFundId) {
        this.sellFundId = sellFundId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
