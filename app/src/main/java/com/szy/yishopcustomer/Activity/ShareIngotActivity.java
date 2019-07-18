package com.szy.yishopcustomer.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.FileUtil;
import com.szy.yishopcustomer.Util.RxFileTool;
import com.szy.yishopcustomer.Util.Utils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 赚元宝-分享好友送元宝 activity
 * @time 2018 10:04
 */

public class ShareIngotActivity extends Activity implements View.OnClickListener {

    public static final String SHARE_INGOT = "Ingot_Share_Img";
    public static final String IMG_PATH = "Ingot_Img_Path";

    private static final int SHARE_QQ = 1101;
    private static final int SHARE_WX = 1102;
    private static final int SHARE_QZONE = 1103;

    private static final int FILE_PERMISSION = 1122;


    @BindView(R.id.toolbar_share_ingot)
    Toolbar mToolbar;

    @BindView(R.id.linear_share_ingot)
    LinearLayout mLinearLayout_Img;
    @BindView(R.id.img_user_code)
    ImageView mImageView_Usercode;

    @BindView(R.id.share_wx_linear)
    LinearLayout mLinearLayout_WX;
    @BindView(R.id.share_qq_linear)
    LinearLayout mLinearLayout_QQ;
    @BindView(R.id.share_qqzone_linear)
    LinearLayout mLinearLayout_QZONE;

    private ArrayList<String> shareData = new ArrayList<>();
    private String invite_code = null;

    private int shareType = 0;
    private Bitmap shareBitmap = null;
    private String shareImgPath = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_ingot);
        ButterKnife.bind(this);

        //处理 exposed beyond app through ClipData.Item.getUri() 方案
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        mLinearLayout_WX.setOnClickListener(this);
        mLinearLayout_QQ.setOnClickListener(this);
        mLinearLayout_QZONE.setOnClickListener(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!TextUtils.isEmpty(App.getInstance().user_inv_code)) {
            invite_code = "?invite_code=" + App.getInstance().user_inv_code + "&source=tgsshare";
            mImageView_Usercode.setImageBitmap(Utils.createBarCode(this, Api.API_REGIS_SHARE_INGOT + invite_code));
        } else {
            mImageView_Usercode.setImageBitmap(Utils.createBarCode(this, Api.API_REGIS_SHARE_INGOT));
        }

    }

    @Override
    public void onClick(View v) {

        shareBitmap = createViewBitmap(mLinearLayout_Img);

        switch (v.getId()) {
            case R.id.share_wx_linear:

                shareType = SHARE_WX;
                goShare();
                break;
            case R.id.share_qq_linear:

                shareType = SHARE_QQ;
                goShare();
                break;
            case R.id.share_qqzone_linear:

                shareType = SHARE_QZONE;
                goShare();
                break;
            default:
                break;
        }
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    public void goShare() {

        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_PERMISSION);
            } else {
                setShareImg();
            }

        } else {
            setShareImg();
        }

    }


    private void setShareImg() {

        shareImgPath = Utils.saveBitmapFile(this, shareBitmap, "shareImg");
        Log.d("wyx", "图片路径为01-" + shareImgPath);

        if (shareImgPath != null) {

            switch (shareType) {
                case SHARE_QQ:
                    shareQQ();
                    break;
                case SHARE_WX:
                    shareWeChat(shareImgPath);
                    break;
                case SHARE_QZONE:
                    shareQZONE();
                    break;
            }
        } else {
            Toast.makeText(this, "图片地址异常,无法分享", Toast.LENGTH_SHORT).show();
        }

    }

    public void shareWeChat(String path) {

        if (Utils.isWeixinAvilible(this)) {
            Uri uriToImage = Uri.fromFile(new File(path));
            Intent shareIntent = new Intent();
            //发送图片给好友。
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            shareIntent.setComponent(comp);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "分享图片"));
        } else {
            Utils.toastUtil.showToast(this, "请先安装微信客户端");
        }
    }

    public void shareQQ() {
        if (Utils.isQQClientAvailable(this)) {

            Intent intent = new Intent();
            intent.putExtra(SHARE_INGOT, 1);
            intent.putExtra(IMG_PATH, shareImgPath);
            intent.setClass(this, TencentShareActivity.class);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_TENCENT_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(this, "请先安装QQ客户端");
        }
    }

    public void shareQZONE() {

        /**分享数据:分享标题 分享内容 分享地址  分享图片地址**/
        shareData.add(Api.API_REGIS_SHARE_INGOT);
        shareData.add("快来！集宝箱疯狂送元宝，1元宝=1元现金");
        shareData.add("一大波现金福利来袭！快和我一起抢抢抢！");
        shareData.add(shareImgPath);

        Intent intentQzone = new Intent();
        intentQzone.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), shareData);
        intentQzone.setClass(this, TencentQzoneShareActivity.class);
        startActivityForResult(intentQzone, RequestCode.REQUEST_CODE_TENCENT_QZONE_SHARE.getValue());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FILE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                shareImgPath = Utils.saveBitmapFile(this, shareBitmap, "shareImg");

                if (shareImgPath != null) {
                    switch (shareType) {
                        case SHARE_QQ:
                            shareQQ();
                            break;
                        case SHARE_WX:
                            shareWeChat(shareImgPath);
                            break;
                        case SHARE_QZONE:
                            shareQZONE();
                            break;
                    }
                } else {
                    Toast.makeText(this, "图片地址异常,无法分享", Toast.LENGTH_SHORT).show();
                }

            } else {
                //已拒绝读写权限申请
                Toast.makeText(this, "读写权限申请已拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
