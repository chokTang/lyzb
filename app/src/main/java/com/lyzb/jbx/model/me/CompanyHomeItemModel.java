package com.lyzb.jbx.model.me;

import com.lyzb.jbx.model.cenum.CompanyItemType;

public class CompanyHomeItemModel {
    private CompanyItemType itemType;
    private int messageNumber;

    public CompanyHomeItemModel(CompanyItemType itemType, int messageNumber) {
        this.itemType = itemType;
        this.messageNumber = messageNumber;
    }

    public CompanyHomeItemModel(CompanyItemType itemType) {
        this.itemType = itemType;
    }

    public CompanyItemType getItemType() {
        return itemType;
    }

    public void setItemType(CompanyItemType itemType) {
        this.itemType = itemType;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
}
