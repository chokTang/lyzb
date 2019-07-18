package com.lyzb.jbx.model.me.company;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/6/18 9:52
 */

public class MyCompanyModel {


    /**
     * msg : 查询成功
     * code : 200
     * data : [{"id":"05f2c8db61a349dcbe21c8cc2bb6a379","companyName":"爱唐笑笑真是好了","role":2,"orgType":1,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/06/25/20sV1g723J.jpg","industryName":"商务服务业","membersNum":8,"selectSt":false,"parentOrgId":"0","level":1,"children":[{"id":"93bb129ce4214e6aa69ef71e59e0ba0f","companyName":"添加个机构","role":2,"orgType":1,"membersNum":3,"selectSt":false,"parentOrgId":"05f2c8db61a349dcbe21c8cc2bb6a379","level":2}]},{"id":"35d78e67272143ae8d085a62d4198696","companyName":"测试创建企业","role":1,"orgType":0,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/06/27/D25CB094-B0D1-43A8-B0E9-4C396FD8A998.jpg","industryName":"商务服务业","membersNum":1,"selectSt":false,"parentOrgId":"0","level":1},{"id":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","companyName":"骆冲的2","role":1,"orgType":1,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/06/21/Q6oK73T02j.jpg","industryName":"商务服务业","membersNum":21,"selectSt":false,"parentOrgId":"0","level":1,"children":[{"id":"4d248ff79fa64a7e97d14f18954357a3","companyName":"这是部门","role":1,"orgType":2,"membersNum":1,"selectSt":false,"parentOrgId":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","level":2},{"id":"68a78301eb024477beec546f29f1336e","companyName":"二级机构","role":1,"orgType":1,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/06/21/48y8T8YPm8.jpg","industryName":"商务服务业","membersNum":1,"selectSt":false,"parentOrgId":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","level":2,"children":[{"id":"e85c2ea894d84266b89a2d857e098a22","companyName":"改成二级机构","role":1,"orgType":1,"membersNum":2,"selectSt":false,"parentOrgId":"68a78301eb024477beec546f29f1336e","level":3,"children":[{"id":"efab15edd5a0408cb5489b690f939953","companyName":"三级机构","role":1,"orgType":1,"membersNum":1,"selectSt":false,"parentOrgId":"e85c2ea894d84266b89a2d857e098a22","level":4}]}]},{"id":"A4F011D3-A750-4AB9-96C5-8FDA689D406E","companyName":"骆冲的2的店铺","role":1,"orgType":1,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/06/21/Da7QjE5406.jpg","membersNum":2,"selectSt":false,"parentOrgId":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","level":2},{"id":"b314a76673b54a26a40b6c1eeeaab911","companyName":"添加企业","role":1,"orgType":1,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/07/02/k2Wn777444.jpg","industryName":"商务服务业","membersNum":1,"selectSt":false,"parentOrgId":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","level":2},{"id":"daaa607b00f74c04a2c6a0a55ee0b9f2","companyName":"希望朋友也寂寞在金英敏肉末log无语一下子","role":1,"orgType":1,"membersNum":1,"selectSt":false,"parentOrgId":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","level":2},{"id":"f1f0bde22c084916b4dd1204c55609bc","companyName":"添加部门","role":1,"orgType":2,"membersNum":1,"selectSt":false,"parentOrgId":"4DBF0EBA-46C7-4C12-B241-9EAA280E336D","level":2}]},{"id":"97359c401a384f8b94f647ca6f0d52d0","companyName":"阿尔法1","role":1,"orgType":1,"logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/06/18/4B0EA3E5-595F-429A-893D-92D24AB01B1C.jpg","industryName":"商务服务业","membersNum":1,"selectSt":false,"parentOrgId":"0","level":1},{"id":"a60c2666a7f74d579be63701fd119b8b","companyName":"再创建个来解散","role":1,"orgType":1,"industryName":"房地产业/建筑业","membersNum":1,"selectSt":false,"parentOrgId":"0","level":1}]
     */

    private String msg;
    private String code;
    private boolean isShowCreate;
    private List<CompanyModel> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isShowCreate() {
        return isShowCreate;
    }

    public void setShowCreate(boolean showCreate) {
        isShowCreate = showCreate;
    }

    public List<CompanyModel> getData() {
        return data;
    }

    public void setData(List<CompanyModel> data) {
        this.data = data;
    }
}
