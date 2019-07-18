package com.lyzb.jbx.mvp.view.me;

import java.util.List;

/**
 *
 * 名片-个人
 */

public interface ICardImgTextView {

    void setImgList(final List<String> imgList);

    void deleImg();

    void toCardInfo(String data);
}
