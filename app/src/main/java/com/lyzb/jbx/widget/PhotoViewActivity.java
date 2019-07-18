package com.lyzb.jbx.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.utilslib.file.FileUtil;
import com.like.utilslib.image.BitmapUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;

import java.util.List;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BIG_IMAGE_URL = "bigimgurls";
    public static final String BIG_IMAGE_POSITION = "bigimgurls";
    public static final String BUNDLE = "bundle";
    public static final String TAG = PhotoViewActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private TextView mTvSaveImage;
    private List<String> Urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
        mTvSaveImage = (TextView) findViewById(R.id.tv_save_image_photo);
        mTvSaveImage.setOnClickListener(this);

    }

    private void initData() {

        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(BIG_IMAGE_POSITION, 0);
        Urls = intent.getBundleExtra(BUNDLE).getStringArrayList(BIG_IMAGE_URL);

        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });

        /**
         * 长按事件
         */
        adapter.setClickListener(new IRecycleAnyClickListener() {
            @Override
            public void onItemClick(final View view, int position, Object item) {
                AlertDialogFragment.newIntance()
                        .setContent("是否保存此图片？")
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageView itemView = (ImageView) view;
                                Bitmap bitmap = BitmapUtil.drawableToBitmap(itemView.getDrawable());
                                FileUtil.saveBitmap(bitmap, "lyzb", 100);
                                Toast.makeText(PhotoViewActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancleBtn(null)
                        .show(getSupportFragmentManager(), "savePicture");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save_image_photo:
                break;
            default:
                break;
        }
    }
}