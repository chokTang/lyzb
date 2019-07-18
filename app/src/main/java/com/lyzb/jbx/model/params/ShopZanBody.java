package com.lyzb.jbx.model.params;

public class ShopZanBody {
    private String articleId;
    private int optType;//=1:点赞; =2:取消点赞

    public ShopZanBody(String articleId, int optType) {
        this.articleId = articleId;
        this.optType = optType;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }
}
