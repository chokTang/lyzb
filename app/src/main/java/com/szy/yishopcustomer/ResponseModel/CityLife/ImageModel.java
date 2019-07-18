package com.szy.yishopcustomer.ResponseModel.CityLife;

/**
 * @author wyx
 * @role 同城生活H5 图片上传 数据 model
 * @time 2018 13:59
 */

public class ImageModel {

    /**
     * IsSuccess : true
     * Info : 成功
     * ResultObj : {"Paths":"http://image.lyzb.cn/Order/2018/04/24/20180424135436_0300.jpg"}
     * ResponseState : {"StateCode":200,"Info":"请求成功"}
     */

    private boolean IsSuccess;
    private String Info;
    private ResultObjBean ResultObj;
    private ResponseStateBean ResponseState;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public ResultObjBean getResultObj() {
        return ResultObj;
    }

    public void setResultObj(ResultObjBean ResultObj) {
        this.ResultObj = ResultObj;
    }

    public ResponseStateBean getResponseState() {
        return ResponseState;
    }

    public void setResponseState(ResponseStateBean ResponseState) {
        this.ResponseState = ResponseState;
    }

    public class ResultObjBean {
        /**
         * Paths : http://image.lyzb.cn/Order/2018/04/24/20180424135436_0300.jpg
         */

        private String Paths;

        public String getPaths() {
            return Paths;
        }

        public void setPaths(String Paths) {
            this.Paths = Paths;
        }
    }

    public class ResponseStateBean {
        /**
         * StateCode : 200
         * Info : 请求成功
         */

        private int StateCode;
        private String Info;

        public int getStateCode() {
            return StateCode;
        }

        public void setStateCode(int StateCode) {
            this.StateCode = StateCode;
        }

        public String getInfo() {
            return Info;
        }

        public void setInfo(String Info) {
            this.Info = Info;
        }
    }
}
