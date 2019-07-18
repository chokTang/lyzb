package com.lyzb.jbx.model.me;

import java.util.ArrayList;
import com.lyzb.jbx.model.circle.OwnerVoBean;
import java.util.List;

/**
 * @author wyx
 * @role 我的圈子 model
 * @time 2019 2019/3/7 20:20
 */

public class CircleModel {


    /**
     * list : [{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":409,"memberId":"jbx110214","owner":true,"userId":"2524","userName":"管-团-业-个人零售名称"}],"allowinvites":false,"approval":false,"background":"d.img","banned":false,"description":"测试修改数据","groupCreateTime":"2019-03-06 15:00:04","groupType":"0","groupUpdateTime":"2019-03-06 15:00:04","groupname":"管-团-业-个人零售名称[已关注]","id":"70248423292930","ifOwner":true,"inviteNeedConfirm":false,"logo":"s.img","maxusers":100,"membersOnly":false,"menberNum":3,"orgId":"1","owner":"jbx110214","publicStr":false,"sort":1,"type":"1"},{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":421,"memberId":"jbx110381","owner":false,"userId":"110381","userName":"JBX150CRMI0899"}],"allowinvites":false,"approval":false,"background":"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAH0AfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDx","banned":false,"description":"群组描述","groupCreateTime":"2019-03-06 11:40:29","groupType":"0","groupUpdateTime":"2019-03-06 11:40:29","groupname":"管-团-业-个人零售名称[已购买]","id":"70248425390081","ifOwner":true,"inviteNeedConfirm":false,"logo":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","maxusers":2000,"membersOnly":false,"menberNum":1,"orgId":"1","owner":"jbx110214","publicStr":false,"sort":2,"type":"2"},{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":494,"memberId":"jbx95785","owner":false,"userId":"95785","userName":"song734141786"}],"allowinvites":false,"approval":false,"background":"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAH0AfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDx","banned":false,"description":"群组描述","groupCreateTime":"2019-03-06 11:40:29","groupType":"0","groupUpdateTime":"2019-03-06 11:40:29","groupname":"管-团-业-个人零售名称[已加购]","id":"70248426438657","ifOwner":true,"inviteNeedConfirm":false,"logo":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","maxusers":2000,"membersOnly":false,"menberNum":1,"orgId":"1","owner":"jbx110214","publicStr":false,"sort":3,"type":"3"},{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":409,"memberId":"jbx110214","owner":true,"userId":"2524","userName":"管-团-业-个人零售名称"}],"allowinvites":false,"approval":false,"background":"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAH0AfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDx","banned":false,"description":"群组描述","groupCreateTime":"2019-03-06 11:40:29","groupType":"0","groupUpdateTime":"2019-03-06 11:40:29","groupname":"管-团-业-个人零售名称[集采会员]","id":"70248428535809","ifOwner":true,"inviteNeedConfirm":false,"logo":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","maxusers":2000,"membersOnly":false,"menberNum":1,"orgId":"1","owner":"jbx110214","publicStr":false,"sort":4,"type":"4"},{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":421,"memberId":"jbx110381","owner":false,"userId":"110381","userName":"JBX150CRMI0899"}],"allowinvites":false,"approval":false,"background":"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAH0AfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDx","banned":false,"description":"群组描述","groupCreateTime":"2019-03-06 11:40:29","groupType":"0","groupUpdateTime":"2019-03-06 11:40:29","groupname":"H[已关注]","id":"70248949678082","ifOwner":false,"inviteNeedConfirm":false,"logo":"/shop/1566/images/2018/12/25/15457244559516.jpg","maxusers":2000,"membersOnly":false,"menberNum":2,"orgId":"1","owner":"jbx26943","publicStr":false,"sort":1,"type":"1"},{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":494,"memberId":"jbx95785","owner":false,"userId":"95785","userName":"song734141786"}],"allowinvites":true,"approval":false,"background":"d.img","banned":false,"description":"undefined","groupCreateTime":"undefined","groupType":"0","groupUpdateTime":"undefined","groupname":"测试圈子","id":"75986894913537","ifOwner":true,"inviteNeedConfirm":true,"logo":"s.img","maxusers":100,"membersOnly":false,"menberNum":1,"orgId":"1","owner":"jbx110214","publicStr":true,"sort":1,"type":"9999"},{"menberList":[{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":409,"memberId":"jbx110214","owner":true,"userId":"2524","userName":"管-团-业-个人零售名称"}],"allowinvites":true,"approval":false,"background":"d.img","banned":false,"description":"测试修改数据","groupCreateTime":"undefined","groupType":"0","groupUpdateTime":"undefined","groupname":"测试圈子","id":"75987029131266","ifOwner":true,"inviteNeedConfirm":true,"logo":"s.img","maxusers":100,"membersOnly":false,"menberNum":1,"orgId":"1","owner":"jbx110214","publicStr":true,"sort":1,"type":"9999"}]
     * pageNum : 1
     * pageSize : 30
     * pages : 1
     * total : 5
     */

    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;
    private List<ListBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * menberList : [{"admin":false,"banned":false,"black":false,"groupId":"70248423292930","headimg":"http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png","id":409,"memberId":"jbx110214","owner":true,"userId":"2524","userName":"管-团-业-个人零售名称"}]
         * allowinvites : false
         * approval : false
         * background : d.img
         * banned : false
         * description : 测试修改数据
         * groupCreateTime : 2019-03-06 15:00:04
         * groupType : 0
         * groupUpdateTime : 2019-03-06 15:00:04
         * groupname : 管-团-业-个人零售名称[已关注]
         * id : 70248423292930
         * ifOwner : true
         * inviteNeedConfirm : false
         * logo : s.img
         * maxusers : 100
         * membersOnly : false
         * menberNum : 3
         * orgId : 1
         * owner : jbx110214
         * publicStr : false
         * sort : 1
         * type : 1
         */

        private boolean allowinvites;       //是否允许其他人加入
        private boolean approval;           //是否需要批准
        private String background;          //圈子背景图片
        private boolean banned;             //是否全部禁言
        private String description;         //圈子描述
        private String groupCreateTime;     //创建时间
        private String groupType;           //圈子类型
        private String groupUpdateTime;
        private String groupname;           //圈子名称
        private String id;                  //圈子id
        private boolean ifOwner;            //是否为圈主
        private boolean inviteNeedConfirm;
        private String logo;                //圈子logo
        private int maxusers;
        private boolean membersOnly;
        private int menberNum;              //圈子成员数目
        private int dynamicNum;             //圈子动态数目
        private String orgId;
        private String owner;
        private boolean publicStr;
        private int sort;
        private String type;
        private OwnerVoBean ownerVo;

        private List<MenberListBean> menberList;

        public boolean isAllowinvites() {
            return allowinvites;
        }

        public void setAllowinvites(boolean allowinvites) {
            this.allowinvites = allowinvites;
        }

        public boolean isApproval() {
            return approval;
        }

        public void setApproval(boolean approval) {
            this.approval = approval;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public boolean isBanned() {
            return banned;
        }

        public void setBanned(boolean banned) {
            this.banned = banned;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroupCreateTime() {
            return groupCreateTime;
        }

        public void setGroupCreateTime(String groupCreateTime) {
            this.groupCreateTime = groupCreateTime;
        }

        public String getGroupType() {
            return groupType;
        }

        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }

        public String getGroupUpdateTime() {
            return groupUpdateTime;
        }

        public void setGroupUpdateTime(String groupUpdateTime) {
            this.groupUpdateTime = groupUpdateTime;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIfOwner() {
            return ifOwner;
        }

        public void setIfOwner(boolean ifOwner) {
            this.ifOwner = ifOwner;
        }

        public boolean isInviteNeedConfirm() {
            return inviteNeedConfirm;
        }

        public void setInviteNeedConfirm(boolean inviteNeedConfirm) {
            this.inviteNeedConfirm = inviteNeedConfirm;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getMaxusers() {
            return maxusers;
        }

        public void setMaxusers(int maxusers) {
            this.maxusers = maxusers;
        }

        public boolean isMembersOnly() {
            return membersOnly;
        }

        public void setMembersOnly(boolean membersOnly) {
            this.membersOnly = membersOnly;
        }

        public int getMenberNum() {
            return menberNum;
        }

        public void setMenberNum(int menberNum) {
            this.menberNum = menberNum;
        }

        public int getDynamicNum() {
            return dynamicNum;
        }

        public void setDynamicNum(int dynamicNum) {
            this.dynamicNum = dynamicNum;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public boolean isPublicStr() {
            return publicStr;
        }

        public void setPublicStr(boolean publicStr) {
            this.publicStr = publicStr;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public OwnerVoBean getOwnerVo() {
            return ownerVo;
        }

        public void setOwnerVo(OwnerVoBean ownerVo) {
            this.ownerVo = ownerVo;
        }

        public List<MenberListBean> getMenberList() {
            if (menberList == null)
                return new ArrayList<>();
            return menberList;
        }

        public void setMenberList(List<MenberListBean> menberList) {
            this.menberList = menberList;
        }


        public static class MenberListBean {
            /**
             * admin : false
             * banned : false
             * black : false
             * groupId : 70248423292930
             * headimg : http://lyzbimage.jbxgo.com/lyzbjbxgo/system/config/default_image/default_user_portrait_0.png
             * id : 409
             * memberId : jbx110214
             * owner : true
             * userId : 2524
             * userName : 管-团-业-个人零售名称
             */

            private boolean admin;
            private boolean banned;
            private boolean black;
            private String groupId;         //圈子id
            private String headimg;         //圈子成员头像
            private int id;
            private String memberId;
            private boolean owner;          //是否为圈主
            private String userId;
            private String userName;

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public boolean isBanned() {
                return banned;
            }

            public void setBanned(boolean banned) {
                this.banned = banned;
            }

            public boolean isBlack() {
                return black;
            }

            public void setBlack(boolean black) {
                this.black = black;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public boolean isOwner() {
                return owner;
            }

            public void setOwner(boolean owner) {
                this.owner = owner;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
