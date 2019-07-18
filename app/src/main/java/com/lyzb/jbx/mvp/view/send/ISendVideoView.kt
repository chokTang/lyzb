package com.lyzb.jbx.mvp.view.send

import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.model.send.SendVideoBean
import com.lyzb.jbx.model.send.TagModel
import com.szy.yishopcustomer.Util.OssAuthenResponse

/**
 * Created by :TYK
 * Date: 2019/3/4  17:47
 * Desc:
 */

interface ISendVideoView{
    fun isSendSucess(sendBean: SendVideoBean)
    fun OnUploadResult(url: String,localMedia: LocalMedia)
    fun getTagSucess(list: MutableList<TagModel>)
}