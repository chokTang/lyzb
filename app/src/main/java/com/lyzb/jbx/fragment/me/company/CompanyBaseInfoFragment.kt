package com.lyzb.jbx.fragment.me.company

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseToolbarFragment
import com.like.utilslib.image.LoadImageUtil
import com.like.utilslib.other.RegexUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.R
import com.lyzb.jbx.dialog.BusinessDialog
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment.Companion.PARAMS_ID
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.ComdDetailModel
import com.lyzb.jbx.model.me.CreateCompanyBody
import com.lyzb.jbx.mvp.presenter.me.company.CompanyBaseInfoPresenter
import com.lyzb.jbx.mvp.view.me.ICompanyBaseInfoView
import com.lyzb.jbx.util.EditTextViewUtil
import com.szy.yishopcustomer.Util.LubanImg.getPath
import kotlinx.android.synthetic.main.fragment_company_base_info.*
import kotlinx.android.synthetic.main.layout_title.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by :TYK
 * Date: 2019/4/20  11:15
 * Desc: 企业基本信息
 */

class CompanyBaseInfoFragment : BaseToolbarFragment<CompanyBaseInfoPresenter>(), ICompanyBaseInfoView, View.OnClickListener {


    var typeId = ""  //行业ID
    var companyId = ""  //企业ID
    var list: MutableList<LocalMedia> = arrayListOf()
    val body = CreateCompanyBody()
    var isMeCompany = false //是否是自己的企业,自己的企业可以操作
    var bean: CreateCompanyBody.GsDistributorVoBean? = null
    var newfile: CreateCompanyBody.ComdFile? = null
    var oldfile: CreateCompanyBody.ComdFile? = null
    val fileList: MutableList<CreateCompanyBody.ComdFile> = arrayListOf()
    var picId = ""
    var companyType = ""
    companion object {
        const val KEY_COMPANY_TYPE = "companytype"
        fun newIntance(companyId: String,disType:String): CompanyBaseInfoFragment {
            val frgamnt = CompanyBaseInfoFragment()
            val bundle = Bundle()
            bundle.putString(PARAMS_ID, companyId)
            bundle.putString(KEY_COMPANY_TYPE, disType)
            frgamnt.arguments = bundle
            return frgamnt
        }
    }


    override fun getResId(): Any {
        return R.layout.fragment_company_base_info
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            companyId = bundle.getString(PARAMS_ID)
            companyType = bundle.getString(KEY_COMPANY_TYPE)
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        setToolbarTitle("基本信息")
        tv_right.text = "保存"

        bean = CreateCompanyBody.GsDistributorVoBean()
        newfile = CreateCompanyBody.ComdFile()
        oldfile = CreateCompanyBody.ComdFile()

        tv_comd_type.setOnClickListener(this)
        ll_add_pic.setOnClickListener(this)
        tv_right.setOnClickListener(this)
        img_logo.setOnClickListener(this)
        //防止换行
        EditTextViewUtil.setListener(edit_address)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getCompantInfo(companyId)
    }


    /**
     * 获取行业信息成功回调
     */
    override fun getTypeList(list: MutableList<BusinessModel>) {
        BusinessDialog
                .newIntance()
                .setMaxSelectd(1)
                .setList(list)
                .invoke(object : BusinessDialog.ClickListener {
                    override fun click(v: View?, list: MutableList<BusinessModel>?) {
                        typeId = list!![0].id.toString()
                        tv_comd_type.text = list[0].name
                        bean!!.industryName = list[0].name
                        bean!!.industryId = list[0].id.toString()
                    }
                }).show(fragmentManager!!, "SelectList")
    }

    /**
     * logo上传成功
     */
    override fun OnUploadResult(imgUrl: String?) {
        activity!!.runOnUiThread(Runnable {
            if (TextUtils.isEmpty(imgUrl)) {
                ll_add_pic.visibility = View.VISIBLE
                img_logo.visibility = View.GONE
            } else {
                img_logo.visibility = View.VISIBLE
                LoadImageUtil.loadImage(img_logo, imgUrl)
                ll_add_pic.visibility = View.GONE
                if (imgUrl!!.contains("oss")) {
                    //  构造新图片上传model
                    newfile!!.tagType = 6
                    newfile!!.filePath = imgUrl
                    newfile!!.fileType = 1
                    newfile!!.distributorId = companyId
                    //判断原来企业是否有图片ID 没有则添加 有就修改
                    newfile!!.delStatus = 0
                    //这里要有修改的新文件时候才上传以前的文件 不然要删除掉老文件
                    fileList.add(oldfile!!)
                    fileList.add(newfile!!)
                }

            }
        })

    }

    /**
     * 获取企业详情回调
     */
    override fun queryCompanyDetail(model: ComdDetailModel) {
        val bean = model.gsDistributorVo
        picId = bean.logo
        //1  自己的企业  可编辑  2 别人的企业  不可编辑
        isMeCompany = bean.isDefault == 1

        edit_name.text = Editable.Factory.getInstance().newEditable(bean.companyName)
        tv_comd_type.text = bean.industryName
        if (!TextUtils.isEmpty(bean.disTel)){
            edit_phone.text = Editable.Factory.getInstance().newEditable(bean.disTel)
        }
        if (!TextUtils.isEmpty(bean.disAddress)){
            edit_address.text = Editable.Factory.getInstance().newEditable(bean.disAddress)
        }

        this.bean!!.industryName = bean.industryName
        this.bean!!.industryId = bean.industryId?.toString()

        //如果是自己的企业
        if (isMeCompany) {
            if (TextUtils.isEmpty(bean.logo)) {//没有图片
                ll_add_pic.visibility = View.VISIBLE
                img_logo.visibility = View.GONE
            } else {//有图片
                ll_add_pic.visibility = View.GONE
                img_logo.visibility = View.VISIBLE
                //  老图片上传上去 删除
                oldfile!!.tagType = 6
                oldfile!!.filePath = bean.logo
                oldfile!!.fileType = 1
                oldfile!!.distributorId = companyId
                oldfile!!.delStatus = 1
                for (i in 0 until model.distributorFiles.size) {
                    if (model.distributorFiles[i].tagType == 6) {//拿到logo对象
                        oldfile!!.id = model.distributorFiles[i].id
                    }
                }
                LoadImageUtil.loadImage(img_logo, bean.logo,R.mipmap.icon_company_defult)
            }
            tv_right.visibility = View.VISIBLE
            edit_phone.isEnabled = true
            //企业类型（1.普通企业 2.团购企业）
            if (companyType=="2"){
                edit_name.isEnabled = false
                edit_address.isEnabled = false
            }else{
                edit_name.isEnabled = true
                edit_address.isEnabled = true
            }

        } else {
            LoadImageUtil.loadImage(img_logo, bean.logo,R.mipmap.icon_company_defult)
            img_logo.visibility = View.VISIBLE
            ll_add_pic.visibility = View.GONE
            tv_right.visibility = View.GONE
            edit_name.isEnabled = false
            edit_phone.isEnabled = false
            edit_address.isEnabled = false
        }
    }

    /**
     * 保存成功回调
     */
    override fun saveInfoSuccess() {
        pop()
    }

    override fun onClick(v: View?) {
        if (!isMeCompany) {
            return
        }
        when (v?.id) {
            R.id.ll_add_pic, R.id.img_logo -> {//企业logo
                choosePicture()
            }
            R.id.tv_comd_type -> {//企业类型
                mPresenter.getInducList()
            }
            R.id.tv_right -> {//保存
                body.optType = "upt"
                body.diff = "dis"
                bean!!.companyName = edit_name.text.toString()
                bean!!.id = companyId
                bean!!.disAddress = edit_address.text.toString()
                bean!!.disTel = edit_phone.text.toString()

                body.gsDistributorVo = bean
                body.distributorFiles = fileList
                checkNull(body)
            }
        }
    }

    fun checkNull(body: CreateCompanyBody) {
        if (TextUtils.isEmpty(body.gsDistributorVo.companyName)) {
            showToast("公司名字不能为空!")
            return
        }
        if (TextUtils.isEmpty(body.gsDistributorVo.industryName)) {
            showToast("企业类型不能为空!")
            return
        }
        if (TextUtils.isEmpty(body.gsDistributorVo.disTel)) {
            showToast("企业电话不能为空!")
            return
        }
        if (!RegexUtil.checkMobile(body.gsDistributorVo.disTel)&&!RegexUtil.checkPhone(body.gsDistributorVo.disTel)){
            showToast("请输入正确的电话号码")
            return
        }
        mPresenter.saveInfo(body)
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
     *  图片回调
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

                    mPresenter.upAliyun(selectList[0].cutPath)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}