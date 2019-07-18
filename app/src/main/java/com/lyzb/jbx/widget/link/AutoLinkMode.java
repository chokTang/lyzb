package com.lyzb.jbx.widget.link;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/8 20:13
 */

public enum AutoLinkMode {
    MODE_HASHTAG("Hashtag"),
    MODE_MENTION("Mention"),
    MODE_URL("Url"),
    MODE_PHONE("Phone"),
    MODE_EMAIL("Email"),
    MODE_CUSTOM("Custom");

    private String name;

    AutoLinkMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
