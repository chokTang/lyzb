package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.me.CardTemplateModel

/**
 * Created by :TYK
 * Date: 2019/5/6  14:30
 * Desc:
 */

interface ICardTemplateDetailView {

    fun getTemplateList(list: MutableList<CardTemplateModel>)

    fun saveTemplate(mode: CardTemplateModel)

}