package com.lyzb.jbx.fragment.me.company

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseFragment
import com.like.longshaolib.dialog.fragment.AlertDialogFragment
import com.like.utilslib.image.LoadImageUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.R
import com.lyzb.jbx.dialog.BusinessDialog
import com.lyzb.jbx.fragment.me.card.AddressFragment
import com.lyzb.jbx.fragment.me.card.AddressFragment.Companion.KEY_ADDRESS
import com.lyzb.jbx.fragment.me.card.UserInfoFragment
import com.lyzb.jbx.inter.ISelectPictureListener
import com.lyzb.jbx.model.LocationAddressInfo
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.company.ChildrenBean
import com.lyzb.jbx.model.me.company.OrganInfoModel
import com.lyzb.jbx.model.me.company.OrganNenberModel
import com.lyzb.jbx.model.params.AddOrganBody
import com.lyzb.jbx.mvp.presenter.me.company.AddOrganPresenter
import com.lyzb.jbx.mvp.view.me.IAddOrganView
import com.szy.yishopcustomer.Util.LubanImg.getPath
import com.szy.yishopcustomer.View.AttributeDialog
import kotlinx.android.synthetic.main.fragment_addorgan.*
import kotlinx.android.synthetic.main.toolbar_statistics.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 *@author wyx
 *@version
 *@role 新增、编辑机构
 *@time 2019 2019/6/14 13:49
 */
class AddOrganFragment : BaseFragment<AddOrganPresenter>(), View.OnClickListener, IAddOrganView,
        ChoiceOrganFragment.OnChoiceOrganListener, ChoiceOrganAdminFragment.OnChoiceOrganAdminListener {

    /**
     * 类型 0新增机构，1编辑机构
     */
    var type = 0
    /**
     * 机构类型,2==部门，
     */
    var organType = ""

    var mOrganId = ""

    var mParentOrgId = ""

    var mOrganData = OrganInfoModel()

    var mLogoUrl = ""

    companion object {
        const val CHOICE_ORGAN_REQUESTCODE = 100
        fun newInstance(type: Int, organType: String, organId: String, parentOrgId: String): AddOrganFragment {
            val fragment = AddOrganFragment()
            fragment.type = type
            fragment.organType = organType
            fragment.mOrganId = organId
            fragment.mParentOrgId = parentOrgId
            return fragment
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {

        statistics_back_iv.setOnClickListener(this)
        addorgan_department_rbtn.setOnClickListener(this)
        addorgan_company_rbtn.setOnClickListener(this)
        addorgan_admin_tv.setOnClickListener(this)
        addorgan_admin_iv.setOnClickListener(this)
        addorgan_company_logo_iv.setOnClickListener(this)
        addorgan_companytype_tv.setOnClickListener(this)
        addorgan_companytype_iv.setOnClickListener(this)
        addorgan_address_tv.setOnClickListener(this)
        addorgan_address_iv.setOnClickListener(this)
        addorgan_superiororgan_tv.setOnClickListener(this)
        addorgan_superiororgan_iv.setOnClickListener(this)
        addorgan_delete_tv.setOnClickListener(this)
        addorgan_confirm_tv.setOnClickListener(this)

        //新增机构时展示选择机构类型，编辑时展示具体机构类型
        if (type == 0) {
            statistics_title_tv.text = "添加子机构"
            if (organType == "2") {
                addorgan_companyinfo_ll.visibility = View.GONE
                addorgan_organtype_rg.visibility = View.GONE
                addorgan_type_tv.visibility = View.VISIBLE
                addorgan_type_tv.text = "部门"
            }
            organType = "2"
        } else {
            statistics_title_tv.text = "编辑机构"
            addorgan_content_ll.visibility = View.GONE
            mPresenter.getOrganInfo(mOrganId)
        }

    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun getResId(): Any {
        return R.layout.fragment_addorgan
    }

    /**
     * 获取机构信息成功
     */
    override fun onOrganData(data: OrganInfoModel) {
        mOrganData = data
        addorgan_organtype_rg.visibility = View.GONE
        addorgan_type_tv.visibility = View.VISIBLE
        addorgan_content_ll.visibility = View.VISIBLE
        addorgan_hint_tv.visibility = View.GONE
        addorgan_department_rbtn.isChecked = false
        //机构类型
        if (data.orgType == "2") {
            addorgan_type_tv.text = "部门"
            addorgan_companyinfo_ll.visibility = View.GONE
        } else {
            addorgan_type_tv.text = "企业"
            addorgan_companyinfo_ll.visibility = View.VISIBLE
            addorgan_company_edit_ll.visibility = View.VISIBLE
        }
        //机构名称
        addorgan_name_edit.setText(data.companyName)
        //机构负责人
        addorgan_admin_tv.text = data.chargePerson
        //能否修改负责人
        if (!data.isIsEditCharge) {
            addorgan_admin_tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.fontcColor3))
            addorgan_admin_iv.visibility = View.GONE
        }
        //是否为顶级机构
        if (data.parentType == "1") {
            addorgan_name_line.visibility = View.GONE
            addorgan_admin_ll.visibility = View.GONE
            addorgan_superiororgan_ll.visibility = View.GONE
            addorgan_delete_tv.visibility = View.GONE
        } else {
            addorgan_name_line.visibility = View.VISIBLE
            addorgan_admin_ll.visibility = View.VISIBLE
            addorgan_superiororgan_ll.visibility = View.VISIBLE
            addorgan_delete_tv.visibility = View.VISIBLE
            if (data.orgType == "2") {
                addorgan_admin_line.visibility = View.VISIBLE
            }
        }
        //能否修改地址、电话
        if (data.canEdit == "2") {
            addorgan_name_edit.isFocusable = false
            addorgan_name_edit.isFocusableInTouchMode = false
            addorgan_superiororgan_tv.hint = ""
            addorgan_superiororgan_iv.visibility = View.GONE
        }
        //企业logo
        LoadImageUtil.loadRoundImage(addorgan_company_logo_iv, data.logoFilePath, 4)
        //企业类型
        addorgan_companytype_tv.text = data.industryName
        //企业电话
        addorgan_phone_edit.setText(data.disTel)
        //企业地址
        addorgan_address_tv.text = data.disAddress
        //上级机构
        addorgan_superiororgan_tv.text = data.parentOrgName
    }

    override fun onAddOrganSuccess() {
        pop()
    }

    /**
     * 查询能否删除机构回调
     */
    override fun canDeleteOrganSuccess(canDelSt: Boolean) {
        val dialog = AlertDialogFragment.newIntance()
        val content: String
        if (canDelSt) {
            content = "确定要删除机构吗？"
            dialog.setCancleBtn(null)
        } else {
            content = "请先删除机构下员工和子机构，再来删除机构！"
        }
        dialog.setKeyBackable(false)
                .setCancleable(false)
                .setTitle("提示")
                .setContent(content)
                .setSureBtn {
                    if (canDelSt) {
                        val body = AddOrganBody()
                        body.gsDistributorVo = OrganInfoModel()
                        body.gsDistributorVo.id = mOrganId
                        body.optType = "del"
                        mPresenter.addOrgan(body)
                    }
                }
                .show(fragmentManager!!, "LogOutTag")
    }

    /**
     * 获取企业类型列表成功
     */
    override fun getInducListSuccess(list: MutableList<BusinessModel>) {
        BusinessDialog
                .newIntance()
                .setMaxSelectd(1)
                .setList(list)
                .invoke(object : BusinessDialog.ClickListener {
                    override fun click(v: View?, list: MutableList<BusinessModel>?) {
                        mOrganData.industryName = list!![0].name
                        mOrganData.industryId = list[0].id.toString()
                        addorgan_companytype_tv.text = list[0].name
                    }
                }).show(fragmentManager!!, "SelectList")
    }

    /**
     * 选择机构的回调
     */
    override fun onChoiceOrganListener(organs: MutableList<ChildrenBean>) {
        mParentOrgId = organs[0].id
        mOrganData.parentOrgId = organs[0].id
        addorgan_superiororgan_tv.text = organs[0].companyName
    }

    /**
     * 选择负责人回调
     */
    override fun onChoiceOrganAdminListener(list: MutableList<OrganNenberModel.DataBean>) {
        mOrganData.membershipId = list[0].userId
        addorgan_admin_tv.text = list[0].gsName
    }

    private fun submit(delete: Boolean) {

        val body = AddOrganBody()
        if (type == 0) {
            //新增机构
            body.optType = "add"
            //机构类型2=部门，1=企业
            body.dataType = organType
            //上级机构id
            body.parentOrgId = mParentOrgId
        } else {
            //编辑机构
            body.optType = "upt"
            //编辑机构时又不一样
            body.dataType = mOrganData.orgType
            //上级机构id，顶级为0
            body.parentOrgId = mOrganData.parentOrgId ?: "0"
        }
        //有logo信息就加进去没有就算了
        if (!TextUtils.isEmpty(mLogoUrl)) {
            val distributorFiles = body.DistributorFiles()
            distributorFiles.delStatus = 0
            distributorFiles.fileType = 1
            distributorFiles.tagType = 6
            distributorFiles.filePath = mLogoUrl
            body.distributorFiles = mutableListOf()
            body.distributorFiles.add(distributorFiles)
        }
        if (delete) {
            body.optType = "del"
        }
        mOrganData.companyName = addorgan_name_edit.text.toString()
        mOrganData.disTel = addorgan_phone_edit.text.toString()
        mOrganData.disAddress = addorgan_address_tv.text.toString()
        body.gsDistributorVo = mOrganData
        mPresenter.addOrgan(body)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            //返回键
            R.id.statistics_back_iv -> {
                pop()
            }
            //结构类型-部门
            R.id.addorgan_department_rbtn -> {
                organType = "2"
                addorgan_companyinfo_ll.visibility = View.GONE
                addorgan_name_line.visibility = View.VISIBLE
            }
            //机构类型-公司
            R.id.addorgan_company_rbtn -> {
                organType = "1"
                addorgan_companyinfo_ll.visibility = View.VISIBLE
                addorgan_name_line.visibility = View.GONE
            }
            //选择机构负责人
            R.id.addorgan_admin_tv, R.id.addorgan_admin_iv -> {
                if (!mOrganData.isIsEditCharge) {
                    return
                }
                start(ChoiceOrganAdminFragment.newIntance(mOrganId, this))
            }
            //企业logo
            R.id.addorgan_company_logo_iv -> {
                choosePicture()
            }
            //选择公司类型
            R.id.addorgan_companytype_tv, R.id.addorgan_companytype_iv -> {
                if (type == 0) {
                    mPresenter.getInducList()
                }
            }
            //选择地址
            R.id.addorgan_address_tv, R.id.addorgan_address_iv -> {
                if (mOrganData.canEdit == "2") {
                    return
                }
                startForResult(AddressFragment(), UserInfoFragment.RESULT_ADDRESS_CODE)
            }
            //选择上级
            R.id.addorgan_superiororgan_tv, R.id.addorgan_superiororgan_iv -> {
                startForResult(ChoiceOrganFragment.newIntance(mOrganId, "1", organType, this), CHOICE_ORGAN_REQUESTCODE)
            }
            //删除
            R.id.addorgan_delete_tv -> {
                mPresenter.canDeleteOrgan(mOrganId)
            }
            //确认
            R.id.addorgan_confirm_tv -> {
                submitCheck()
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (resultCode != ISupportFragment.RESULT_OK || data == null) return
        when (requestCode) {
            //选择地址
            UserInfoFragment.RESULT_ADDRESS_CODE -> {
                val addressInfo = data.getSerializable(KEY_ADDRESS) as LocationAddressInfo
                mOrganData.mapLat = addressInfo.lat.toString()
                mOrganData.mapLng = addressInfo.lon.toString()
                mOrganData.disAddress = addressInfo.getText()
                addorgan_address_tv.text = addressInfo.getText()
            }
        }
    }

    /**
     *提交前的判断
     */
    private fun submitCheck() {
        if (TextUtils.isEmpty(addorgan_name_edit.text)) {
            showToast("请输入机构名称")
            return
        }
        if (TextUtils.isEmpty(addorgan_superiororgan_tv.text) && mOrganData.parentType != "1") {
            showToast("请选择上级机构")
            return
        }
        submit(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null || resultCode != ISupportFragment.RESULT_OK || requestCode != UserInfoFragment.AVATAR) return
        val selectList = PictureSelector.obtainMultipleResult(data)
        // 图片、视频、音频选择结果回调
        // 例如 LocalMedia 里面返回三种path
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
        val path = if (selectList[0].isCut) {
            selectList[0].cutPath
        } else {
            selectList[0].path
        }
        mPresenter.upFileLoad(path, object : ISelectPictureListener {
            override fun onSuccess(imgUrl: String) {
                mLogoUrl = imgUrl
                addorgan_company_logo_iv.post {
                    LoadImageUtil.loadRoundImage(addorgan_company_logo_iv, mLogoUrl, 4)
                }
            }

            override fun onFail() {
                showToast("上传失败")
            }
        })
    }

    val list: MutableList<LocalMedia> = mutableListOf()
    /**
     * 选择企业logo
     */
    private fun choosePicture() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
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
                .forResult(UserInfoFragment.AVATAR)//结果回调onActivityResult code
    }
}