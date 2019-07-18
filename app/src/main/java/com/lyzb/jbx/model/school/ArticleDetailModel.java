package com.lyzb.jbx.model.school;

public class ArticleDetailModel {
    private String addTime;
    private String articleId;
    private int articleReadnum;
    private int articleThumb;
    private String author;
    private String content;
    private String title;
    private boolean isZan;//是否点赞

    public boolean isZan() {
        return isZan;
    }

    public void setZan(boolean zan) {
        isZan = zan;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
