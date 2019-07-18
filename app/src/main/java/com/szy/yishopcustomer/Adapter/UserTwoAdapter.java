package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnUserIngotNumberViewListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.User.UserModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsColumnViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserButtonViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserGuessTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserMyAssetsViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserTitleTbViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserTopViewHolder;
import com.szy.yishopcustomer.ViewModel.User.ButtonModel;
import com.szy.yishopcustomer.ViewModel.User.GuessTitleModel;
import com.szy.yishopcustomer.ViewModel.User.LineModel;
import com.szy.yishopcustomer.ViewModel.User.MyAssetsModel;
import com.szy.yishopcustomer.ViewModel.User.TitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 18/1/4.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserTwoAdapter extends RecyclerView.Adapter {

    public static final int TOP_VIEW = 0;
    public static final int TITLE_ITEM_VIEW = 1;
    public static final int BUTTON_VIEW = 2;
    public static final int TITLE_VIEW = 3;
    //分割线
    public static final int LINE_VIEW = 4;
    //我的资产
    private static final int MY_ASSETS_VIEW = 5;

    //猜你喜欢
    private static final int GUESS_LIKE = 6;

    //猜你喜欢title
    private static final int GUESS_LIKE_TITLE = 7;

    public View.OnClickListener onClickListener;

    public String topBg;
    private ImageView mImageView;
    IndexGuessLikeAdapter adapter;
    public List<GuessGoodsModel> mGoodsModelList;
    public List<Object> data;

    public int guessPosition = 0;

    public UserTwoAdapter() {
        this.data = new ArrayList<>();
        this.mGoodsModelList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TOP_VIEW:
                return createTopViewHolder(parent);
            case BUTTON_VIEW:
                return createButtonViewHolder(parent);
            case TITLE_ITEM_VIEW:
                return createTitleItemViewHolder(parent);
            case TITLE_VIEW://title  左边字 右边箭头
                return createTitleViewHolder(parent);
            case LINE_VIEW:
                return createLineViewHolder(parent);
            case MY_ASSETS_VIEW:
                return createMyAssetsViewHolder(parent);
            case GUESS_LIKE:
                return createGuessLikdeViewHolder(parent);
            case GUESS_LIKE_TITLE:
                return createGuessLikeTitleViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TOP_VIEW:
                bindTopViewHolder((UserTopViewHolder) holder, position);
                break;
            case BUTTON_VIEW:
                bindButtonViewHolder((UserButtonViewHolder) holder, position);
                break;
            case TITLE_ITEM_VIEW:
                bindTitleTbViewHolder((UserTitleTbViewHolder) holder, position);
                break;
            case TITLE_VIEW:
                bindTitleViewHolder((UserTitleViewHolder) holder, position);
                break;
            case LINE_VIEW:
                bindLineViewHolder((DividerViewHolder) holder, position);
                break;
            case MY_ASSETS_VIEW:
                bindMyAssetsViewHolder((UserMyAssetsViewHolder) holder, position);
                break;
            case GUESS_LIKE:
                setGuessLikeViewHolder((GoodsColumnViewHolder) holder, position);
                break;
            case GUESS_LIKE_TITLE:
                setGuessLikeTitleViewHolder((UserGuessTitleViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof UserModel) {
            return TOP_VIEW;
        } else if (object instanceof ButtonModel) {
            return BUTTON_VIEW;
        } else if (object instanceof TitleModel) {
            TitleModel titleModel = (TitleModel) object;
            if (!titleModel.editable || titleModel.isTitle) {
                return TITLE_VIEW;
            }
            return TITLE_ITEM_VIEW;
        } else if (object instanceof LineModel) {
            return LINE_VIEW;
        } else if (object instanceof MyAssetsModel) {
            return MY_ASSETS_VIEW;
        } else if (object instanceof GuessGoodsModel) {
            return GUESS_LIKE;
        } else if (object instanceof GuessTitleModel) {
            return GUESS_LIKE_TITLE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindButtonViewHolder(UserButtonViewHolder holder, int position) {
        ButtonModel item = (ButtonModel) data.get(position);

        switch (item.title) {
            case "待付款"://待付款  第一个左边是左下角有圆角
                holder.view_left.setVisibility(View.VISIBLE);
                holder.view_right.setVisibility(View.GONE);
                holder.rl_user_button_bg.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_radio_5bottom_left));
                break;
            case "退款/售后"://退款/售后 最后一个是右下角有圆角
                holder.view_left.setVisibility(View.GONE);
                holder.view_right.setVisibility(View.VISIBLE);
                holder.rl_user_button_bg.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_radio_5bottom_right));
                break;
            default:
                holder.view_left.setVisibility(View.GONE);
                holder.view_right.setVisibility(View.GONE);
                holder.rl_user_button_bg.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.white));
        }


        holder.titleTextView.setText(item.title);
        if (item.badgeNumber.equals("") || item.badgeNumber.equals("0")) {
            holder.numberTextView.setVisibility(View.INVISIBLE);
        } else {
            holder.numberTextView.setVisibility(View.VISIBLE);
        }
        if (item.badgeNumber.length() == 3) {
            holder.numberTextView.setText("99+");
            holder.numberTextView.setTextSize(6);

        } else {
            holder.numberTextView.setText(item.badgeNumber);
            holder.numberTextView.setTextSize(10);
        }


        holder.iconImageView.setImageResource(item.imageResource);

        Utils.setViewTypeIntegerForTag(holder.itemView, item.extraInfo);
        holder.itemView.setOnClickListener(onClickListener);
    }

    private void bindLineViewHolder(DividerViewHolder holder, int position) {
        LineModel item = (LineModel) data.get(position);

        Context context = holder.itemView.getContext();

        holder.view_line.setBackgroundResource(item.getResourceId());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.view_line.getLayoutParams();
        layoutParams.height = Utils.dpToPx(holder.itemView.getContext(), item.getHeightDp());
        layoutParams.setMargins(Utils.dpToPx(context, item.getLrPadding()), 0, Utils.dpToPx(context, item.getLrPadding()), 0);
    }

    private void bindTitleViewHolder(UserTitleViewHolder holder, int position) {
        TitleModel item = (TitleModel) data.get(position);
        holder.titleTextView.setText(item.title);
        holder.subTitleTextView.setText(item.subTitle);
        if (item.editable) {//不显示退出登录
            holder.rl_bg_title.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_top_radio_5_white));
            holder.titleLogOff.setVisibility(View.INVISIBLE);
            holder.iconImageView.setVisibility(View.VISIBLE);
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.subTitleTextView.setVisibility(View.VISIBLE);
            if (item.imageSource != 0) {
                holder.iconImageView.setVisibility(View.VISIBLE);
                holder.iconImageView.setImageResource(item.imageSource);
            } else {
                holder.iconImageView.setVisibility(View.GONE);
            }

            if (item.extraInfo == 0) {
                holder.ll_bg_title.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorTen));
                holder.arrowImageView.setVisibility(View.INVISIBLE);
            } else {
                holder.ll_bg_title.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.bg_user_top_view));
                holder.arrowImageView.setVisibility(View.VISIBLE);
            }
        } else {//显示退出登录
            holder.ll_bg_title.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorTen));
            holder.titleLogOff.setVisibility(View.VISIBLE);
            holder.rl_bg_title.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_radio_5_white));
            holder.arrowImageView.setVisibility(View.INVISIBLE);
            holder.iconImageView.setVisibility(View.INVISIBLE);
            holder.titleTextView.setVisibility(View.INVISIBLE);
            holder.subTitleTextView.setVisibility(View.INVISIBLE);
        }

        Utils.setViewTypeIntegerForTag(holder.itemView, item.extraInfo);
        holder.itemView.setOnClickListener(onClickListener);
    }

    private void bindTitleTbViewHolder(UserTitleTbViewHolder holder, int position) {
        TitleModel item = (TitleModel) data.get(position);

        switch (item.title) {
            case "我的收藏":
                holder.view_title_left.setVisibility(View.VISIBLE);
                holder.view_title_right.setVisibility(View.GONE);
                break;
            case "账户安全":
                holder.view_title_left.setVisibility(View.VISIBLE);
                holder.view_title_right.setVisibility(View.GONE);
                holder.ll_title_tb_bg.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_radio_5bottom_left));
                break;
            case "个人资料":
                holder.view_title_left.setVisibility(View.GONE);
                holder.view_title_right.setVisibility(View.VISIBLE);
                break;
            case "":
                holder.view_title_left.setVisibility(View.GONE);
                holder.view_title_right.setVisibility(View.VISIBLE);
                holder.ll_title_tb_bg.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_radio_5bottom_right));
                break;

            default:
                holder.view_title_left.setVisibility(View.GONE);
                holder.view_title_right.setVisibility(View.GONE);
                holder.ll_title_tb_bg.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.white));
        }

        holder.titleTextView.setText(item.title);
        holder.subTitleTextView.setText(item.subTitle);
        if (item.editable) {
            holder.titleLogOff.setVisibility(View.INVISIBLE);
            holder.iconImageView.setVisibility(View.VISIBLE);
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.subTitleTextView.setVisibility(View.VISIBLE);
            if (item.imageSource != 0) {
                holder.iconImageView.setVisibility(View.VISIBLE);
                holder.iconImageView.setImageResource(item.imageSource);
            } else {
                if (item.title.equals("")) {
                    holder.iconImageView.setVisibility(View.VISIBLE);
                } else {
                    holder.iconImageView.setVisibility(View.GONE);
                }
            }
            if (item.extraInfo == 0) {
                holder.arrowImageView.setVisibility(View.INVISIBLE);
            } else {
                holder.arrowImageView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.titleLogOff.setVisibility(View.VISIBLE);
            holder.arrowImageView.setVisibility(View.INVISIBLE);
            holder.iconImageView.setVisibility(View.INVISIBLE);
            holder.titleTextView.setVisibility(View.INVISIBLE);
            holder.subTitleTextView.setVisibility(View.INVISIBLE);
        }

        Utils.setViewTypeIntegerForTag(holder.itemView, item.extraInfo);
        holder.itemView.setOnClickListener(onClickListener);
    }

    //我的资产
    private void bindMyAssetsViewHolder(final UserMyAssetsViewHolder holder, int position) {
        final MyAssetsModel item = (MyAssetsModel) data.get(position);
//        if (!Utils.isNull(topBg)) {//用后台的图片
//            Glide.with(holder.itemView.getContext()).load(Utils.urlOfImage(topBg, false))
//                    .into(new ViewTarget<View, Drawable>(holder.ll_bg_my_assets) {
//                        @Override
//                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                            this.view.setBackground(resource.getCurrent());
//                        }
//                    });
//        }
        //使用固定颜色背景
        holder.ll_bg_my_assets.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.bg_user_top_view));
        if (holder.linearlayoutPaymentCode != null) {
            Utils.setViewTypeIntegerForTag(holder.linearlayoutPaymentCode, ViewType.VIEW_TYPE_TOOL_PAYMENT.ordinal());
            holder.linearlayoutPaymentCode.setOnClickListener(onClickListener);
        }

        if (App.getInstance().user_ingot_number != null) {
            /**元宝*/
            holder.textViewUserIntegral.setText(App.getInstance().user_ingot_number);
        }
        /**金额*/
        holder.textViewUserBalance.setText(App.getInstance().user_money_format);

        holder.textViewRedPacket.setText(item.bonusCount);

        if (onUserIngotNumberViewListener != null) {
            onUserIngotNumberViewListener.getIngotView(holder.textViewUserIntegral);
        }

        //个人中心-我的元宝 点击事件 type
        Utils.setViewTypeIntegerForTag(holder.linearlayoutUserIntegral, ViewType.VIEW_TYPE_USER_INTEGRAL.ordinal());
        //红包
        Utils.setViewTypeIntegerForTag(holder.linearlayoutRedPacket, ViewType.VIEW_TYPE_BONUS.ordinal());
        //余额
        Utils.setViewTypeIntegerForTag(holder.linearlayoutUserBalance, ViewType.VIEW_TYPE_BALANCE.ordinal());


        holder.linearlayoutRedPacket.setOnClickListener(onClickListener);
        holder.linearlayoutUserIntegral.setOnClickListener(onClickListener);
        holder.linearlayoutUserBalance.setOnClickListener(onClickListener);

    }

    private void setGuessLikeTitleViewHolder(UserGuessTitleViewHolder viewHolder, final int position) {
        viewHolder.img_title.setBackgroundResource(R.mipmap.bg_guess_like);
    }

    private void setGuessLikeViewHolder(GoodsColumnViewHolder viewHolder, final int position) {
//        final GuessGoodsModel item = (GuessGoodsModel) data.get(position);
        LogUtils.Companion.e("猜你喜欢的position为" + position);
        mImageView = viewHolder.mImageView_Nodata;
//        IndexGuessLikeAdapter adapter = new IndexGuessLikeAdapter(context);
        if (adapter == null) {
            adapter = new IndexGuessLikeAdapter(viewHolder.itemView.getContext());
            viewHolder.recyclerView.setAdapter(adapter);
        }
        adapter.itemPosition = position;
        adapter.onClickListener = onClickListener;

        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setNestedScrollingEnabled(false);
        adapter.data = mGoodsModelList;
        adapter.notifyDataSetChanged();
    }

    private void bindTopViewHolder(final UserTopViewHolder holder, int position) {
        final UserModel item = (UserModel) data.get(position);

//        //使用后台的图片
//        if (!Utils.isNull(item.user_center_bgimage)) {
//            topBg = item.user_center_bgimage;
//            ImageLoader.displayImage(Utils.urlOfImage(item.user_center_bgimage, false), holder.mBackground);
//        } else {
//            holder.mBackground.setImageResource(R.mipmap.bg_user_top);
//  /*          ImageLoader.getInstance().displayImage("drawable://" +  R.drawable.bg_user_top,
//                    holder.mBackground);*/
//        }
        holder.mBackground.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.bg_user_top_view));
        if (!Utils.isNull(item.headimg)) {
            String url = Utils.urlOfImage(item.headimg, false);
            if (Utils.isWxHeadImg(url)) {
                ImageLoader.displayImage(item.headimg, holder.avatarImageView);
            } else {
                ImageLoader.displayImage(Utils.urlOfImage(item.headimg, false), holder.avatarImageView);
            }
//            ImageLoader.getInstance().displayImage(url, holder.avatarImageView);
            holder.avatarImageView.setAlpha(1f);
        } else {
            holder.avatarImageView.setImageResource(R.mipmap.pl_user_avatar);
            holder.avatarImageView.setAlpha(0.3f);
        }

        if (!Utils.isNull(item.no_read_count) && item.no_read_count > 0) {
            holder.badgeTextView.setVisibility(View.VISIBLE);
        } else {
            holder.badgeTextView.setVisibility(View.GONE);
        }

        if (App.getInstance().isLogin()) {
            if (Utils.isNull(item.nickname)) {
                holder.nameTextView.setText(item.user_name);
            } else {
                holder.nameTextView.setText(item.nickname);
            }
        } else {
            holder.nameTextView.setText(R.string.clickToLogin);
        }

        if (!Utils.isNull(item.rankName)) {
            holder.rankTextView.setVisibility(View.VISIBLE);
            holder.rankTextView.setText(item.rankName);
        } else {
            holder.rankTextView.setVisibility(View.INVISIBLE);
        }

//        holder.bonusTextView.setText(item.bonusCount);
        Context context = holder.itemView.getContext();

        if (Utils.isNull(item.user_money_limit)) {
            item.user_money_limit = "0";
        }
        if (Utils.isNull(item.user_money)) {
            item.user_money = "0";
        }

//        holder.userMoneyTextView.setText(item.money_all_format);

        Utils.setViewTypeIntegerForTag(holder.messageImageButton, ViewType.VIEW_TYPE_MESSAGE
                .ordinal());
        Utils.setViewTypeIntegerForTag(holder.settingImageButton, ViewType.VIEW_TYPE_SETTING
                .ordinal());

        if (App.getInstance().isLogin()) {
            Utils.setViewTypeIntegerForTag(holder.avatarImageView, ViewType.VIEW_TYPE_AVATAR
                    .ordinal());
            Utils.setViewTypeIntegerForTag(holder.nameTextView, ViewType.VIEW_TYPE_NAME.ordinal());
        } else {
            Utils.setViewTypeIntegerForTag(holder.avatarImageView, ViewType.VIEW_TYPE_LOGIN
                    .ordinal());
            Utils.setViewTypeIntegerForTag(holder.nameTextView, ViewType.VIEW_TYPE_LOGIN.ordinal());
        }

        Utils.setViewTypeIntegerForTag(holder.linearlayoutPaymentCode, ViewType.VIEW_TYPE_TOOL_PAYMENT.ordinal());
        holder.linearlayoutPaymentCode.setOnClickListener(onClickListener);


//        Utils.setViewTypeIntegerForTag(holder.balanceWrapper, ViewType.VIEW_TYPE_BALANCE.ordinal());
//        Utils.setViewTypeIntegerForTag(holder.bonusWrapper, ViewType.VIEW_TYPE_BONUS.ordinal());

        holder.messageImageButton.setOnClickListener(onClickListener);
        holder.settingImageButton.setOnClickListener(onClickListener);
        holder.avatarImageView.setOnClickListener(onClickListener);
        holder.nameTextView.setOnClickListener(onClickListener);
//        holder.balanceWrapper.setOnClickListener(onClickListener);
//        holder.bonusWrapper.setOnClickListener(onClickListener);
    }

    private RecyclerView.ViewHolder createButtonViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_user_button, parent, false);
        return new UserButtonViewHolder(view);
    }

    private RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_user_title_two, parent, false);
        return new UserTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createLineViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_user_line, parent, false);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createTitleItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_user_title_item, parent, false);
        return new UserTitleTbViewHolder(view);
    }

    private RecyclerView.ViewHolder createTopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_top_two,
                parent, false);
        return new UserTopViewHolder(view);
    }

    private RecyclerView.ViewHolder createMyAssetsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_user_my_assets, parent, false);
        return new UserMyAssetsViewHolder(view);
    }


    private RecyclerView.ViewHolder createGuessLikeTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guess_title, parent, false);
        return new UserGuessTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createGuessLikdeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_index_goods_column, parent, false);
        return new GoodsColumnViewHolder(view);
    }

    OnUserIngotNumberViewListener onUserIngotNumberViewListener;

    public void setUserIngotNumber(OnUserIngotNumberViewListener onUserIngotNumberViewListener) {
        this.onUserIngotNumberViewListener = onUserIngotNumberViewListener;
    }
}
