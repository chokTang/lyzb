package com.lyzb.jbx.fragment.me.company

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseToolbarFragment
import com.like.utilslib.image.LoadImageUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.R
import com.lyzb.jbx.dialog.BusinessDialog
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.CreateCompanyBody
import com.lyzb.jbx.mvp.presenter.me.company.CreateCompanyPresenter
import com.lyzb.jbx.mvp.view.me.ICreateCompanyView
import com.szy.yishopcustomer.Util.LubanImg.getPath
import kotlinx.android.synthetic.main.frgment_create_company.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by :TYK
 * Date: 2019/4/19  9:57
 * Desc: 创建企业(后面改版的)
 */
class CreateCompanyFragment : BaseToolbarFragment<CreateCompanyPresenter>(), ICreateCompanyView, View.OnClickListener {


    var typeId = ""
    var list: MutableList<LocalMedia> = arrayListOf()
    val body = CreateCompanyBody()
    var bean: CreateCompanyBody.GsDistributorVoBean? = null

    override fun getResId(): Any {
        return R.layout.frgment_create_company
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        setToolbarTitle("创建企业")
        bean = CreateCompanyBody.GsDistributorVoBean()

        tv_comd_type.setOnClickListener(this)
        ll_add_pic.setOnClickListener(this)
        tv_create_company.setOnClickListener(this)
        img_logo.setOnClickListener(this)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
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

//                构造图片上传model
                val file = CreateCompanyBody.ComdFile()
                file.tagType = 6
                file.filePath = imgUrl
                file.fileType = 1
                file.delStatus = 0
                val fileList: MutableList<CreateCompanyBody.ComdFile> = arrayListOf()
                fileList.add(file)
                body.distributorFiles = fileList
            }
        })

    }

    /**
     * 创建成功
     */
    override fun createCompanySuccess() {
        pop()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_add_pic, R.id.img_logo -> {//企业logo
                choosePicture()
            }
            R.id.tv_comd_type -> {//企业类型
                mPresenter.getInducList()
            }
            R.id.tv_create_company -> {//确认创建
                body.optType = "add"
                body.diff = "dis"
                this.bean!!.companyName = edt_comd_name.text.toString()
                body.gsDistributorVo = bean
                checkNull(body)
            }
        }
    }

    /**
     * 判断空
     */
    fun checkNull(body: CreateCompanyBody) {
        if (TextUtils.isEmpty(body.gsDistributorVo.companyName)) {
            showToast("请输入企业名称")
            return
        }
        if (TextUtils.isEmpty(body.gsDistributorVo.industryName)) {
            showToast("请选择企业类型")
            return
        }
        mPresenter.createCompany(body)
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

                    mPresenter.upAliyun(selectList[0].cutPath)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}