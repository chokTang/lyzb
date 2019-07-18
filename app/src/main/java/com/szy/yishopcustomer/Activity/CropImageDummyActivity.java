package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by 宗仁 on 2016/12/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class CropImageDummyActivity extends YSCBaseActivity {

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_unionpay_dummy;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        Uri imageUri = intent.getParcelableExtra(Key.KEY_IMAGE_URI.getValue());
        Uri outputUri = intent.getParcelableExtra(Key.KEY_IMAGE_OUTPUT_URI.getValue());

        CropImage.activity(imageUri).setOutputUri(outputUri).setFixAspectRatio(true).setAspectRatio(
                1, 1).setMaxCropResultSize(1000, 1000).setGuidelines(
                CropImageView.Guidelines.ON).start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == Activity.RESULT_OK) {
            Uri resultUri = result.getUri();
            Intent intent = new Intent();
            intent.putExtra(Key.KEY_IMAGE_URI.getValue(), resultUri);
            setResult(RESULT_OK, intent);
            finish();
        } else if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            Toast.makeText(this, R.string.cropImageFailed, Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
