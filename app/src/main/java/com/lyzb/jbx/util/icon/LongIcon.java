package com.lyzb.jbx.util.icon;

import com.joanzapata.iconify.Icon;

/**
 * 具体添加图片字体的地方
 * Created by Administrator on 2017/7/22.
 */

public enum LongIcon implements Icon {
    iconclose('\ue502'),
    iconpig('\ue601');

    private char character;

    LongIcon(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
