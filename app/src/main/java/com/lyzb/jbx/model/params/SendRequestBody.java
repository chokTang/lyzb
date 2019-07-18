package com.lyzb.jbx.model.params;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/12  17:41
 * Desc:
 */
public class SendRequestBody {
    private List<FileBody> fileList;
    private List<GoodsBody> goodsList;
    private ContentBody gsTopicInfo;

    public List<FileBody> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileBody> fileList) {
        this.fileList = fileList;
    }

    public ContentBody getGsTopicInfo() {
        return gsTopicInfo;
    }

    public void setGsTopicInfo(ContentBody gsTopicInfo) {
        this.gsTopicInfo = gsTopicInfo;
    }

    public List<GoodsBody> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsBody> goodsList) {
        this.goodsList = goodsList;
    }
}
