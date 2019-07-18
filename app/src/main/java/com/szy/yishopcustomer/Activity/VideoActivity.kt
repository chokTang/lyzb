package com.szy.yishopcustomer.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.like.LikeButton
import com.like.OnLikeListener
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Adapter.VideoShowProductAdapter
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.Api.API_CITY_HOME_MER_IMG_URL
import com.szy.yishopcustomer.Constant.Key

import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import com.xiao.nicevideoplayer.TxVideoPlayerController
import kotlinx.android.synthetic.main.activity_video_home.*

/**
 * Created by :TYK
 * Date: 2019/1/12  14:11
 * Desc:视频直播页面
 */

class VideoActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val KEY_VIDEO = "keyvideo"
        const val KEY_BUNDLE = "bundle"
    }

    var controller: TxCustomVideoPlayerController? = null
    var bean: HomeShopAndProductBean.LiveBean? = null
    var adater: VideoShowProductAdapter? = null
    var isShowMsg = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_home)
        initView()
        setListener()
    }

    /**
     * 初始化
     */
    private fun initView() {

        val intent = intent
        bean = intent.getBundleExtra(KEY_BUNDLE).getSerializable(KEY_VIDEO) as HomeShopAndProductBean.LiveBean?

        tv_title_name.text = bean!!.live_title
        tv_shop_name.text = bean!!.nick_name
        tv_people_num.text = bean!!.member_num.toString() + "人观看"
        Glide.with(this).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, bean!!.avatar_url))
                .apply(GlideUtil.EmptyOptions()).into(img_avatar)
        if (!TextUtils.isEmpty(bean!!.activity_img_url)){
            Glide.with(this).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, bean!!.activity_img_url))
                    .apply(GlideUtil.EmptyOptions()).into(img_ad)
        }

        nice_video_player.setPlayerType(NiceVideoPlayer.TYPE_NATIVE) // or NiceVideoPlayer.TYPE_NATIVE
        nice_video_player.setUp(bean!!.url_push, null)
        controller = TxCustomVideoPlayerController(this@VideoActivity)
        controller!!.setTitle("")
//        controller!!.imageView().background = resources.getDrawable(R.mipmap.bg_offline)
        Glide.with(this@VideoActivity).load(Api.API_CITY_HOME_MER_IMG_URL + bean!!.live_img).apply(GlideUtil.OptionsDefaultLogo()).into(controller!!.imageView())
        nice_video_player.setController(controller)
        nice_video_player.start()


        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adater = VideoShowProductAdapter(R.layout.item_video_show_product)
        rv_product.layoutManager = manager
        rv_product.adapter = adater
        adater!!.setNewData(bean!!.goods)
    }


    fun setListener() {
//        fl_is_show_msg.setOnClickListener(this)
        img_title_back.setOnClickListener(this)
        img_ad.setOnClickListener(this)

        star_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                LogUtils.e("红心")
            }

            override fun unLiked(p0: LikeButton?) {
                LogUtils.e("非红心")
            }
        })


        adater!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val intent = Intent()
            intent.putExtra(Key.KEY_GOODS_ID.value, bean!!.goods[position].goods_id.toString())
            intent.setClass(this, GoodsActivity::class.java)
            // 在onStop时释放掉播放器
            onStop()
            startActivity(intent)
        }

        controller!!.setDoVideoPlayer(object : TxCustomVideoPlayerController.DoVideoPlayer {
            override fun isPlay(isplay: Boolean, ishow: Boolean, iscontroller: Boolean) {
                if (isplay && !ishow) {
                    if (nice_video_player.isIdle) {
                        nice_video_player.start()
                    } else {
                        if (nice_video_player.isPlaying || nice_video_player.isBufferingPlaying) {
                            nice_video_player.pause()
                        } else if (nice_video_player.isPaused || nice_video_player.isBufferingPaused) {
                            nice_video_player.restart()
                        }
                    }
                }
                if (!isplay && ishow && !iscontroller) {
                    showView()
                }

            }

        })
    }

    override fun onStop() {
        super.onStop()
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return
        super.onBackPressed()
    }

    private fun showView() {
        if (isShowMsg) {
            rl_msg.visibility = View.VISIBLE
            rv_product.visibility = View.VISIBLE
            rl_title.visibility = View.VISIBLE
            star_button.visibility = View.VISIBLE
            img_ad.visibility = View.VISIBLE
            isShowMsg = false
        } else {
            rl_msg.visibility = View.GONE
            rv_product.visibility = View.GONE
            rl_title.visibility = View.GONE
            star_button.visibility = View.GONE
            img_ad.visibility = View.GONE
            isShowMsg = true
        }
    }


    override fun onClick(p0: View?) {
        when (p0) {
            fl_is_show_msg -> {//点击屏幕

            }
            img_title_back -> {//返回
                onStop()
                finish()
            }
            img_ad -> {//右上角广告
                var mIntent: Intent? = null
                if (!bean!!.activity_url.contains(Api.H5_CITYLIFE_URL)) {//同城生活
                    mIntent = Intent(this, ProjectH5Activity::class.java)
                    mIntent.putExtra(Key.KEY_URL.value, bean!!.activity_url)
                    startActivity(mIntent)
                } else {//零售
                    mIntent = Intent(this, YSCWebViewActivity::class.java)
                    mIntent.putExtra(Key.KEY_URL.value, bean!!.activity_url)
                    startActivity(mIntent)
                }
                onStop()
            }
        }
    }

}