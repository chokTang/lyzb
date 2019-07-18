package com.lyzb.jbx.model.params;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/14 11:33
 */

public class CustomerAddTrackBody {

    private String customerId;
    private String content;

    public CustomerAddTrackBody() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
