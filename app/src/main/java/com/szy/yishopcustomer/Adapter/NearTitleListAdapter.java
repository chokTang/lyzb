package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleAll;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleDistance;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2018 11:37
 */

public class NearTitleListAdapter extends BaseAdapter {

    private Context mContext;
    private boolean isAllData = true;

    private List<NearTitleAll> mList;
    private List<NearTitleDistance> mDistanceList;

    public NearTitleListAdapter(Context context) {
        this.mContext = context;
    }

    public void setChanageData(boolean chanageData) {
        this.isAllData = chanageData;
    }

    public void setAllList(List<NearTitleAll> titleAlls) {
        this.mList = titleAlls;
    }

    public void setNearList(List<NearTitleDistance> titleDistances) {
        this.mDistanceList = titleDistances;
    }

    @Override
    public int getCount() {

        if (isAllData) {
            return mList == null ? 0 : mList.size();
        } else {
            return mDistanceList == null ? 0 : mDistanceList.size();
        }
    }

    @Override
    public Object getItem(int position) {

        if (isAllData) {
            return mList.get(position);
        } else {
            return mDistanceList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_near_title, null);
            holder.mTextView_Name = (TextView) convertView.findViewById(R.id.tv_near_title_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (isAllData) {
            holder.mTextView_Name.setText(mList.get(position).catgName);

            holder.mTextView_Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClick != null) {
                        mItemClick.onAllClick(mList.get(position).catgName, mList.get(position).catgId);
                    }
                }
            });
        } else {
            holder.mTextView_Name.setText(mDistanceList.get(position).disContent);

            holder.mTextView_Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClick != null) {
                        mItemClick.onNearClick(mDistanceList.get(position).disContent, mDistanceList.get(position).disTance);
                    }
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        TextView mTextView_Name;
    }


    public interface onPopItemClick {
        void onAllClick(String titleName, int titleId);

        void onNearClick(String titleName, int distance);
    }

    private onPopItemClick mItemClick;

    public void setItemClick(onPopItemClick itemClick) {
        this.mItemClick = itemClick;
    }
}
