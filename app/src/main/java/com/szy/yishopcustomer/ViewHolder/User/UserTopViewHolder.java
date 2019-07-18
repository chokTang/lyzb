package com.szy.yishopcustomer.ViewHolder.User;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserTopViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_user_top_avatarCircleImageView)
    public CircleImageView avatarImageView;
    @BindView(R.id.fragment_user_top_background)
    public ImageView mBackground;
    @BindView(R.id.fragment_user_top_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_user_top_badgeTextView)
    public TextView badgeTextView;
    @BindView(R.id.fragment_user_top_rankTextView)
    public TextView rankTextView;


    @BindView(R.id.fragment_user_top_messageImageButton)
    public ImageButton messageImageButton;
    @BindView(R.id.fragment_user_top_settingImageButton)
    public ImageButton settingImageButton;
    @BindView(R.id.fragment_user_top_messageView)
    public View messageView;

    @BindView(R.id.linearlayoutPaymentCode)
    public View linearlayoutPaymentCode;

//    @BindView(R.id.fragment_user_top_balanceWrapperRelativeLayout)
//    public RelativeLayout balanceWrapper;
//    @BindView(R.id.fragment_user_top_bonusWrapperRelativeLayout)
//    public RelativeLayout bonusWrapper;
//@BindView(R.id.fragment_user_top_userMoneyTextView)
//public TextView userMoneyTextView;
//@BindView(R.id.fragment_user_top_bonusTextView)
//public TextView bonusTextView;

    public UserTopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
