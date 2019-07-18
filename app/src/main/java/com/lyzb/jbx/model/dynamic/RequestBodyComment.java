package com.lyzb.jbx.model.dynamic;

import java.io.Serializable;
import java.util.List;

// FIXME generate failure  field _$FileList3137

/**
 * Created by :TYK
 * Date: 2019/3/21  15:32
 * Desc:
 */
public class RequestBodyComment implements Serializable {
    /**
     * fileList|3 : [{}]
     * gsTopicComment : {"content":"plcontent","createDate":"2019-03-06 17:14:20","id":"a7b536ad81c740f8b0e15faa55fa1b6d","topicId":"00760ea444284e6fbfe57a109242354b","userId":32801}
     */

    private List<FileList> fileList;
    private GsTopicCommentBean gsTopicComment;

    public List<FileList> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileList> fileList) {
        this.fileList = fileList;
    }

    public GsTopicCommentBean getGsTopicComment() {
        return gsTopicComment;
    }

    public void setGsTopicComment(GsTopicCommentBean gsTopicComment) {
        this.gsTopicComment = gsTopicComment;
    }

    public static class GsTopicCommentBean  implements Serializable {


        private String content;//评论内容
        private String topicId;//动态文章id
        private String replyId;//回复评论id
        private int type;//类型 默认0 回复时为 1

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class FileList  implements Serializable {
        public String file;
    }

}
