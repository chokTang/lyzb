package com.lyzb.jbx.mvp.view.me;

import java.util.List;

/**
 *
 * 名片-企业
 */

public interface ICardComdImgView {

    void setImgList(final List<String> imgList);

    void deleImg();

    void toCardInfo(String data);

}
