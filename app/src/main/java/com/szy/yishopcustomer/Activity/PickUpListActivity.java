package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.yishopcustomer.Fragment.PickUpListFragment;
import com.lyzb.jbx.R;

import butterknife.BindView;

public class PickUpListActivity extends YSCBaseActivity {

    @BindView(R.id.activity_goods_list_backImage)
    ImageButton activity_goods_list_backImage;
    @BindView(R.id.activity_goods_list_showType)
    TextView activity_goods_list_showType;
    @BindView(R.id.activity_goods_list_commonEditText)
    EditText activity_goods_list_commonEditText;

    PickUpListFragment pickUpListFragment;
    @Override
    public PickUpListFragment createFragment() {
        return pickUpListFragment = new PickUpListFragment();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_pickup_list ;
        super.onCreate(savedInstanceState);

        activity_goods_list_backImage.setOnClickListener(this);
        activity_goods_list_showType.setOnClickListener(this);

        activity_goods_list_commonEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String str = textView.getText().toString().trim();
                    pickUpListFragment.search(str);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.activity_goods_list_backImage:
                finish();
                break;
            case R.id.activity_goods_list_showType:
                String str = activity_goods_list_commonEditText.getText().toString().trim();
                pickUpListFragment.search(str);
                break;
        }
    }
}
