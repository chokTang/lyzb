package com.lyzb.jbx.mvp.presenter.home.first

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.json.JSONUtil
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.follow.InterestMemberModel
import com.lyzb.jbx.model.me.CardModel
import com.lyzb.jbx.model.me.DoLikeModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.home.first.IHomeCardView
import com.lyzb.jbx.util.AppPreference
import io.reactivex.Observable
import java.util.*
import kotlin.collections.set

class HomeCardPresenter : APPresenter<IHomeCardView>() {
    private var pageIndex = 1

    //获取行业列表
    fun getIndustryList() {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return accountApi.onGetListBusiness(getHeadersMap(GET, "/lbs/gs/user/selectGsProfessionList"))
            }

            override fun onSuccess(t: String?) {
                val businessModel = GSONUtil.getEntityList(t, BusinessModel::class.java)
                view.onIndustryResult(businessModel)
            }

            override fun onFail(message: String?) {
            }
        })
    }

    //获取名片列表
    fun getCardList(isRefresh: Boolean, industrId: String, phoneType: String) {
        if (isRefresh) {
            pageIndex = 1
        } else {
            pageIndex++
        }
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val params = HashMap<String, Any>()
                params["pageNum"] = pageIndex
                params["pageSize"] = 10
                params["hasMobile"] = phoneType
                params["professionId"] = industrId
                return APPresenter.followApi.getMeInterest(getHeadersMap(APPresenter.GET, "/lbs/gs/home/maybeInterest"), params)
            }

            override fun onSuccess(s: String) {
                val resultObject = JSONUtil.toJsonObject(s)
                val listArray = JSONUtil.getJsonArray(resultObject, "list")
                view.onCardResult(isRefresh, GSONUtil.getEntityList(listArray.toString(), InterestMemberModel::class.java))
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 获取名片信息
     */
    fun getCardData() {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return APPresenter.meApi.getCardData(getHeadersMap(APPresenter.GET, "/lbs/gs/user/getMyGaUserExtVoById"))
            }

            override fun onSuccess(s: String) {
                try {
                    val modelList = GSONUtil.getEntity(s, CardModel::class.java)
                    AppPreference.getIntance().userShowInfo = "1" == modelList!!.showInfo
                    AppPreference.getIntance().userIsVip = modelList.userVipAction.size > 0
                    view.onCardData(modelList)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 分享、点赞走的是一个接口。不改之前的逻辑，我只新增个判断。cx 3.9.1 isShare
     *
     * @param isLike
     * @param isShare
     * @param model
     */
    fun doLike(isLike: Boolean, isShare: Boolean, model: DoLikeModel) {
        onRequestData(false, object : IRequestListener<Any> {
            override fun onCreateObservable(): Observable<*> {
                return APPresenter.meApi.doLike(getHeadersMap(APPresenter.POST, "/lbs/gs/user/saveGsOperRecord"), model)
            }

            override fun onSuccess(o: Any) {
            }

            override fun onFail(message: String) {
            }
        })
    }

    /**
     * 关注某人 动态里面关注某人
     *
     * @param userId
     * @param enabled 1 关注 0 取消关注
     */
    fun onDynamciFollowUser(userId: String, enabled: Int, position: Int) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val params = HashMap<String, Any>()
                params["enabled"] = enabled
                params["toUserId"] = userId
                return APPresenter.meApi.onCardFollow(getHeadersMap(APPresenter.GET, "/lbs/gs/user/saveUsersRelation"), params)
            }

            override fun onSuccess(s: String) {
                view.onFollowItemResult(position)
                if (enabled == 1) {
                    showFragmentToast("关注成功")
                } else {
                    showFragmentToast("已取消关注")
                }
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }
}