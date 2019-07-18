package com.lyzb.jbx.model.me.company;

/**
 * @author wyx
 * @role
 * @time 2019 2019/6/19 8:56
 */

public class OrganInfoResponse {


    /**
     * msg : 查询成功
     * code : 200
     * data : {"industryName":"快速消费品","mapLng":"0.000000","companyName":"6月测试新增企业_1_机构1(公司)","mapLat":"0.0000","disTel":"123123123","parentOrgName":"6月测试新增企业_1","parentType":2,"orgType":"100","industryId":"5","mParentOrgId":"ef9e9cb75fa44c98a2437c48b4bacce6","chargePerson":"xxxx","logoFilePath":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/2019/04/01/5B587BC1-2498-4010-AAB2-3DA80FEE5446.jpeg","id":"84be0fadf0b94bedbab126862eb31349","disAddress":"重庆","isEditCharge":false}
     */

    private String msg;
    private String code;
    private OrganInfoModel data;

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

    public OrganInfoModel getData() {
        return data;
    }

    public void setData(OrganInfoModel data) {
        this.data = data;
    }

}
