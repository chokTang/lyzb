package com.lyzb.jbx.mvp.presenter.send

import com.like.longshaolib.base.inter.IRequestListener
import com.like.longshaolib.dialog.original.HttpProessDialog
import com.like.utilslib.file.FileUtil
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.model.params.SendRequestBody
import com.lyzb.jbx.model.send.SendVideoBean
import com.lyzb.jbx.model.send.TagModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.send.ISendVideoView
import com.szy.yishopcustomer.Constant.Key
import com.szy.yishopcustomer.Util.Oss
import com.szy.yishopcustomer.Util.SharedPreferencesUtils
import com.vincent.videocompressor.VideoCompress
import io.reactivex.Observable
import org.json.JSONObject
import java.io.File


/**
 * Created by :TYK
 * Date: 2019/3/4  17:47
 * Desc: 发布视频Presenter
 */

class SendVideoPresenter : APPresenter<ISendVideoView>() {


    /**
     *发送视频
     */
    fun sendVideo(body: SendRequestBody) {
        onRequestData(true, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return sendApi.onSendTextOrVideo(getHeadersMap(POST, "/lbs/gs/topic/doPublish"), body)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("发送视频回调信息$t")
                view.isSendSucess(GSONUtil.getEntity(t, SendVideoBean::class.java))
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 上传文件
     */
    fun upLoadFile(localMedia: LocalMedia) {
        val dialog = HttpProessDialog(context, "压缩中...")
        val file = File(localMedia.path)
        if (file.length() > 100 * 1024 * 1024) {//如果文件大于100M，才启用压缩
            var outFile = FileUtil.createFile(FileUtil.getVideoFileUrl(), System.currentTimeMillis().toString() + "." + FileUtil.getExtension(file.absolutePath))
            VideoCompress.compressVideoLow(file.absolutePath, outFile.absolutePath, object : VideoCompress.CompressListener {
                override fun onStart() {
                    dialog.show()
                }

                override fun onSuccess() {
                    dialog.setText("0")
                    dialog.setText("上传中...")
                    Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.value, "") as String,
                            outFile.absolutePath, object : Oss.OssListener {
                        override fun onProgress(progress: Int) {
                            dialog.setProress("$progress")
                        }

                        override fun onSuccess(url: String) {
                            dialog.dismiss()
                            view.OnUploadResult(url, localMedia)
                            if (outFile.exists()) {
                                outFile.delete()
                                outFile = null
                            }
                        }

                        override fun onFailure() {
                            showFragmentToast("上传失败")
                            dialog.dismiss()
                        }
                    })
                }

                override fun onFail() {
                    showFragmentToast("压缩失败")
                }

                override fun onProgress(percent: Float) {
                    dialog.setProress(String.format("%.2f", percent))
                }
            })
        } else {
            dialog.show()
            dialog.setText("0")
            dialog.setText("上传中...")
            Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.value, "") as String,
                    file.absolutePath, object : Oss.OssListener {
                override fun onProgress(progress: Int) {
                    dialog.setProress("$progress")
                }

                override fun onSuccess(url: String) {
                    dialog.dismiss()
                    view.OnUploadResult(url, localMedia)
                }

                override fun onFailure() {
                    showFragmentToast("上传失败")
                    dialog.dismiss()
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