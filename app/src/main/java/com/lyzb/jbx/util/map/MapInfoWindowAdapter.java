package com.lyzb.jbx.util.map;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.lyzb.jbx.R;

public class MapInfoWindowAdapter implements AMap.InfoWindowAdapter {

    View infoWindow = null;
    private Context context;
    TextView tv_location_address, tv_locatio;

    public MapInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(context).inflate(
                    R.layout.view_map_mark, null);
        }
        render(marker, infoWindow);
        return infoWindow;
    }

    public void render(Marker marker, View view) {
        //如果想修改自定义Infow中内容，请通过view找到它并修改
        tv_location_address = view.findViewById(R.id.tv_location_address);
        tv_locatio = view.findViewById(R.id.tv_locatio);
    }

    public void setContent(String value) {
        if (!TextUtils.isEmpty(value))
            tv_location_address.setText(value);
    }

    public void setLoactionCilck(final View.OnClickListener cilck) {
        tv_locatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cilck.onClick(v);
            }
        });
    }
}
