package com.lyzb.jbx.mvp.view.send

import com.lyzb.jbx.model.account.CircleBean
import com.lyzb.jbx.model.params.FileBody
import com.lyzb.jbx.model.send.SendTWBean
import com.lyzb.jbx.model.send.TagModel
import com.szy.yishopcustomer.Util.OssAuthenResponse

/**
 * Created by :TYK
 * Date: 2019/3/4  17:47
 * Desc:
 */

interface ISendTWView{
    fun OnUploadResult(list: MutableList<FileBody>)
    fun isSendSucess(sendBean: SendTWBean)
    fun getTagSucess(list: MutableList<TagModel>)
}