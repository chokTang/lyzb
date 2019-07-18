package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.card.CdMallAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.model.me.CardMallModel;
import com.lyzb.jbx.mvp.presenter.me.card.CdMallPresenter;
import com.lyzb.jbx.mvp.view.me.ICdMallView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我(TA)的名片-商城
 * @time 2019 2019/3/4 14:40
 */

public class CardMallFragment extends BaseFragment<CdMallPresenter> implements OnRefreshLoadMoreListener, ICdMallView {

    @BindView(R.id.lin_goods_empty)
    View emptyLin;
    @BindView(R.id.scorllview)
    NestedScrollView scorllview;
    @BindView(R.id.empty_tv)
    TextView emptyText;

    @BindView(R.id.lin_cd_mall_no_vip)
    LinearLayout noVipLin; //上次不是vip

    SmartRefreshLayout mRefreshLayout;
    RecyclerView mallRecy;

    private boolean isScore = false;

    @BindView(R.id.tv_un_hint)
    TextView openVipTitle;
    @BindView(R.id.un_cd_open_vip)
    TextView openVip;

    @BindView(R.id.lin_no_good_vip)
    LinearLayout noGoodsvipLin;
    @BindView(R.id.un_cd_good_open_vip)
    TextView vipText;

    @BindView(R.id.tv_un_me_card_add_good)
    TextView addGoodText;

    private CdMallAdapter mMallAdapter = null;
    private CardFragment parentFragment = null;


    @Override
    public Object getResId() {
        return R.layout.fragment_union_card_mall;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentFragment = (CardFragment) getParentFragment();
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        scoreView();

        mRefreshLayout = findViewById(R.id.sf_un_me_card_mall);
        mallRecy = findViewById(R.id.recy_un_me_card_mall);
        mallRecy.setNestedScrollingEnabled(false);
        mallRecy.setFocusable(false);

        if (parentFragment.getFromType() == 2) {//别人的名片
            noVipLin.setVisibility(View.GONE);
            noGoodsvipLin.setVisibility(View.GONE);
            addGoodText.setVisibility(View.GONE);
        } else {//自己的名片
            //判断当前企业是否是自己的企业
            if (App.getInstance().isMeComd) {//是自己的企业
                emptyLin.setVisibility(View.GONE);
                addGoodText.setVisibility(View.VISIBLE);
                addGoodText.setText("发布商品");
                if (!App.getInstance().isUserVip) {
                    noVipLin.setVisibility(View.VISIBLE);
                    openVipTitle.setText("您还未开通商城服务,商品暂时不能交易。");
                    openVip.setText("去开通>>");
                } else {
                    noVipLin.setVisibility(View.GONE);
                }
            } else {//不是管理员
                noVipLin.setVisibility(View.VISIBLE);
                openVipTitle.setText("创建企业可免费发布商品。");
                openVip.setText("立即创建>>");
                addGoodText.setVisibility(View.GONE);
            }
        }

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);


        addGoodText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getInstance().isLogin()){
                    if (addGoodText.getText().toString().trim().equals("发布商品")) {//发布商品
                        childStartForResult(new AddGoodFragment(), 1166);
                    } else {//看别人的名片 发布自己的商品
                        ((BaseFragment) getParentFragment()).start(CardFragment.myIntance(1,2,null));
                    }
                }else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

            }
        });


        openVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (App.getInstance().isMeComd) {
                    //开通服务
                    Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                    tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_DISCOUNT);
                    startActivity(tgIntent);
                } else {
//                    start(CompanySearchFragment.newTance(0));
//                    ((BaseFragment) getParentFragment()).start(CompanySearchFragment.newTance(2));
                }
            }
        });

        vipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开通服务
                Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_DISCOUNT);
                startActivity(tgIntent);
            }
        });

        mMallAdapter = new CdMallAdapter(getContext(), null);

        if (parentFragment.getFromType() == 1) {
            mMallAdapter.isMeGood = true;
        } else {
            mMallAdapter.isMeGood = false;
        }

        mMallAdapter.setGridLayoutManager(mallRecy, 2);
        mallRecy.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_winbg_10));

        mallRecy.setAdapter(mMallAdapter);

        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(true, App.getInstance().userId);
        } else {
            mPresenter.getList(true, parentFragment.getCardUserId());
        }


        mallRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(final BaseRecyleAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.img_un_me_card_good_img:
                    case R.id.tv_un_me_card_good_name:

                        CardMallModel.ListBean gd = (CardMallModel.ListBean) adapter.getPositionModel(position);

                        if (gd.getCan_buy() == 1) {//能买
                            //跳转商品详情页面
                            Intent mIntent = new Intent();
                            mIntent.setClass(getActivity(), GoodsActivity.class);
                            mIntent.putExtra(Key.KEY_GOODS_ID.getValue(), gd.getGoods_id());
                            mIntent.putExtra("card_user_id", parentFragment.mCardModel.getUserId());
                            startActivity(mIntent);
                        } else {
                            Intent pfIntent = new Intent(getContext(), YSCWebViewActivity.class);
                            pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.GOODS_URL + gd.getGoods_id()+"&card_user_id="+parentFragment.mCardModel.getUserId());
                            startActivity(pfIntent);
                        }
                        break;
                    case R.id.ll_pop:
                        if (parentFragment.getFromType() == 1) {
                            //删除商品
                            CardMallModel.ListBean good = (CardMallModel.ListBean) adapter.getPositionModel(position);
                            Map<String, Object> map = new HashMap<>();
                            map.put("userId", App.getInstance().userId);
                            map.put("type", "java");
                            map.put("ids", good.getGoods_id());

                            deleteHint(position, map);
                        }

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void scoreView(){
        parentFragment.getAppBar().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //当顶部view滑到最底部时候 这时候不管下面view  是否为0 都得允许 上面appbar滑动
                if (parentFragment.getLayout_view().getHeight() == -verticalOffset) {
                    isScore = true;
                } else {
                    isScore = false;
                }
                LogUtil.loge("view的高度" + parentFragment.getLayout_view().getHeight() + "偏移高度" + verticalOffset);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scorllview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    LogUtil.loge(oldScrollY + "里面的高度" + scrollY);
                    if (scrollY <= 0) {//当下面的fragment拉到顶部的时候 才允许上层的 拉动 0为顶部
                        parentFragment.banAppBarScroll(true);
                    } else {
                        if (isScore) {
                            parentFragment.banAppBarScroll(true);
                        } else {
                            parentFragment.banAppBarScroll(false);
                        }
                    }
                }
            });
        }
    }
    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    private void deleteHint(final int position, final Map<String, Object> map) {

        AlertDialogFragment.newIntance()
                .setKeyBackable(false)
                .setCancleable(false)
                .setContent("是否要删除该商品?")
                .setCancleBtn(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setSureBtn(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteGoods(position, map);
                    }
                })
                .show(getFragmentManager(), "");
    }

    @Override
    public void onDelete(int position) {
        mMallAdapter.remove(position);

        if (mMallAdapter.getItemCount() == 0) {
            noGoodsvipLin.setVisibility(View.VISIBLE);
            if (App.getInstance().isUserVip) {
                emptyLin.setVisibility(View.VISIBLE);
                emptyText.setText("亲,你还没有上传商品哦!");
            }
        } else {
            noGoodsvipLin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGoodList(boolean isRefresh, CardMallModel model) {

        if (isRefresh) {
            mMallAdapter.update(model.getList());
            mRefreshLayout.finishRefresh();
        } else {
            mMallAdapter.addAll(model.getList());

            if (model.getList().size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }

        if (mMallAdapter.getItemCount() == 0) {//没有商品
            noGoodsvipLin.setVisibility(View.VISIBLE);
            //mFromType 1:我的智能名片  2:TA的智能名片
            if (parentFragment.getFromType() == 1) {//自己的名片
                //判断当前企业是否是自己的企业
                if (App.getInstance().isMeComd) {//是自己的企业
                    addGoodText.setVisibility(View.VISIBLE);
                    addGoodText.setText("发布商品");
                    if (App.getInstance().isUserVip) {
                        emptyLin.setVisibility(View.VISIBLE);
                        noVipLin.setVisibility(View.GONE);
                        emptyText.setText("亲,你还没有上传商品哦!");
                    } else {
                        noVipLin.setVisibility(View.VISIBLE);
                        emptyLin.setVisibility(View.GONE);
                        openVipTitle.setText("您还未开通商城服务,商品暂时不能交易。");
                        openVip.setText("去开通>>");
                    }
                } else {//自己不是企业管理员
                    noVipLin.setVisibility(View.VISIBLE);
                    emptyLin.setVisibility(View.VISIBLE);
                    openVipTitle.setText("创建企业可免费发布商品。");
                    openVip.setText("立即创建>>");
                    noGoodsvipLin.setVisibility(View.GONE);
                    addGoodText.setVisibility(View.GONE);
//                    noGoodsvipLin.setVisibility(View.VISIBLE);
//                    addGoodText.setVisibility(View.VISIBLE);
                    emptyText.setText("您当前所在企业尚未发布商品");
                }

            } else {//别人的名片
                emptyLin.setVisibility(View.VISIBLE);
                addGoodText.setVisibility(View.GONE);

//                if (App.getInstance().isMeComd){//是不是自己的商店
//                    addGoodText.setVisibility(View.VISIBLE);
//                    addGoodText.setText("免费发布我的商品");
//                }else {
//                    addGoodText.setVisibility(View.GONE);
//                }
            }
        } else {//有商品
            noGoodsvipLin.setVisibility(View.GONE);
            emptyLin.setVisibility(View.GONE);
            //mFromType 1:我的智能名片  2:TA的智能名片
            if (parentFragment.getFromType() == 1) {
                //判断当前企业是否是自己的企业
                if (App.getInstance().isMeComd) {//是自己的企业
                    addGoodText.setVisibility(View.VISIBLE);
                    if (App.getInstance().isUserVip) {
                        noVipLin.setVisibility(View.GONE);
                        emptyText.setText("亲,你还没有上传商品哦!");
                    } else {
                        noVipLin.setVisibility(View.VISIBLE);
                        openVipTitle.setText("您还未开通商城服务,商品暂时不能交易。");
                        openVip.setText("去开通>>");
                    }
                } else {//自己不是企业管理员
                    noVipLin.setVisibility(View.VISIBLE);
                    noGoodsvipLin.setVisibility(View.GONE);
                    openVipTitle.setText("创建企业可免费发布商品。");
                    openVip.setText("立即创建>>");
                    addGoodText.setVisibility(View.GONE);
                    emptyText.setText("您当前所在企业尚未发布商品");

                }

            }
        }
    }


    @Override
    public void onFinshOrLoadMore(boolean isRefresh) {
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }

        if (parentFragment.getFromType() == 1) {

            if (mMallAdapter.getItemCount() == 0) {
                if (!App.getInstance().isUserVip) {
                    noGoodsvipLin.setVisibility(View.VISIBLE);
                }
            } else {
                noGoodsvipLin.setVisibility(View.GONE);
                mallRecy.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(false, App.getInstance().userId);
        } else {
            mPresenter.getList(false, parentFragment.getCardUserId());
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(true, App.getInstance().userId);
        } else {
            mPresenter.getList(true, parentFragment.getCardUserId());
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1166) {

                if (parentFragment.getFromType() == 1) {
                    mPresenter.getList(true, App.getInstance().userId);
                } else {
                    mPresenter.getList(true, parentFragment.getCardUserId());
                }
            }
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(true, App.getInstance().userId);
        } else {
            mPresenter.getList(true, parentFragment.getCardUserId());
        }
    }
}
