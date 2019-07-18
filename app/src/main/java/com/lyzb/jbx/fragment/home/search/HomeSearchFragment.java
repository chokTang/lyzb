package com.lyzb.jbx.fragment.home.search;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.lyzb.jbx.R;
import com.lyzb.jbx.dbdata.SearchHistroyDb;
import com.lyzb.jbx.fragment.circle.SearchCircleFragment;
import com.lyzb.jbx.fragment.dynamic.DynamicListFragment;
import com.lyzb.jbx.model.search.HistroyModel;

/**
 * 首页-搜索功能
 * Created by shidengzhong on 2019/3/4.
 */

public class HomeSearchFragment extends BaseFragment implements View.OnClickListener {

    @IntDef({DYNAMIC_VIEW, GOODS_VIEW, ACCOUNT_VIEW, CIRCLE_VIEW})
    public @interface ShowView {
    }

    public static final int DYNAMIC_VIEW = 2;
    public static final int GOODS_VIEW = 5;
    public static final int ACCOUNT_VIEW = 3;
    public static final int CIRCLE_VIEW = 4;

    private static final String PARAMS_VALUE = "params_value";
    private static final String PARAMS_SHOW = "params_show";
    private String mSearchValue = "";//搜索内容
    private int mCurrentIndex = 0;//当前显示的fragment的位置

    private ImageView img_home_sreach_back;
    private ClearEditText edt_search_value;
    private ImageView img_search;

    private BaseFragment[] fragements;
    private SearchValueFragment valueFragment;
    private DynamicListFragment dynamicFragment;//动态
    private SearchAccountDynamicFragment accountFragment;//用户
    private SearchCircleFragment circleFragment;//圈子
    private SearchGoodsFragment goodsFragment;//商品

    public static HomeSearchFragment newIntance(String searchValue, @ShowView int index) {
        HomeSearchFragment fragment = new HomeSearchFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_VALUE, searchValue);
        args.putInt(PARAMS_SHOW, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchValue = args.getString(PARAMS_VALUE);
            mCurrentIndex = args.getInt(PARAMS_SHOW, 0);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        img_home_sreach_back = (ImageView) findViewById(R.id.img_home_sreach_back);
        edt_search_value = (ClearEditText) findViewById(R.id.edt_search_value);
        img_search = (ImageView) findViewById(R.id.img_search);

        img_home_sreach_back.setOnClickListener(this);
        img_search.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        valueFragment = SearchValueFragment.newIntance(mSearchValue);
        dynamicFragment = DynamicListFragment.newIntance(mSearchValue);
        accountFragment = SearchAccountDynamicFragment.newIntance(mSearchValue);
        circleFragment = SearchCircleFragment.newIntance(mSearchValue);
        goodsFragment = SearchGoodsFragment.newIntance(mSearchValue);

        edt_search_value.setText(mSearchValue);
        if (!TextUtils.isEmpty(mSearchValue)) {
            edt_search_value.setSelection(mSearchValue.length());
        }

        fragements = new BaseFragment[]{new SearchHistroyFragment(), valueFragment, dynamicFragment, accountFragment,
                circleFragment, goodsFragment};
        loadMultipleRootFragment(R.id.fragment_search_result, mCurrentIndex, fragements);

        edt_search_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String value = edt_search_value.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        showToast("请输入内容");
                        return true;
                    }
                    SearchHistroyDb.getIntance().insert(new HistroyModel(value));
                    searachGoods(value);
                }
                return true;
            }
        });

        edt_search_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onSearchData(s.toString(), false);
            }
        });

        edt_search_value.post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(mSearchValue)) {
                    showSoftInput(edt_search_value);
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_home_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_home_sreach_back:
                pop();
                break;
            //搜索
            case R.id.img_search:
                String value = edt_search_value.getText().toString().trim();
                if (TextUtils.isEmpty(value)) {
                    showToast("请输入内容");
                    return;
                }
                SearchHistroyDb.getIntance().insert(new HistroyModel(value));
                searachGoods(value);
                break;
            default:
                break;
        }
    }

    /**
     * 执行搜索
     *
     * @param value
     * @param isSave 是否保存到数据库
     */
    private void onSearchData(String value, boolean isSave) {
        if (!TextUtils.isEmpty(value) && isSave) {
            SearchHistroyDb.getIntance().insert(new HistroyModel(value));
        }
        if (mCurrentIndex == 1) {
            valueFragment.onDataNoticeChange(value);
        } else {
            showHideFragment(valueFragment, fragements[mCurrentIndex]);
            valueFragment.onDataNoticeChange(value);
            mCurrentIndex = 1;
        }
    }

    public void onSearchData(String value) {
        onSearchData(value, true);
    }

    public void startFragment(int position, String value) {
        hideSoftInput();
        showHideFragment(fragements[position], fragements[mCurrentIndex]);
        switch (position) {
            //动态
            case 2:
                dynamicFragment.notifySeacrhValue(value);
                break;
            //用户
            case 3:
                accountFragment.notifySeacrhValue(value);
                break;
            //圈子
            case 4:
                circleFragment.notifySeacrhValue(value);
                break;
            //商品列表
            case 5:
                setEditContent(value);
                goodsFragment.notifySeacrhValue(value);
                break;
        }
        mCurrentIndex = position;
    }

    /**
     * 设置文本内容
     *
     * @param value
     */
    public void setEditContent(String value) {
        if (TextUtils.isEmpty(value)) {
            edt_search_value.setText("");
        } else {
            edt_search_value.setText(value);
            edt_search_value.setSelection(value.length());
        }
    }

    /**
     * 直接搜索商品
     *
     * @param value
     */
    private void searachGoods(String value) {
        hideSoftInput();
        if (mCurrentIndex == 5) {
            goodsFragment.notifySeacrhValue(value);
        } else {
            showHideFragment(goodsFragment, fragements[mCurrentIndex]);
            goodsFragment.notifySeacrhValue(value);
            mCurrentIndex = 5;
        }
    }
}
