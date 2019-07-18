package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.CityNameModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.SortModel;
import com.szy.yishopcustomer.Util.PinyinComparator;
import com.szy.yishopcustomer.Util.PinyinUtils;
import com.szy.yishopcustomer.View.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 同城生活 城市列表 adapter
 * @time 2018 13:54
 */

public class SameCityListAdapter extends RecyclerView.Adapter {

    public Context mContext;

    private List<CityNameModel> mCityNameModelList = new ArrayList<>();

    public SameCityListAdapter(Context context) {
        this.mContext = context;
    }


    public void setCityNameList(List<CityNameModel> modelList) {
        this.mCityNameModelList = modelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatCityViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItemBanner((CityHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mCityNameModelList.size();
    }

    public RecyclerView.ViewHolder creatCityViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_list_layout, parent, false);
        CityHolder holder = new CityHolder(view);
        return holder;
    }

    public class CityHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_recy_city_list)
        CommonRecyclerView mRecyclerView_List;
        @BindView(R.id.item_city_dialog)
        TextView mTextView_Dialog;
        @BindView(R.id.item_sidebar)
        SideBar mSideBar;

        public CityHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<SortModel> SourceDateList;
    private PinyinComparator pinyinComparator;
    private CityListAdapter mListAdapter;
    private LinearLayoutManager manager;


    public void bindItemBanner(CityHolder holder, int position) {

        pinyinComparator = new PinyinComparator();
        holder.mSideBar.setTextView(holder.mTextView_Dialog);

        holder.mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });


        SourceDateList = filledData(mCityNameModelList);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.mRecyclerView_List.setLayoutManager(manager);

        mListAdapter = new CityListAdapter(mContext, SourceDateList);
        holder.mRecyclerView_List.setAdapter(mListAdapter);
    }


    public List<SortModel> filledData(List<CityNameModel> nameModels) {

        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < nameModels.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(nameModels.get(i).regionName);
            sortModel.setCode(nameModels.get(i).regionCode);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(nameModels.get(i).regionName);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private List<SortModel> mSortModelList;

    /***
     * 根据文本框输入内容  显示城市
     * @param filterStr
     */
    public void filterData(String filterStr) {
        List<SortModel> mSortList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            mSortList = mSortModelList;
        } else {

            mSortModelList.clear();
            for (SortModel sortModel : SourceDateList) {

                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString()) || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())) {
                    mSortModelList.add(sortModel);
                }
            }
        }
    }
}
