package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.QRCodeUtil;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

public class BarCodeActivity extends YSCBaseActivity {

    @BindView(R.id.imageViewBarcode)
    ImageView imageViewBarcode;
    @BindView(R.id.textViewBarcode)
    TextView textViewBarcode;

    @BindView(R.id.linearlayout)
    View linearlayout;
    @BindView(R.id.linearlayoutContent)
    View linearlayoutContent;


    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_bar_code;
        super.onCreate(savedInstanceState);

        linearlayoutContent.setRotation(90);

        code = getIntent().getStringExtra(Key.KEY_BARCODE.getValue());
        setUpdate();

        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpdate(){
        int barcodeWidth = Utils.dpToPx(this, 300);
        imageViewBarcode.setImageBitmap(QRCodeUtil.creatBarcode(this, code, barcodeWidth, barcodeWidth / 5, false));
        textViewBarcode.setText(code);
    }

}
