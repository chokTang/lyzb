package com.lyzb.jbx.fragment.home.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.dbdata.SearchHistroyDb;
import com.lyzb.jbx.model.search.HistroyModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 搜索结果下的历史搜索
 * Created by shidengzhong on 2019/3/4.
 */

public class SearchHistroyFragment extends BaseFragment {

    private TagFlowLayout layout_histroy;
    private List<HistroyModel> mList;
    private TextView tv_search_no_data;
    private HomeSearchFragment parentFargment;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        layout_histroy = (TagFlowLayout) findViewById(R.id.layout_histroy);
        tv_search_no_data = (TextView) findViewById(R.id.tv_search_no_data);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        parentFargment = (HomeSearchFragment) getParentFragment();

        mList = SearchHistroyDb.getIntance().getHistroyList();
        if (mList.size() <= 0) {
            tv_search_no_data.setVisibility(View.VISIBLE);
        } else {
            layout_histroy.setAdapter(new TagAdapter<HistroyModel>(mList) {
                @Override
                public View getView(FlowLayout parent, int position, HistroyModel o) {
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flowlayout_search_text,
                            layout_histroy, false);
                    tv.setText(o.getValue());
                    return tv;
                }
            });

            layout_histroy.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    HistroyModel model = mList.get(position);
                    model.setTime(System.currentTimeMillis());
                    SearchHistroyDb.getIntance().insert(model);
                    parentFargment.setEditContent(model.getValue());
                    parentFargment.onSearchData(model.getValue());
                    hideSoftInput();
                    return true;
                }
            });
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_search_history;
    }
}
