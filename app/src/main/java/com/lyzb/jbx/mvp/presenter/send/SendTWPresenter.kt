package com.lyzb.jbx.mvp.presenter.send

import com.like.longshaolib.base.inter.IRequestListener
import com.like.longshaolib.net.widget.HttpResultDialog
import com.like.utilslib.image.LuBanUtil
import com.like.utilslib.image.inter.ICompressListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.fragment.send.UnionSendTWFragment.Companion.ADD_PIC
import com.lyzb.jbx.model.account.CircleBean
import com.lyzb.jbx.model.params.FileBody
import com.lyzb.jbx.model.params.SendRequestBody
import com.lyzb.jbx.model.send.SendTWBean
import com.lyzb.jbx.model.send.TagModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.send.ISendTWView
import com.szy.yishopcustomer.Constant.Key
import com.szy.yishopcustomer.Util.Oss
import com.szy.yishopcustomer.Util.SharedPreferencesUtils
import io.reactivex.Observable
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by :TYK
 * Date: 2019/3/4  17:47
 * Desc: 发布图文Presenter
 */

class SendTWPresenter : APPresenter<ISendTWView>() {

    /**
     *发送图文
     */
    fun sendTw(body: SendRequestBody) {
        onRequestData(true, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return sendApi.onSendTextOrVideo(getHeadersMap(POST, "/lbs/gs/topic/doPublish"), body)
            }

            override fun onSuccess(t: String?) {
                view.isSendSucess(GSONUtil.getEntity(t, SendTWBean::class.java))
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    /**
     * 上传文件  多文件上传
     */
    fun upLoadFiles(list: MutableList<FileBody>) {
        val dialog = HttpResultDialog(context)
        dialog.show()
        val urllist: MutableList<FileBody> = arrayListOf()
        val uplist: MutableList<FileBody> = arrayListOf()
        for (i in list.size-1 downTo 0  ) {
            if (list[i].file == ADD_PIC) {//去除第一个加号图标
                list.removeAt(i)
            }
        }

        for (i in 0 until list.size) {//这里是排除已经上传了的文件  上传了的文件 file里面含有OSS
            if (!list[i].file.contains("lyzbjbx.oss")) {
                uplist.add(list[i])
            }
        }
        for (i in 0 until uplist.size) {//这里是遍历list中的数据 根据sort字段顺序来排序 sort为0 则对应的file为第0个
            val file = File(uplist[i].file)
            LuBanUtil.compress(file, object : ICompressListener {
                override fun onFail(msg: String?) {

                }

                override fun onSuccess(file: File?) {
                    Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.value, "") as String,
                            file!!.absolutePath, object : Oss.OssListener {
                        override fun onProgress(progress: Int) {

                        }

                        override fun onSuccess(url: String) {
                            val fileBody = FileBody()
                            fileBody.file = url
                            fileBody.sort = i
                            urllist.add(fileBody)
                            if (urllist.size == uplist.size) {
                                dialog.dismiss()
                                view.OnUploadResult(urllist)
                            }
                        }

                        override fun onFailure() {
                            if (isViewAttached) {
                                dialog.dismiss()
                            }
                        }
                    })
                }
            })
        }
    }


    /**
     * 获取标签数据
     */
    fun getTagList() {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return accountApi.getTagList(getHeadersMap(GET, "/lbs/gs/topic/getRecommendTopics"))
            }

            override fun onSuccess(t: String?) {
                val json = JSONObject(t)
                val list = GSONUtil.getEntityList(json.getString("data"), TagModel::class.java)
                view.getTagSucess(list)
            }

            override fun onFail(message: String?) {
            }

        })
    }


}