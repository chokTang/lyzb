package com.lyzb.jbx.fragment.send

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.Gson
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.like.utilslib.screen.DensityUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.send.PicAdapter
import com.lyzb.jbx.fragment.account.PerfectOneFragment.Companion.PERMISSION_CODE
import com.lyzb.jbx.fragment.account.PerfectOneFragment.Companion.permissions
import com.lyzb.jbx.fragment.base.BasePhotoFragment
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment.Companion.KEY_REQUESCODE
import com.lyzb.jbx.fragment.me.card.AddGoodFragment.KEY_RESULT_PAMAS
import com.lyzb.jbx.fragment.me.card.AddGoodFragment.RESULT_CODE
import com.lyzb.jbx.fragment.send.ChoiceProductFragment.Companion.KEY_PRODUCT
import com.lyzb.jbx.fragment.send.HistoryChoiceProductFragment.Companion.KEY_TW
import com.lyzb.jbx.inter.IRecycleHolderAnyClilck
import com.lyzb.jbx.model.dynamic.RequestBodyComment
import com.lyzb.jbx.model.me.GoodsDesModel
import com.lyzb.jbx.model.params.ContentBody
import com.lyzb.jbx.model.params.FileBody
import com.lyzb.jbx.model.params.GoodsBody
import com.lyzb.jbx.model.params.SendRequestBody
import com.lyzb.jbx.model.send.GoodsModel
import com.lyzb.jbx.model.send.SendTWBean
import com.lyzb.jbx.model.send.TagModel
import com.lyzb.jbx.mvp.presenter.send.SendTWPresenter
import com.lyzb.jbx.mvp.view.send.ISendTWView
import com.lyzb.jbx.util.AppPreference
import com.lyzb.jbx.util.ListChangeIndexUtil
import com.lyzb.jbx.widget.GuideView
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.DividerGridItemDecoration
import com.szy.yishopcustomer.Util.LubanImg.getPath
import com.szy.yishopcustomer.Util.PermissionUtils
import kotlinx.android.synthetic.main.fragment_send_tw.*
import kotlinx.android.synthetic.main.layout_send_title.*
import me.yokeyword.fragmentation.ISupportFragment
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by :TYK
 * Date: 2019/3/1  10:00
 * Desc:共商联盟发布动态图文页面
 */

class UnionSendTWFragment : BasePhotoFragment<SendTWPresenter>(), ISendTWView, View.OnClickListener {

    companion object {
        //小于9张显示第一张 添加图标(我这里addpic 是一个标识) 遇到当前标识则显示为添加图标
        const val ADD_PIC = "addpic"
        const val PIC_KEY = "pickey"
        const val SEND_COMMENT = "sendComment"// 发布评论
        const val SEND_CIRCLE = "sendCircle" //发布圈子
        const val SEND_GOODS_PIC = "sendGoodsPic" //发布商品图片
        const val SEND_GOODS_MODEL = "sendGoc" //发布商品内容
        const val KEY_CIRCLE_ID = "keyCircleId" //圈子KEY
        const val TYPE = "type"
        const val KEY_RESULY = "keyresult"
        const val KEY_SELECT_PRODUCT = 65841 //选择商品回调

        fun newIntance(localMedia: LocalMedia): UnionSendTWFragment {
            return newIntance("", localMedia)
        }

        fun newIntance(circleId: String, localMedia: LocalMedia): UnionSendTWFragment {
            val fragment = UnionSendTWFragment()
            val args = Bundle()
            args.putParcelable(PIC_KEY, localMedia)
            args.putString(KEY_CIRCLE_ID, circleId)
            fragment.arguments = args
            return fragment
        }

        /**
         * TYPE  SEND_COMMENT  发布评论,SEND_CIRCLE 发布圈子，SEND_GOODS_PIC  发布商品图片
         */
        fun newIntance(type: String, circleId: String): UnionSendTWFragment {
            val fragment = UnionSendTWFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            args.putString(KEY_CIRCLE_ID, circleId)
            fragment.arguments = args
            return fragment
        }

        /**
         * TYPE  SEND_COMMENT  发布评论,SEND_CIRCLE 发布圈子，SEND_GOODS_PIC  发布商品图片
         */
        fun newIntance(type: String, model: GoodsDesModel): UnionSendTWFragment {
            val fragment = UnionSendTWFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            args.putSerializable(SEND_GOODS_MODEL, model)
            fragment.arguments = args
            return fragment
        }
    }

    var goodsModel:GoodsDesModel? = null
    var selectProductList: MutableList<GoodsModel> = arrayListOf()
    var str = ""
    var lastTimePosition = 0 //上一次点击的tagposition
    var mBundle: Bundle? = null
    var maxNum = 9  //最大9张图片
    var picList: MutableList<FileBody> = arrayListOf() //切记piclist=9  则全是图片 不然得除去第一张添加图标
    var list: MutableList<LocalMedia> = arrayListOf() //切记piclist=9  则全是图片 不然得除去第一张添加图标
    var picAdapter: PicAdapter? = null
    var tagAdapter: com.lyzb.jbx.adapter.send.TagAdapter? = null
    var localMedia: LocalMedia? = null
    var type = SEND_CIRCLE
    var circleId = ""
    var helper: ItemTouchHelper? = null

    override fun getResId(): Any {
        return R.layout.fragment_send_tw
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (null != arguments!!.getParcelable(PIC_KEY)) {
            mBundle = arguments
            localMedia = mBundle!!.getParcelable(PIC_KEY) as LocalMedia
            circleId = mBundle!!.getString(KEY_CIRCLE_ID)
        }
        if (null != arguments!!.getString(TYPE)) {
            mBundle = arguments
            type = mBundle!!.getString(TYPE)
            if (type == SEND_CIRCLE) {
                circleId = mBundle!!.getString(KEY_CIRCLE_ID)
            }
        }
        if (type=="sendGoodsPic"){
            goodsModel = mBundle!!.getSerializable(SEND_GOODS_MODEL) as GoodsDesModel?
        }

        EventBus.getDefault().register(this)
    }

    override fun onInitView(savedInstanceState: Bundle?) {

        picAdapter = PicAdapter()
        val gridLayoutManager = GridLayoutManager(activity, 3)
        rv_pic.layoutManager = gridLayoutManager
        rv_pic.adapter = picAdapter
        rv_pic.addItemDecoration(DividerGridItemDecoration(activity, R.drawable.listdivider_white_10))


        when(type){
            SEND_COMMENT->{//发布评论时候hint要变  ,评论时候不要标签
                edit_send_tw.hint = "请输入评论内容,评论时请文明用语"
                tv_center_title.text = "发布评论"
                rv_tag.visibility = View.GONE
                ll_recommendedProduct.visibility = View.GONE
            }
            SEND_GOODS_PIC->{//发布商品图片
                rv_tag.visibility = View.GONE
                ll_recommendedProduct.visibility = View.GONE
                if (goodsModel==null){
                    edit_send_tw.hint = "请输入描述内容"
                    tv_center_title.text = "发布商品描述"
                }else{
                    edit_send_tw.text = Editable.Factory.getInstance().newEditable(goodsModel!!.decContent)
                    tv_center_title.text = "发布商品描述"
                    picList= GSONUtil.getEntityList(goodsModel!!.getDecPic(), FileBody::class.java)
                    val fileBody = FileBody()
                    fileBody.file = ADD_PIC
                    fileBody.sort = picList.size
                    picList.add( fileBody)
                    picAdapter!!.setNewData(picList)
                }
            }
            else->{
                tv_center_title.text = "发布图文"
                rv_tag.visibility = View.VISIBLE
                ll_recommendedProduct.visibility = View.VISIBLE
            }
        }



        tagAdapter = com.lyzb.jbx.adapter.send.TagAdapter()
        val gridLayoutManager1 = GridLayoutManager(activity, 4)
        rv_tag.layoutManager = gridLayoutManager1
        rv_tag.adapter = tagAdapter

        edit_send_tw.requestFocus()
        edit_send_tw.isFocusable = true
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(edit_send_tw, InputMethodManager.SHOW_FORCED)

        if (null != arguments!!.getParcelable(PIC_KEY)) {//有数据传递过来  照相
            val fileBody = FileBody()
            fileBody.file = localMedia!!.path
            fileBody.sort = 0
            picList.add(fileBody)
            mPresenter.upLoadFiles(picList)
        } else {//无数据传递过来  图文
            if (goodsModel==null){   //发送商品的时候  没商品图片的时候  要显示加号
                val fileBody = FileBody()
                fileBody.file = ADD_PIC
                fileBody.sort = 0
                picList.add(0, fileBody)
                picAdapter!!.setNewData(picList)
            }
        }

        //设置点击事件  点击item
        picAdapter!!.setOnItemClickListener { adapter, view, position ->

            if (PermissionUtils.hasPermission(context, *permissions)) {
                choosePicture()
            } else {
                PermissionUtils.requestPermission(activity!!.parent, PERMISSION_CODE, *permissions)
            }

        }
        /**
         * 删除图片点击事件
         */
        picAdapter!!.setOnItemChildClickListener { adapter, view, position ->

            if (picList[picList.size - 1].file == ADD_PIC) {//有第一个添加图片
                picList.removeAt(position)
            } else {//全是图片  移除一张图片后要吧添加图片弄上去
                picList.removeAt(position)
                val fileBody = FileBody()
                fileBody.file = ADD_PIC
                fileBody.sort = picList.size - 1
                picList.add(picList.size - 1, fileBody)
            }
            for (i in 0 until picList.size) {
                if (picList[i].file == ADD_PIC) {
                    Collections.swap(picList, i, picList.size - 1)
                }
            }
            picAdapter!!.setNewData(picList)
        }

//        标签点击事件
        tagAdapter?.setOnItemClickListener { adapter, view, position ->
            //            str = if (lastTimePosition == position) {
//                edit_send_tw.text.toString() +(adapter.data[position] as TagModel).name
//            } else {
//                (adapter.data[position] as TagModel).name
//            }
//            lastTimePosition = position

            hideSoftInput()
            str = edit_send_tw.text.toString() + (adapter.data[position] as TagModel).name

            edit_send_tw.text = Editable.Factory.getInstance().newEditable(str)
            edit_send_tw.setSelection(edit_send_tw.text.length)
        }


        helper = ItemTouchHelper(callback)
        helper?.attachToRecyclerView(rv_pic)
        //长按时间
        picAdapter?.invoke(object : IRecycleHolderAnyClilck {
            override fun onItemLongClick(holder: RecyclerView.ViewHolder, position: Int, obj: Any): Boolean {
                helper?.startDrag(holder)
                return true
            }
        })


        tv_send.setOnClickListener(this)
        img_back.setOnClickListener(this)
        ll_recommendedProduct.setOnClickListener(this)
    }

    /**
     * 长按事件回调
     */
    var callback: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragflag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return ItemTouchHelper.Callback.makeMovementFlags(dragflag, 0)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//            Collections.swap(picAdapter?.data, viewHolder.adapterPosition, target.adapterPosition)
            if (picAdapter!!.data.size - 1 != target.adapterPosition) {
                ListChangeIndexUtil.swap(picAdapter?.data, viewHolder.adapterPosition, target.adapterPosition)
                picAdapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            }

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

        override fun canDropOver(recyclerView: RecyclerView?, current: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            return true
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                val layoutParams = RelativeLayout.LayoutParams(DensityUtil.dpTopx(95f), DensityUtil.dpTopx(95f))
                viewHolder!!.itemView.findViewById<ImageView>(R.id.pic).layoutParams = layoutParams
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            val layoutParams = RelativeLayout.LayoutParams(DensityUtil.dpTopx(85f), DensityUtil.dpTopx(85f))
            viewHolder.itemView.findViewById<ImageView>(R.id.pic).layoutParams = layoutParams

        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }
    }


    override fun onInitData(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(circleId)) {//主页发布  显示TAG  圈子发布动态 不显示TAG
            mPresenter.getTagList()
        }
    }

    /**
     * 发送成功回调
     */
    override fun isSendSucess(sendBean: SendTWBean) {
        showToast("发布成功")
        hideSoftInput()
        if (type == SEND_CIRCLE) {//主页发布圈子 成功后进入动态详情
            startWithPop(DynamicDetailFragment.newIntance(sendBean.gsTopicInfo.id))
        } else {
            pop()
        }
    }

    /**
     * 上传图片成功回调
     */
    override fun OnUploadResult(list: MutableList<FileBody>) {
//        picList.clear()

        //要删除的数据的下标
        val deleteTag: MutableList<Int> = arrayListOf()

        activity!!.runOnUiThread {
            picList.addAll(list)

            for (i in 0 until picList.size) {//这里只添加上传成功了的文件  上传了的文件 file里面含有OSS
                if (picList[i].file.contains("storage")) {
                    deleteTag.add(i)
                }
            }
            //删除对应下标的数据(切记删除list中多个数据时候要倒序,不然你删除前面的下标影响后面的链式结构)
            for (i in deleteTag.size - 1 downTo 0) {
                picList.removeAt(deleteTag[i])
            }

            if (picList.size in 1..8) {//piclist张数在1-8张
                if (picList[0].file != ADD_PIC) {
                    //小于9张显示第一张 添加图标(我这里addpic 是一个标识) 遇到当前标识则显示为添加图标
                    val fileBody = FileBody()
                    fileBody.file = ADD_PIC
                    fileBody.sort = 0
                    picList.add(0, fileBody)
                }
            }
            for (i in 0 until picList.size) {
                if (picList[i].file == ADD_PIC) {
                    Collections.swap(picList, i, picList.size - 1)
                }
            }
            //上传成功后  适配数据
            picAdapter!!.setNewData(picList)

            if (list.size >= 2) {
                val num = AppPreference.getIntance().keyHintOne
                LogUtil.loge("当前提示拖动次数为$num")
                AppPreference.getIntance().keyHintOne = num + 1
                LogUtil.loge("当前提示拖动次数为------>>>>>" + AppPreference.getIntance().keyHintOne)
                if (num <= 0) {
                    setGuideView()
                }
            }
        }

    }

    /**
     * 获取tag回调列表
     */
    override fun getTagSucess(list: MutableList<TagModel>) {
        tagAdapter?.setNewData(list)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {//返回按钮
                pop()
            }

            R.id.ll_recommendedProduct -> {//推荐商品
                hideSoftInput()
                startForResult(ChoiceProductFragment.newIntance(selectProductList, KEY_TW), KEY_SELECT_PRODUCT)
            }

            R.id.tv_send -> {//发送  切记piclist[0]!=ADD_PIC  则全是图片 不然得除去第一张添加图标
                val contentBody = ContentBody()
                contentBody.content = edit_send_tw.text.toString()
                contentBody.groupId = circleId  //直接发布 不带ID
                contentBody.class1 = "1"   //文件类型(1:图片 2:视频)

                val sendRequestBody = SendRequestBody()
                sendRequestBody.gsTopicInfo = contentBody

                //构造图片数据
                val fileList: MutableList<FileBody> = arrayListOf()
                if (picAdapter!!.data.size > 0) {//
                    if (picAdapter!!.data[picList.size - 1].file == ADD_PIC) {//最后一张是加号图标  除去加号图标
                        for (i in 0 until picAdapter!!.data.size - 1) {
                            val fileBody = FileBody()
                            fileBody.sort = i
                            fileBody.file = picAdapter!!.data[i].file
                            fileList.add(fileBody)
                        }
                    } else {//全是图片
                        for (i in 0 until picAdapter!!.data.size) {
                            val fileBody = FileBody()
                            fileBody.sort = i
                            fileBody.file = picAdapter!!.data[i].file
                            fileList.add(fileBody)
                        }
                    }
                }
                sendRequestBody.fileList = fileList

                //构造商品数据
                if (selectProductList.size > 0) {
                    val goodslist: MutableList<GoodsBody> = arrayListOf()
                    for (i in 0 until selectProductList.size) {
                        val goodsBody = GoodsBody()
                        goodsBody.goodsId = selectProductList[i].goods_id
                        goodsBody.sort = i
                        goodslist.add(goodsBody)
                    }
                    sendRequestBody.goodsList = goodslist
                }

                if (App.getInstance().isLogin) {
                    when (type) {
                        SEND_COMMENT -> {//发布评论
                            val bundle = Bundle()
                            bundle.putSerializable(KEY_RESULY, initRequestBody())
                            setFragmentResult(KEY_REQUESCODE, bundle)
                            pop()
                        }
                        SEND_GOODS_PIC -> {//发送商品图片
                            val bundle = Bundle()
                            val model = GoodsDesModel()
                            model.decContent = edit_send_tw.text.toString()
                            model.decPic = Gson().toJson(fileList)
                            bundle.putSerializable(KEY_RESULT_PAMAS, model)
                            setFragmentResult(RESULT_CODE, bundle)
                            pop()
                        }
                        else -> {//发布图文
                            checkIsNull(sendRequestBody)
                        }
                    }
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public fun getHistorySelectList(string: String) {
        selectProductList = GSONUtil.getEntityList(string, GoodsModel::class.java)
        tv_select_num.text = "已选择${selectProductList.size}款商品"
    }


    /**
     * 发布图文
     */
    fun checkIsNull(body: SendRequestBody) {
        mPresenter.sendTw(body)
    }


    /**
     * 发布评论请求body初始化
     */
    private fun initRequestBody(): RequestBodyComment {
        val body = RequestBodyComment()
        val contentBody = RequestBodyComment.GsTopicCommentBean()
        contentBody.content = edit_send_tw.text.toString()
        val fileList: MutableList<RequestBodyComment.FileList> = arrayListOf()
        if (picAdapter!!.data.size > 0) {//
            if (picAdapter!!.data[0].file == ADD_PIC) {//第一张是加号图标  除去加号图标
                for (i in 1 until picAdapter!!.data.size) {
                    val fileBody = RequestBodyComment.FileList()
                    fileBody.file = picAdapter!!.data[i].file
                    fileList.add(fileBody)
                }
            } else {//全是图片 没有加号图标
                for (i in 0 until picAdapter!!.data.size) {
                    val fileBody = RequestBodyComment.FileList()
                    fileBody.file = picAdapter!!.data[i].file
                    fileList.add(fileBody)
                }
            }
        }
        body.fileList = fileList
        body.gsTopicComment = contentBody
        return body
    }


    /**
     * 选择头像
     */
    fun choosePicture() {
        hideSoftInput()
        if (picAdapter!!.data.size > 0) {
            maxNum = if (picAdapter!!.data[picAdapter!!.data.size - 1].file == ADD_PIC) {
                9 - picAdapter!!.data.size + 1
            } else {
                9 - picAdapter!!.data.size
            }
        }
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxNum)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }


    /**
     * 选择城市回调  图片回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == ISupportFragment.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
//                    list = selectList
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    for (i in 0 until selectList.size) {
                        val fileBody = FileBody()
                        if (selectList[i].isCompressed) {
                            fileBody.file = selectList[i].compressPath
                        } else {
                            fileBody.file = selectList[i].path
                        }
                        fileBody.sort = i
                        picList.add(fileBody)
                    }

                    //上传成功后  适配数据
                    mPresenter.upLoadFiles(picList)
                }


            }
        }

        super.onActivityResult(requestCode, resultCode, data)
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


    /**
     * 设置引导图
     */
    fun setGuideView() {

        val iv1 = ImageView(context)
        iv1.setImageResource(R.drawable.union_guide_access)
        val params1 = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        iv1.layoutParams = params1

        val iv2 = TextView(context)
        iv2.text = "长按拖动排序"
        iv2.setTextColor(ContextCompat.getColor(context!!, R.color.white))
        iv2.textSize = 18f
        val params2 = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        iv2.layoutParams = params2

        var guideView: GuideView? = null
        guideView = GuideView.Builder
                .newInstance(baseActivity)
                .setTargetView(rv_pic)  //设置目标view
                .setTextGuideView(iv1)      //设置文字图片
                .setCustomGuideView(iv2)    //设置 我知道啦图片
                .setOffset(0, 50)           //偏移量  x=0 y=80
                .setDirction(GuideView.Direction.BOTTOM)   //方向
                .setShape(GuideView.MyShape.RECTANGULAR)   //矩形
                .setRadius(0)                             //圆角
                .setContain(true)                         //透明的方块时候包含目标view  默认false
                .setOnclickListener {
                    AppPreference.getIntance().userFristInterAccess = false
                    guideView?.hide()
                }
                .build()
        guideView.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}