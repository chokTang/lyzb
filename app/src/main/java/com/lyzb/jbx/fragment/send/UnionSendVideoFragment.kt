package com.lyzb.jbx.fragment.send

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseFragment
import com.like.utilslib.json.GSONUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.R
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment
import com.lyzb.jbx.fragment.send.ChoiceProductFragment.Companion.KEY_PRODUCT
import com.lyzb.jbx.fragment.send.HistoryChoiceProductFragment.Companion.KEY_VIDEO
import com.lyzb.jbx.fragment.send.UnionSendTWFragment.Companion.KEY_SELECT_PRODUCT
import com.lyzb.jbx.model.params.ContentBody
import com.lyzb.jbx.model.params.FileBody
import com.lyzb.jbx.model.params.GoodsBody
import com.lyzb.jbx.model.params.SendRequestBody
import com.lyzb.jbx.model.send.GoodsModel
import com.lyzb.jbx.model.send.SendVideoBean
import com.lyzb.jbx.model.send.TagModel
import com.lyzb.jbx.mvp.presenter.send.SendVideoPresenter
import com.lyzb.jbx.mvp.view.send.ISendVideoView
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Util.App
import kotlinx.android.synthetic.main.fragment_send_video.*
import kotlinx.android.synthetic.main.layout_send_title.*
import me.yokeyword.fragmentation.ISupportFragment
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Nullable


/**
 * Created by :TYK
 * Date: 2019/3/5  15:30
 * Desc:共商联盟发布动态视频页面
 */
class UnionSendVideoFragment : BaseFragment<SendVideoPresenter>(), ISendVideoView, View.OnClickListener {

    var isSend = false
    var str = ""
    var lastTimePosition = 0 //上一次点击的tagposition
    var selectProductList: MutableList<GoodsModel> = arrayListOf()

    companion object {
        const val VIDEO_KEY = "videokey"
        const val PARAM_CRICLEID = "param_cricleid"
        fun newIntance(localMedia: LocalMedia): UnionSendVideoFragment {
            return newIntanceByCircle("", localMedia)
        }

        fun newIntanceByCircle(circleId: String, localMedia: LocalMedia): UnionSendVideoFragment {
            val fragment = UnionSendVideoFragment()
            val args = Bundle()
            args.putParcelable(VIDEO_KEY, localMedia)
            args.putString(PARAM_CRICLEID, circleId)
            fragment.arguments = args
            return fragment
        }
    }

    var mBundle: Bundle? = null
    var localMedia: LocalMedia? = null
    var mCircleId = ""
    var taglist: MutableList<TagModel> = arrayListOf()
    var tagAdapter: com.lyzb.jbx.adapter.send.TagAdapter? = null
    var videoUrl = ""

    override fun getResId(): Any {
        return R.layout.fragment_send_video
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBundle = arguments
        localMedia = mBundle!!.getParcelable(VIDEO_KEY) as LocalMedia
        mCircleId = mBundle!!.getString(PARAM_CRICLEID)
        EventBus.getDefault().register(this)
    }


    override fun onInitView(savedInstanceState: Bundle?) {
        mPresenter.upLoadFile(localMedia!!)
        //显示视频图片
        val media = MediaMetadataRetriever()
        media.setDataSource(localMedia!!.path)// videoPath 本地视频的路径
        val bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)

        img_video.setImageBitmap(bitmap)//对应的ImageView赋值图片
        //显示视频时长
        val date = Date(localMedia!!.duration)
        val sdf = SimpleDateFormat("mm:ss")
        tv_video_time.text = sdf.format(date)

        tagAdapter = com.lyzb.jbx.adapter.send.TagAdapter()
        val gridLayoutManager = GridLayoutManager(activity, 4)
        rv_tag.layoutManager = gridLayoutManager
        rv_tag.adapter = tagAdapter


//        标签点击事件
        tagAdapter?.setOnItemClickListener { adapter, view, position ->
//            str = if (lastTimePosition == position) {
//                edit_video_send.text.toString() + (adapter.data[position] as TagModel).name
//            } else {
//                (adapter.data[position] as TagModel).name
//            }
//            lastTimePosition = position
            str= edit_video_send.text.toString() + (adapter.data[position] as TagModel).name
            edit_video_send.text = Editable.Factory.getInstance().newEditable(str)
            edit_video_send.setSelection(edit_video_send.text.length)
        }




        tv_send.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_video.setOnClickListener(this)
        ll_recommendedProduct.setOnClickListener(this)

    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getTagList()
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public fun getHistorySelectList(string: String){
        selectProductList = GSONUtil.getEntityList(string,GoodsModel::class.java)
        tv_select_num.text = "已选择${selectProductList.size}款商品"
    }

    /**
     * 获取tag回调列表
     */
    override fun getTagSucess(list: MutableList<TagModel>) {
        tagAdapter?.setNewData(list)
    }

    /**
     * 发送成功回调
     */
    override fun isSendSucess(sendBean: SendVideoBean) {
        showToast("发布成功")
        startWithPop(DynamicDetailFragment.newIntance(sendBean.gsTopicInfo.id))
    }

    /**
     * 上传视频到云服务器后回调
     */
    override fun OnUploadResult(url: String, localMedia: LocalMedia) {
        isSend = true
        videoUrl = url
        activity!!.runOnUiThread(Runnable {
            showToast("视频上传成功")
        })
    }


    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.img_back -> {//返回按钮
                pop()
            }
            R.id.img_video -> {//点击视频
                PictureSelector.create(this).externalPictureVideo(localMedia!!.path)
            }
            R.id.ll_recommendedProduct -> {//推荐商品
                hideSoftInput()
                startForResult(ChoiceProductFragment.newIntance(selectProductList,KEY_VIDEO), KEY_SELECT_PRODUCT)
            }

            R.id.tv_send -> {//发送视频
                if (App.getInstance().isLogin) {
                    if (isSend) {
                        val contentBody = ContentBody()
                        contentBody.content = edit_video_send.text.toString()
                        contentBody.groupId = mCircleId   //直接发布 不带ID
                        contentBody.class1 = "2"   //文件类型(1:图片 2:视频)

                        val sendRequestBody = SendRequestBody()
                        sendRequestBody.gsTopicInfo = contentBody

                        val fileList: MutableList<FileBody> = arrayListOf()

                        val fileBody = FileBody()
                        fileBody.sort = 0
                        fileBody.file = videoUrl
                        fileList.add(fileBody)
                        sendRequestBody.fileList = fileList

                        //构造商品数据
                        if (selectProductList.size>0){
                            val goodslist: MutableList<GoodsBody> = arrayListOf()
                            for (i in 0 until selectProductList.size){
                                val goodsBody = GoodsBody()
                                goodsBody.goodsId = selectProductList[i].goods_id
                                goodsBody.sort = i
                                goodslist.add(goodsBody)
                            }
                            sendRequestBody.goodsList = goodslist
                        }

                        checkIsNull(sendRequestBody)
                    } else {
                        showToast("视频还在上传,请稍后在发送")
                    }
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
        }
    }


    fun checkIsNull(body: SendRequestBody) {

        mPresenter.sendVideo(body)
    }


    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (data != null && resultCode == ISupportFragment.RESULT_OK) {
            when (requestCode) {
                KEY_SELECT_PRODUCT -> {//选择商品数据回调
                    selectProductList = GSONUtil.getEntityList(data.getString(KEY_PRODUCT), GoodsModel::class.java)
                    if (selectProductList.size > 0) {
                        tv_select_num.text = "已选择${selectProductList.size}款商品"
                    }
                }
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}