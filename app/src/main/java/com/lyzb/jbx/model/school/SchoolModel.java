package com.lyzb.jbx.model.school;

public class SchoolModel {
    private String articleId;//文章ID
    private int articleReadnum;//阅读量
    private int articleThumb;//点赞量
    private String fileUrl;//图片地址
    private String title;//标题

    //与数据无关的
    private boolean isZan=false;//是否点赞

    public boolean isZan() {
        return isZan;
    }

    public void setZan(boolean zan) {
        isZan = zan;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getArticleReadnum() {
        return articleReadnum;
    }

    public void setArticleReadnum(int articleReadnum) {
        this.articleReadnum = articleReadnum;
    }

    public int getArticleThumb() {
        return articleThumb;
    }

    public void setArticleThumb(int articleThumb) {
        this.articleThumb = articleThumb;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
