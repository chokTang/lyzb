package com.lyzb.jbx.activity;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.home.first.SendMessageFragment;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class SendMessageActivity extends BaseActivity {
    @Override
    public BaseFragment setRootFragment() {
        return new SendMessageFragment();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(R.anim.bottom_to_center, R.anim.center_to_bottom);
    }
}
