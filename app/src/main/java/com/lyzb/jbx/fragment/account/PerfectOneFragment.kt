package com.lyzb.jbx.fragment.account

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.like.longshaolib.base.BaseFragment
import com.like.utilslib.image.LoadImageUtil
import com.like.utilslib.other.LogUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.R
import com.lyzb.jbx.activity.PerfectActivity
import com.lyzb.jbx.adapter.account.PerfectBusinessAdapter
import com.lyzb.jbx.adapter.account.PopupwindowAdapter
import com.lyzb.jbx.dialog.BusinessDialog
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.account.QueryByShopName
import com.lyzb.jbx.model.account.RequestPerfectBean
import com.lyzb.jbx.mvp.presenter.account.PerfectOnePresenter
import com.lyzb.jbx.mvp.view.account.IPerfectOneView
import com.lyzb.jbx.util.EditTextViewUtil
import com.szy.common.Activity.RegionActivity
import com.szy.common.Fragment.RegionFragment
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.RequestCode
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.LubanImg.getPath
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.tbruyelle.rxpermissions.Permission
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.fragment_perfect_one.*
import kotlinx.android.synthetic.main.layout_xx_title.*
import me.yokeyword.fragmentation.ISupportFragment
import rx.functions.Action1

/**
 * Created by :TYK
 * Date: 2019/3/5  16:15
 * Desc: 完善信息页面1
 */

class PerfectOneFragment : BaseFragment<PerfectOnePresenter>(), IPerfectOneView, View.OnClickListener {

    companion object {
        //动态请求的权限数组
        var permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        const val PERMISSION_CODE: Int = 3694
        const val RESULT_CODE = 6654
        const val RESULT_BUNDLE_KEY = "result_bundle_key"
    }

    var adapter: PerfectBusinessAdapter? = null
    var list: MutableList<LocalMedia> = arrayListOf()
    var listbusiness: MutableList<BusinessModel> = arrayListOf()
    var dialog: BusinessDialog? = null
    var requestPerfectBean: RequestPerfectBean? = null
    var popAdapter: PopupwindowAdapter? = null
    var isClick = false
    var companyId = ""

    override fun getResId(): Any {
        return R.layout.fragment_perfect_one
    }

    /**
     * 上传文件回调
     */
    override fun OnUploadResult(imgUrl: String?) {

        activity!!.runOnUiThread(Runnable {
            LoadImageUtil.loadRoundSizeImage(img_avatar, imgUrl, 50)
            if (TextUtils.isEmpty(imgUrl)) {
                img_take.visibility = View.VISIBLE
            } else {
                img_take.visibility = View.GONE
            }
        })
        requestPerfectBean!!.headimg = imgUrl
    }

    override fun OnGetListBusiness(bean: MutableList<BusinessModel>?) {
        listbusiness = bean!!
        dialog!!.setList(listbusiness)
        dialog!!.show(fragmentManager!!, "选择行业")
    }

    /**
     * 商家姓名联想回调
     */
    override fun OnGetShopNameList(list: MutableList<QueryByShopName.DataListBean>?) {
        if (null == list || list.size == 0) {
            ll_shop_name_list.visibility = View.GONE
        } else {
            popAdapter!!.setNewData(list)
            ll_shop_name_list.visibility = View.VISIBLE
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        tv_center_text.text = "完善信息"
        requestPerfectBean = RequestPerfectBean()
        adapter = PerfectBusinessAdapter()
        dialog = BusinessDialog()
        dialog!!.max = 10
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_business.layoutManager = linearLayoutManager
        rv_business.adapter = adapter
        val bean = BusinessModel()
        bean.name = ""
        listbusiness.add(bean)
        adapter!!.setNewData(listbusiness)

        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.item_business_null -> {//点击选择行业
                    listbusiness.clear()
                    mPresenter.getListBusiness()
                }
                R.id.ll_business -> {//已经选择后点击 选择行业栏
                    listbusiness.clear()
                    mPresenter.getListBusiness()
                }
            }
        }

        img_back.setOnClickListener(this)
        edit_job.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        tv_city.setOnClickListener(this)
        ll_city.setOnClickListener(this)
        ll_business.setOnClickListener(this)
        img_avatar.setOnClickListener(this)

        //防止换行
        EditTextViewUtil.setListener(edit_name)
        EditTextViewUtil.setListener(edit_job)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_man -> {
                    requestPerfectBean!!.sex = "1"
                }
                R.id.rb_woman -> {
                    requestPerfectBean!!.sex = "2"
                }
            }
        }

        dialog!!.invoke(object : BusinessDialog.ClickListener {
            override fun click(v: View?, list: MutableList<BusinessModel>?) {
                when (v!!.id) {
                    R.id.tv_finish -> {//完成
                        var str = ""
                        for (i in 0 until list!!.size) {
                            str = if (i == 0) {
                                list[i].id.toString()
                            } else {
                                list[i].id.toString() + "," + str
                            }
                        }
                        requestPerfectBean!!.professionId = str
                        if (list.size > 0) {
                            adapter!!.setNewData(list)
                            requestPerfectBean!!.professionName = list
                        } else {
                            val bean = BusinessModel()
                            bean.name = ""
                            listbusiness.add(bean)
                            adapter!!.setNewData(listbusiness)
                            requestPerfectBean!!.professionName = listbusiness
                        }
                        dialog!!.dismiss()
                    }
                }
            }
        })

        popAdapter = PopupwindowAdapter()
        val popmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rv_shop_name_link.layoutManager = popmanager
        rv_shop_name_link.adapter = popAdapter
        popAdapter!!.setOnItemClickListener { adapter, view, position ->
            isClick = true
            edit_sj.text = Editable.Factory.getInstance().newEditable((adapter.data[position] as QueryByShopName.DataListBean).companyName)
            companyId = (adapter.data[position] as QueryByShopName.DataListBean).id
            ll_shop_name_list.visibility = View.GONE
        }

        /**
         * 商家名称关联
         */
        edit_sj.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isEmpty()) {
                    ll_shop_name_list.visibility = View.GONE
                    isClick = false
                    return
                } else {
                    if (isClick) {
                        ll_shop_name_list.visibility = View.GONE
                    } else {
                        ll_shop_name_list.visibility = View.VISIBLE
                        mPresenter.getListByShopName(s.toString())
                    }

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {//返回
                if (activity is PerfectActivity) {
                    activity!!.finish()
                } else {
                    pop()
                }
            }

            R.id.img_avatar -> {//点击头像
                val rxPermissions = RxPermissions(activity!!)
                rxPermissions.requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(object : Action1<Permission> {
                            override fun call(t: Permission?) {
                                if (t!!.granted) {
                                    choosePicture()
                                }
                            }
                        })
            }

            R.id.edit_job -> {//职位
                LogUtils.e("你点击了职位")
            }

            R.id.ll_business -> {//点击选择行业
                listbusiness.clear()
                mPresenter.getListBusiness()
            }

            R.id.tv_city, R.id.ll_city -> {//城市
                openRegionActivity("500112")
            }

            R.id.tv_next -> {//下一步
                requestPerfectBean!!.gsName = edit_name.text.toString()
                requestPerfectBean!!.position = edit_job.text.toString()
                requestPerfectBean!!.shopName = edit_sj.text.toString()
                requestPerfectBean!!.currentDepartmentID = companyId
                checkIsNull(requestPerfectBean!!)
            }
        }
    }

    /**
     * 检查传入参数
     */
    fun checkIsNull(requestPerfectBean: RequestPerfectBean) {

        if (TextUtils.isEmpty(requestPerfectBean.headimg)) {
            showToast("请选择头像")
            return
        }
        if (TextUtils.isEmpty(requestPerfectBean.gsName)) {
            showToast("请填写您的姓名")
            return
        }
        if (TextUtils.isEmpty(requestPerfectBean.sex)) {
            showToast("请选择称呼")
            return
        }
        if (TextUtils.isEmpty(requestPerfectBean.position)) {
            showToast("请填写您的职位/岗位")
            return
        }
        if (TextUtils.isEmpty(requestPerfectBean.shopName)) {
            showToast("请填写商家名称")
            return
        }
        if (TextUtils.isEmpty(requestPerfectBean.professionId)) {
            showToast("请选择您熟悉的行业")
            return
        }
        if (TextUtils.isEmpty(requestPerfectBean.residence)) {
            showToast("请选择城市")
            return
        }
        startForResult(PerfectTwoFragment.newInstance(requestPerfectBean), RESULT_CODE)
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
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList)
//                    adapter.notifyDataSetChanged()
//                    mPresenter.upLoadFile(selectList[0])
                    mPresenter.upAliyun(selectList[0].cutPath)
                }
            }
        }

        //地址回调
        when (RequestCode.valueOf(requestCode)) {
            RequestCode.REQUEST_CODE_REGION_CODE -> if (data != null) {
                val addressDate = data.extras
                val regionName = addressDate!!.getString(RegionFragment.KEY_REGION_LIST)
                val regionCode = addressDate.getString(RegionFragment.KEY_REGION_CODE)

                LogUtil.loge("当前的region code为" + regionCode)
                if (!TextUtils.isEmpty(regionCode)) {
                    var str = regionCode.split(",")
                    when (str.size) {
                        1 -> {
                            requestPerfectBean!!.residence = str[0]
                        }
                        2 -> {
                            requestPerfectBean!!.residence = str[0] + ";" + regionCode
                        }
                        3 -> {
                            requestPerfectBean!!.residence = str[0] + ";" + str[0] + "," + str[1] + ";" + regionCode
                        }
                    }
                }

                LogUtil.loge("传入的地区regioncode" + requestPerfectBean!!.residence)
                requestPerfectBean!!.regionName = regionName
                tv_city.text = regionName
            }
            RequestCode.REQUEST_CODE_ADDRESS -> {
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 保存信息回调
     */
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                RESULT_CODE -> {
                    requestPerfectBean = GsonUtils.toObj(data.getString(RESULT_BUNDLE_KEY), RequestPerfectBean::class.java)
                    //如果保存了数据 开始进来就加载数据
                    LoadImageUtil.loadRoundSizeImage(img_avatar, requestPerfectBean?.headimg, 50)
                    edit_name.text = Editable.Factory.getInstance().newEditable(requestPerfectBean?.gsName)
                    if (requestPerfectBean?.sex == "1") {
                        rb_man.isChecked = true
                    } else {
                        rb_woman.isChecked = true
                    }
                    edit_job.text = Editable.Factory.getInstance().newEditable(requestPerfectBean?.position)
                    edit_sj.text = Editable.Factory.getInstance().newEditable(requestPerfectBean?.shopName)
                    tv_city.text = Editable.Factory.getInstance().newEditable(requestPerfectBean?.regionName)
                    adapter!!.setNewData(requestPerfectBean?.professionName)
                }
            }
        }
    }

    /**
     * 选择头像
     */
    fun choosePicture() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
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
     * 权限回调
     */
//说明：
//Constants.CODE_CAMERA
//这是在外部封装的一个常量类，里面有许多静态的URL以及权限的CODE，可以自定义
//但是在调用的时候，记得这个CODE要和你自己定义的CODE一一对应
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                choosePicture()
            }
        }
    }

    /**
     * 选择地区
     *
     * @param regionCode
     */
    private fun openRegionActivity(regionCode: String) {
        val intent = Intent(context, RegionActivity::class.java)
        intent.putExtra(RegionFragment.KEY_REGION_CODE, regionCode)
        intent.putExtra(RegionFragment.KEY_API, Api.API_REGION_LIST)
        startActivityForResult(intent, RequestCode.REQUEST_CODE_REGION_CODE.value)
    }

    override fun onBackPressedSupport(): Boolean {
        return true
    }
}
