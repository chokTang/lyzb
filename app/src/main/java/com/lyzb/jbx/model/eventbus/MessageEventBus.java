package com.lyzb.jbx.model.eventbus;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/2 17:53
 */

public class MessageEventBus {

    private int number;
    private List<EMMessage> list;

    public MessageEventBus(int number, List<EMMessage> list) {
        this.number = number;
        this.list = list;
    }

    public List<EMMessage> getList() {
        return list;
    }

    public void setList(List<EMMessage> list) {
        this.list = list;
    }

    public MessageEventBus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
