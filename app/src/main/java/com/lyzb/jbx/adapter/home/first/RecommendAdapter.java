package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.account.FunctionAdapter;
import com.lyzb.jbx.adapter.circle.DynamicCircleListAdapter;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.account.FunctionModel;
import com.lyzb.jbx.model.common.RecommentBannerModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.follow.InterestMemberModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends DynamicCircleListAdapter {

    private final static int TYPE_HEADER_FIRST = 0x985;//banner图
    private final static int TYPE_HEADER_SECOND = 0x986;//可能感兴趣的人

    //bannaer数据
    private IRecycleAnyClickListener clickListener;
    private List<FunctionModel> functionModels = new ArrayList<>();
    private List<RecommentBannerModel> bannerList = new ArrayList<>();
    private Banner mBanner;

    //感性趣人数据
    private List<InterestMemberModel> interestList = new ArrayList<>();

    public RecommendAdapter(Context context, List<DynamicModel> list) {
        super(context, list);
        if (_list != null) {
            _list.add(0, new DynamicModel());
            _list.add(1, new DynamicModel());
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicModel item) {
        switch (holder.getItemViewType()) {
            //轮播
            case TYPE_HEADER_FIRST:
                BannerHolder bannerHolder = (BannerHolder) holder;
                mBanner = bannerHolder.bag_recommend;
                bannerHolder.bag_recommend.update(bannerList);
                bannerHolder.functionAdapter.update(functionModels);
                break;
            //可能赶兴趣的人
            case TYPE_HEADER_SECOND:
                UserHolder userHolder = (UserHolder) holder;
                userHolder.userAdapter.update(interestList);

                userHolder.addItemClickListener(R.id.tv_recommond_change);
                break;
            default:
                break;
        }
        super.convert(holder, item);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_HEADER_FIRST:
                return new BannerHolder(_context, getItemView(R.layout.recycle_recommend_header_frist, parent));
            case TYPE_HEADER_SECOND:
                return new UserHolder(_context, getItemView(R.layout.recycle_recommend_header_second, parent));
            default:
                break;
        }
        return super.onChildCreateViewHolder(viewType, parent);
    }

    @Override
    protected int getChildItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER_FIRST;
            case 1:
                return TYPE_HEADER_SECOND;
            default:
                break;
        }
        return super.getChildItemViewType(position);
    }

    public void noticeHeaderFirstData(List<RecommentBannerModel> bannerList, List<FunctionModel> models) {
        if (bannerList != null) {
            this.bannerList.clear();
            this.bannerList.addAll(bannerList);
        }
        if (models != null) {
            functionModels.clear();
            functionModels.addAll(models);
        }
        notifyItemChanged(0);
    }

    /**
     * 通知功能模块刷新
     *
     * @param models
     */
    public void noticeFunctionList(List<FunctionModel> models) {
        if (functionModels.size() == 0) {
            if (models != null) {
                functionModels.clear();
                functionModels.addAll(models);
            }
            notifyItemChanged(0);
        }
    }

    public void noticeHeaderSecondData(List<InterestMemberModel> value) {
        interestList = value;
        notifyItemChanged(1);
    }

    @Override
    public void update(List<DynamicModel> items) {
        _list.clear();
        _list.add(0, new DynamicModel());
        _list.add(1, new DynamicModel());
        if (items != null) {
            _list.addAll(items);
        }
        notifyDataSetChanged();
    }

    class BannerHolder extends BaseViewHolder {

        Banner bag_recommend;
        RecyclerView recycle_function;
        FunctionAdapter functionAdapter;

        public BannerHolder(final Context context, View itemView) {
            super(context, itemView);
            bag_recommend = itemView.findViewById(R.id.bag_recommend);
            recycle_function = itemView.findViewById(R.id.recycle_function);
            functionAdapter = new FunctionAdapter(context, null);
            functionAdapter.setLayoutManager(recycle_function, LinearLayoutManager.HORIZONTAL);
            recycle_function.setAdapter(functionAdapter);
            recycle_function.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    if (clickListener != null) {
                        clickListener.onItemClick(view, position, functionAdapter.getPositionModel(position));
                    }
                }
            });

            bag_recommend.setImageLoader(new ImageLoaderInterface() {
                @Override
                public void displayImage(Context context, Object path, View imageView) {
                    RecommentBannerModel model = (RecommentBannerModel) path;
                    LoadImageUtil.loadImage((ImageView) imageView, model.getImageUrl());
                }

                @Override
                public View createImageView(Context context) {
                    return null;
                }
            });
            bag_recommend.setIndicatorGravity(BannerConfig.CENTER);
            bag_recommend.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (clickListener != null) {
                        clickListener.onItemClick(null, position, bannerList.get(position));
                    }
                }
            });
            bag_recommend.start();
        }
    }

    class UserHolder extends BaseViewHolder {

        RecyclerView recycle_recommond_user;
        RecommendUserAdapter userAdapter;

        public UserHolder(Context context, View itemView) {
            super(context, itemView);
            recycle_recommond_user = itemView.findViewById(R.id.recycle_recommond_user);

            userAdapter = new RecommendUserAdapter(_context, null);
            recycle_recommond_user.setAdapter(userAdapter);
            userAdapter.setLayoutManager(recycle_recommond_user, LinearLayoutManager.HORIZONTAL);
            recycle_recommond_user.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    if (clickListener != null) {
                        clickListener.onItemClick(view, position, userAdapter.getPositionModel(position));
                    }
                }

                @Override
                public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                    if (clickListener!=null){
                        clickListener.onItemClick(view, position, userAdapter.getPositionModel(position));
                    }
                }
            });
        }
    }

    public void setClickListener(IRecycleAnyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Banner getmBanner() {
        return mBanner;
    }
}
