package com.szy.yishopcustomer.ViewModel;

/**
 * Created by buqingqiang on 2016/6/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EvaluateCenterModel {
    private String mEvaluate;//评论类型
    private String mEvaluateImg;//评论类型图片
    private String mEvaluateDate;//评论日期
    private String mEvaluateGoodsImg;//评论商品图片
    private String mEvaluateGoodsName;//评论商品名称
    private String mEvaluateStarOneImg;//评论类型星星1图片
    private String mEvaluateStarTwoImg;//评论类型星星2图片
    private String mEvaluateStarThreeImg;//评论类型星星3图片
    private String mEvaluateStarFourImg;//评论类型星星4图片
    private String mEvaluateStarFiveImg;//评论类型星星5图片
    private String mEvaluateContent;//评论内容
    private String mEvaluateShareOrderOneImg;//晒单图片1
    private String mEvaluateShareOrderTwoImg;//晒单图片2
    private String mEvaluateShareOrderThreeImg;//晒单图片3

    public EvaluateCenterModel(String evaluate, String evaluateImg, String evaluateDate, String evaluateGoodsImg, String evaluateGoodsName, String evaluateContent, String evaluateShareOrderOneImg, String evaluateShareOrderTwoImg, String evaluateShareOrderThreeImg) {
        mEvaluate = evaluate;
        mEvaluateImg = evaluateImg;
        mEvaluateDate = evaluateDate;
        mEvaluateGoodsImg = evaluateGoodsImg;
        mEvaluateGoodsName = evaluateGoodsName;
        mEvaluateContent = evaluateContent;
        mEvaluateShareOrderOneImg = evaluateShareOrderOneImg;
        mEvaluateShareOrderTwoImg = evaluateShareOrderTwoImg;
        mEvaluateShareOrderThreeImg = evaluateShareOrderThreeImg;
    }

    public String getEvaluate() {
        return mEvaluate;
    }

    public void setEvaluate(String evaluate) {
        mEvaluate = evaluate;
    }

    public String getEvaluateContent() {
        return mEvaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        mEvaluateContent = evaluateContent;
    }

    public String getEvaluateDate() {
        return mEvaluateDate;
    }

    public void setEvaluateDate(String evaluateDate) {
        mEvaluateDate = evaluateDate;
    }

    public String getEvaluateGoodsImg() {
        return mEvaluateGoodsImg;
    }

    public void setEvaluateGoodsImg(String evaluateGoodsImg) {
        mEvaluateGoodsImg = evaluateGoodsImg;
    }

    public String getEvaluateGoodsName() {
        return mEvaluateGoodsName;
    }

    public void setEvaluateGoodsName(String evaluateGoodsName) {
        mEvaluateGoodsName = evaluateGoodsName;
    }

    public String getEvaluateImg() {
        return mEvaluateImg;
    }

    public void setEvaluateImg(String evaluateImg) {
        mEvaluateImg = evaluateImg;
    }

    public String getEvaluateShareOrderOneImg() {
        return mEvaluateShareOrderOneImg;
    }

    public void setEvaluateShareOrderOneImg(String evaluateShareOrderOneImg) {
        mEvaluateShareOrderOneImg = evaluateShareOrderOneImg;
    }

    public String getEvaluateShareOrderThreeImg() {
        return mEvaluateShareOrderThreeImg;
    }

    public void setEvaluateShareOrderThreeImg(String evaluateShareOrderThreeImg) {
        mEvaluateShareOrderThreeImg = evaluateShareOrderThreeImg;
    }

    public String getEvaluateShareOrderTwoImg() {
        return mEvaluateShareOrderTwoImg;
    }

    public void setEvaluateShareOrderTwoImg(String evaluateShareOrderTwoImg) {
        mEvaluateShareOrderTwoImg = evaluateShareOrderTwoImg;
    }

    public String getEvaluateStarFiveImg() {
        return mEvaluateStarFiveImg;
    }

    public void setEvaluateStarFiveImg(String evaluateStarFiveImg) {
        mEvaluateStarFiveImg = evaluateStarFiveImg;
    }

    public String getEvaluateStarFourImg() {
        return mEvaluateStarFourImg;
    }

    public void setEvaluateStarFourImg(String evaluateStarFourImg) {
        mEvaluateStarFourImg = evaluateStarFourImg;
    }

    public String getEvaluateStarOneImg() {
        return mEvaluateStarOneImg;
    }

    public void setEvaluateStarOneImg(String evaluateStarOneImg) {
        mEvaluateStarOneImg = evaluateStarOneImg;
    }

    public String getEvaluateStarThreeImg() {
        return mEvaluateStarThreeImg;
    }

    public void setEvaluateStarThreeImg(String evaluateStarThreeImg) {
        mEvaluateStarThreeImg = evaluateStarThreeImg;
    }

    public String getEvaluateStarTwoImg() {
        return mEvaluateStarTwoImg;
    }

    public void setEvaluateStarTwoImg(String evaluateStarTwoImg) {
        mEvaluateStarTwoImg = evaluateStarTwoImg;
    }

}
