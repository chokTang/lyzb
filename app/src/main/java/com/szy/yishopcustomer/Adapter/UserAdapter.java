package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.User.UserModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.User.UserButtonViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.User.UserTopViewHolder;
import com.szy.yishopcustomer.ViewModel.User.ButtonModel;
import com.szy.yishopcustomer.ViewModel.User.TitleModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 17/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserAdapter extends RecyclerView.Adapter {
    public static final int TOP_VIEW = 0;
    public static final int TITLE_VIEW = 1;
    public static final int BUTTON_VIEW = 2;

    public View.OnClickListener onClickListener;

    public List<Object> data;

    public UserAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TOP_VIEW:
                return createTopViewHolder(parent);
            case BUTTON_VIEW:
                return createButtonViewHolder(parent);
            case TITLE_VIEW:
                return createTitleViewHolder(parent);
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
            case TITLE_VIEW:
                bindTitleViewHolder((UserTitleViewHolder) holder, position);
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
            return TITLE_VIEW;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindButtonViewHolder(UserButtonViewHolder holder, int position) {
        ButtonModel item = (ButtonModel) data.get(position);

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

    private void bindTitleViewHolder(UserTitleViewHolder holder, int position) {
        TitleModel item = (TitleModel) data.get(position);
        holder.titleTextView.setText(item.title);
        holder.subTitleTextView.setText(item.subTitle);
        if (item.editable) {
            holder.titleLogOff.setVisibility(View.INVISIBLE);
            holder.arrowImageView.setVisibility(View.VISIBLE);
            holder.iconImageView.setVisibility(View.VISIBLE);
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.subTitleTextView.setVisibility(View.VISIBLE);
            if (item.imageSource != 0) {
                holder.iconImageView.setVisibility(View.VISIBLE);
                holder.iconImageView.setImageResource(item.imageSource);
            } else {
                holder.iconImageView.setVisibility(View.GONE);
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

    private void bindTopViewHolder(final UserTopViewHolder holder, int position) {
        final UserModel item = (UserModel) data.get(position);
        if(!Utils.isNull(item.user_center_bgimage)){
            ImageLoader.displayImage(Utils.urlOfImage(item.user_center_bgimage,false), holder.mBackground);
        }else{
            holder.mBackground.setImageResource(R.mipmap.bg_user_top);
  /*          ImageLoader.getInstance().displayImage("drawable://" +  R.drawable.bg_user_top,
                    holder.mBackground);*/
        }
        if (!Utils.isNull(item.headimg)) {
            String url = Utils.urlOfImage(item.headimg,false);
            if(Utils.isWxHeadImg(url)) {
                ImageLoader.displayImage(item.headimg, holder.avatarImageView);
            } else {
                ImageLoader.displayImage(Utils.urlOfImage(item.headimg,false), holder.avatarImageView);
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
        String format = context.getString(R.string.formatUserMoney);
        if (Utils.isNull(item.user_money_limit)) {
            item.user_money_limit = "0";
        }
        if (Utils.isNull(item.user_money)) {
            item.user_money = "0";
        }
        BigDecimal aa = new BigDecimal(item.user_money);
        BigDecimal bb = new BigDecimal(item.user_money_limit);
        Double money = aa.add(bb).doubleValue();
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        String userMoney = df.format(money);

//        holder.userMoneyTextView.setText(String.format(format, userMoney));

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
                .fragment_user_title, parent, false);
        return new UserTitleViewHolder(view);
    }

    private RecyclerView.ViewHolder createTopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_top,
                parent, false);
        return new UserTopViewHolder(view);
    }
}
