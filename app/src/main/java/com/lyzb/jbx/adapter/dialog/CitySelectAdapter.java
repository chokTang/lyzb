package com.lyzb.jbx.adapter.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.common.CityDialogModel;

import java.util.List;

public class CitySelectAdapter extends BaseRecyleAdapter<CityDialogModel> {

    private String cityName = "重庆市";

    public CitySelectAdapter(Context context, List<CityDialogModel> list) {
        super(context, R.layout.dialog_recycle_item_layout, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CityDialogModel item) {
        holder.setText(R.id.city_name, item.getName());
        TextView city_name = holder.cdFindViewById(R.id.city_name);
        if (item.getName().equals(cityName)){
            city_name.setTextColor(ContextCompat.getColor(_context,R.color.red));
            holder.setVisible(R.id.city_choose,true);
        }else {
            city_name.setTextColor(ContextCompat.getColor(_context,R.color.fontcColor1));
            holder.setVisible(R.id.city_choose,false);
        }
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
