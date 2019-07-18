package com.lyzb.jbx.mvp.view.home.first

import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.follow.InterestMemberModel
import com.lyzb.jbx.model.me.CardModel

interface IHomeCardView {
    fun onIndustryResult(list: MutableList<BusinessModel>)
    fun onCardResult(isRefresh: Boolean, list: MutableList<InterestMemberModel>)
    fun onFinshOrLoadMore(isRefresh: Boolean)
    fun onCardData(model: CardModel)
    fun onFollowItemResult(position: Int)
}