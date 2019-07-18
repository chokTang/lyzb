package com.lyzb.jbx.fragment.campaign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseStatusToolbarFragment;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.like.utilslib.image.BitmapUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.campaign.CampaignDetailMemeberAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.campagin.CampaginDetailModel;
import com.lyzb.jbx.model.common.WeiXinMinModel;
import com.lyzb.jbx.mvp.presenter.campaign.CampaignDetailPresenter;
import com.lyzb.jbx.mvp.view.campaign.IcampaignDetailView;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

/**
 * 活动详情页面
 *
 * @author longshao
 */
public class CampaignDetailFragment extends BaseStatusToolbarFragment<CampaignDetailPresenter> implements IcampaignDetailView, View.OnClickListener {

    private boolean isNeedRequest = false;//当前Loging页面属于第三方页面。所以无法进行操作

    private static final int RESULT_SIGN = 0x532;//报名活动
    private static final String PARAMS_ID = "campaignId";
    private String mCampaignId = "";

    private ConstraintLayout cons_card;
    private ImageView img_campaign_background;
    private TextView tv_campaign_title;
    private TextView tv_scan_number;
    private TextView tv_share_number;
    private TextView tv_follow_number;
    private TextView tv_campaign_time;
    private TextView tv_campaign_city;
    private TextView tv_campaign_number;
    private ImageView img_host_header;
    private TextView tv_host_name;
    private TextView tv_host_detail;
    private TextView tv_yes_follow;
    private TextView tv_no_follow;
    private TextView tv_describe_content;
    private TextView tv_part_number;
    private TextView tv_part_more;
    private RecyclerView recycle_part_user;
    private TextView tv_no_number;
    private TextView tv_fabu;
    private TextView tv_message;
    private TextView tv_follow;
    private TextView tv_campaign_status;

    private CampaignDetailMemeberAdapter memeberAdapter;
    private CampaginDetailModel mModel;

    public static CampaignDetailFragment newIntance(String campaignId) {
        CampaignDetailFragment fragment = new CampaignDetailFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, campaignId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCampaignId = args.getString(PARAMS_ID);
        }
    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_campaign_detail;
    }

    @Override
    public void onLazyRequest() {
        mPresenter.getCampaignDetail(mCampaignId);
    }

    @Override
    public void onAgainRequest() {
        mPresenter.getCampaignDetail(mCampaignId);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        setToolbarTitle("活动详情");
        onBack();
        mToolbar.inflateMenu(R.menu.share_union_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent shareIntent = new Intent(getContext(), ShareActivity.class);
                WeiXinMinModel model = new WeiXinMinModel();
                model.setLowVersionUrl(Config.BASE_URL);
                model.setTitle(mModel.getTitle());
                model.setDescribe(" ");
                model.setShareUrl("/pages/activityDetail/activityDetail?id=" + mCampaignId + "&gsName=" + mModel.getTitle()
                        + "&shareFromId=" + App.getInstance().userId + "&originUserId=" + mModel.getCreateMan());
                Bitmap bitmap = BitmapUtil.createViewBitmap(cons_card);
                bitmap = BitmapUtil.zoomMaxImage(bitmap, 500, 400);//分享的图片配置
                model.setImageUrl(BitmapUtil.bitmap2Bytes(bitmap));

                shareIntent.putExtra(ShareActivity.SHARE_DATA, model);
                shareIntent.putExtra(ShareActivity.SHARE_SCOPE, 2);
                shareIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_SOURCE);

                startActivity(shareIntent);
                return false;
            }
        });

        img_campaign_background = findViewById(R.id.img_campaign_background);
        tv_campaign_title = findViewById(R.id.tv_campaign_title);
        tv_scan_number = findViewById(R.id.tv_scan_number);
        tv_share_number = findViewById(R.id.tv_share_number);
        tv_follow_number = findViewById(R.id.tv_follow_number);
        tv_campaign_time = findViewById(R.id.tv_campaign_time);
        tv_campaign_city = findViewById(R.id.tv_campaign_city);
        tv_campaign_number = findViewById(R.id.tv_campaign_number);
        img_host_header = findViewById(R.id.img_host_header);
        tv_host_name = findViewById(R.id.tv_host_name);
        tv_host_detail = findViewById(R.id.tv_host_detail);
        tv_yes_follow = findViewById(R.id.tv_yes_follow);
        tv_no_follow = findViewById(R.id.tv_no_follow);
        tv_describe_content = findViewById(R.id.tv_describe_content);
        tv_part_number = findViewById(R.id.tv_part_number);
        tv_part_more = findViewById(R.id.tv_part_more);
        recycle_part_user = findViewById(R.id.recycle_part_user);
        tv_no_number = findViewById(R.id.tv_no_number);
        tv_fabu = findViewById(R.id.tv_fabu);
        tv_message = findViewById(R.id.tv_message);
        tv_follow = findViewById(R.id.tv_follow);
        tv_campaign_status = findViewById(R.id.tv_campaign_status);
        cons_card = findViewById(R.id.cons_card);

        tv_campaign_city.setOnClickListener(this);
        tv_no_follow.setOnClickListener(this);
        tv_yes_follow.setOnClickListener(this);
        tv_part_more.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        tv_campaign_status.setOnClickListener(this);
        img_host_header.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        int width = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(40)) / 5;
        memeberAdapter = new CampaignDetailMemeberAdapter(getContext(), width, ViewGroup.LayoutParams.WRAP_CONTENT, null);
        memeberAdapter.setLayoutManager(recycle_part_user, LinearLayoutManager.HORIZONTAL);
        memeberAdapter.setShowVip(true);
        recycle_part_user.setAdapter(memeberAdapter);

        recycle_part_user.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //跳转到个人名片页面
                start(CardFragment.newIntance(2, memeberAdapter.getPositionModel(position).getUserId()));
            }
        });
    }

    @Override
    public void onCampaignDetail(CampaginDetailModel model) {
        isNeedRequest = false;
        if (model == null) return;
        mModel = model;
        LoadImageUtil.loadImage(img_campaign_background, model.getActivityLogo());
        tv_campaign_title.setText(model.getTitle());
        tv_scan_number.setText(String.format("%d人浏览", model.getLookCount()));
        tv_share_number.setText(String.valueOf(model.getShareCount()));
        tv_follow_number.setText(String.valueOf(model.getCollectCount()));
        tv_campaign_time.setText(String.format("%s 至 %s",
                DateUtil.StringToString(model.getActivityStart(), DateStyle.MM_DD_HH_MM_CN),
                DateUtil.StringToString(model.getActivityEnd(), DateStyle.MM_DD_HH_MM_CN)));
        if (model.getAccess() == 1) {//线上活动
            tv_campaign_city.setText("线上活动");
            Drawable leftDrawable = ContextCompat.getDrawable(getContext(), R.drawable.union_address);
            tv_campaign_city.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
        } else {//线下活动
            tv_campaign_city.setText(model.getPlace());
        }
        tv_campaign_number.setText(String.format("已报名%d人", model.getPartCount()));
        LoadImageUtil.loadRoundSizeImage(img_host_header, model.getHeadimg(), 60);
        tv_host_name.setText(model.getUserName());
        tv_host_detail.setText(String.format("%d个活动 | %d个粉丝", model.getActivityCount(), model.getFansNum()));
        tv_describe_content.setText(Html.fromHtml(model.getContent()));
        tv_part_number.setText(String.format("报名（%d）", model.getPartCount()));
        if (model.getActivityParticipantList().size() == 0) {
            tv_part_number.setVisibility(View.GONE);
            tv_part_more.setVisibility(View.GONE);
            recycle_part_user.setVisibility(View.GONE);
            tv_no_number.setVisibility(View.VISIBLE);
            if (model.getActivitySatatus() == 3 | model.getActivitySatatus() == 1) {//如果活动已结束或者进行中
                tv_no_number.setText("该活动已截止报名，敬请期待下一个活动开始哦~~~");
            }
        } else {
            if (model.getActivityParticipantList().size() > 5) {
                memeberAdapter.update(model.getActivityParticipantList().subList(0, 5));
            } else {
                memeberAdapter.update(model.getActivityParticipantList());
            }
            tv_no_number.setVisibility(View.GONE);
            tv_part_number.setVisibility(View.VISIBLE);
            tv_part_more.setVisibility(View.VISIBLE);
            recycle_part_user.setVisibility(View.VISIBLE);
        }
        if (model.getActivitySatatus() == 3) {
            tv_campaign_status.setText("已结束");
            tv_campaign_status.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_red));
        } else {
            if (model.getParUserActivity() > 0) {
                tv_campaign_status.setText("已报名");
                tv_campaign_status.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_blue));
            } else {
                tv_campaign_status.setText(model.getActivitySatatusZh());
                tv_campaign_status.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_green));
            }
        }
        tv_follow.setSelected(model.isCollect());
        isConcern();
    }

    @Override
    public void onCollectionResultScuess() {
        tv_follow.setSelected(true);
    }

    @Override
    public void onDeleteCollectionResultScuess() {
        tv_follow.setSelected(false);
    }

    @Override
    public void onCardFollowSuccess() {
        mModel.setConcern(mModel.getConcern() > 0 ? 0 : 1);
        isConcern();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //关注发起者
            case R.id.tv_no_follow:
                if (App.getInstance().isLogin()) {
                    mPresenter.onFollowUser(mModel.getCreateMan(), 1);
                } else {
                    isNeedRequest = true;
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //取消关注
            case R.id.tv_yes_follow:
                if (App.getInstance().isLogin()) {
                    mPresenter.onFollowUser(mModel.getCreateMan(), 0);
                } else {
                    isNeedRequest = true;
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //跳转到地图
            case R.id.tv_campaign_city:
                //不是线上活动
                if (mModel.getAccess() != 0) {
//                    start(MapLocationFragment.newIntance(mModel.getPlace(), mModel.getActivityLat(), mModel.getActivityLng()));
                }
                break;
            //参与人-点击更多
            case R.id.tv_part_more:
                start(CampaignMemberListFragment.newIntance(mCampaignId));
                break;
            //发布
            case R.id.tv_fabu:
                showToast("该功能还未上线，敬请期待！");
                break;
            //在线咨询
            case R.id.tv_message:
                if (App.getInstance().isLogin()) {
                    Intent intent = new Intent(getContext(), ImCommonActivity.class);
                    ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                    model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                    model.setShopImName(mModel.getComAaccount());
                    model.setShopName(mModel.getUserName());
                    model.setShopHeadimg(mModel.getHeadimg());
                    model.setShopId("");

                    Bundle args = new Bundle();
                    args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                    intent.putExtras(args);
                    startActivity(intent);
                } else {
                    isNeedRequest = true;
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //收藏
            case R.id.tv_follow:
                if (App.getInstance().isLogin()) {
                    if (!tv_follow.isSelected()) {
                        mPresenter.onCollectionCampaign(mCampaignId);
                    } else {
                        mPresenter.onCancleCollectionCampaign(mCampaignId);
                    }
                } else {
                    isNeedRequest = true;
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //活动状态
            case R.id.tv_campaign_status:
                if (App.getInstance().isLogin()) {
                    //活动未开始并且没有报名
                    if (mModel.getActivitySatatus() == 2 && mModel.getParUserActivity() == 0) {
                        startForResult(SignCampaignFragment.newIntance(mCampaignId), RESULT_SIGN);
                    } else {
                        showToast("活动未开始或则您已经报名");
                    }
                } else {
                    isNeedRequest = true;
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //跳转到举办人的名片列表
            case R.id.img_host_header:
                start(CardFragment.newIntance(2, mModel.getCreateMan()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        switch (requestCode) {
            //报名
            case RESULT_SIGN:
                if (resultCode == RESULT_OK) {
                    mPresenter.getCampaignDetail(mCampaignId);
                }
                break;
            default:
                break;
        }
    }

    private void isConcern() {
        if (mModel.getConcern() > 0) {
            tv_no_follow.setVisibility(View.GONE);
            tv_yes_follow.setVisibility(View.VISIBLE);
        } else {
            tv_no_follow.setVisibility(View.VISIBLE);
            tv_yes_follow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isNeedRequest) {
            mPresenter.getCampaignDetail(mCampaignId);
        }
    }
}
