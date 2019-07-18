package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by liuzhfieng on 2016/8/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsAttrDetailFragment extends LazyFragment {
    @BindView(R.id.fragment_goods_attr_listView)
    ListView mListView;
    @BindView(R.id.relativeLayout_empty)
    LinearLayout mEmptyLayout;
    @BindView(R.id.empty_view_titleTextView)
    TextView mEmptyTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_attr_detail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        List<Map<String, Object>> mAttrData = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 20; i++) {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("goods_parameter_name", "測試名字");
//            item.put("goods_parameter_value", "測試内容");
//            mAttrData.add(item);
//        }
        if(GoodsIndexFragment.mAttrData.size()==0){
            mEmptyLayout.setVisibility(View.VISIBLE);
            mEmptyTitle.setText(R.string.emptyGoodsAttr);
            mListView.setVisibility(View.GONE);
        }else{
            mEmptyLayout.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), GoodsIndexFragment.mAttrData, R
                    .layout.goods_parameter_item, new String[]{"goods_parameter_name",
                    "goods_parameter_value"}, new int[]{R.id.goods_parameter_name, R.id
                    .goods_parameter_value});
            mListView.setAdapter(adapter);
            mListView.setFocusable(false);

        }

        return view;
    }
}
