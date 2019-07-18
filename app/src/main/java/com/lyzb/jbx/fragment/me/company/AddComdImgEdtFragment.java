package com.lyzb.jbx.fragment.me.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.AddComdImgAdapter;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.me.AddComdImgModel;
import com.lyzb.jbx.mvp.presenter.me.AddComdImgPresenter;
import com.lyzb.jbx.mvp.view.me.IAddComdImgView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 创建企业 图片信息
 * @time
 */

public class AddComdImgEdtFragment extends BasePhotoFragment<AddComdImgPresenter> implements IAddComdImgView {

    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String TYPE_TEXT = "TYPE_TEXT";
    private static final String TYPE_LIST = "TYPE_LIST";

    private int mType = 0;
    private String mText = null;
    private List<AddComdImgModel> mList = new ArrayList<>();

    @BindView(R.id.img_un_card_info_edt_back)
    ImageView backImg;
    @BindView(R.id.tv_un_card_info_title)
    TextView titleText;
    @BindView(R.id.tv_un_card_info_save)
    TextView saveText;

    @BindView(R.id.edt_un_card_info_edt)
    EditText infoEdt;

    @BindView(R.id.recy_un_card_info)
    RecyclerView cardRecy;

    private AddComdImgAdapter mImgAdapter = null;
    private List<AddComdImgModel> mImgAdds = new ArrayList<>();
    private List<AddComdImgModel> mImgModels = new ArrayList<>();

    private boolean isAdd = true;    //标记  当前页面是 创建 or 修改


    //记录删除的图片集合
    private List<AddComdImgModel> mDeleteImgs = new ArrayList<>();

    public static AddComdImgEdtFragment newIntance(int mType, String text, List<AddComdImgModel> imgList) {
        AddComdImgEdtFragment fragment = new AddComdImgEdtFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_KEY, mType);
        args.putString(TYPE_TEXT, text);
        args.putSerializable(TYPE_LIST, (Serializable) imgList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            mType = mBundle.getInt(TYPE_KEY);
            mText = mBundle.getString(TYPE_TEXT);
            mList = (List<AddComdImgModel>) mBundle.getSerializable(TYPE_LIST);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        ButterKnife.bind(this, mView);
        switch (mType) {
            //1 公司简介  2荣誉展示  3品牌展示
            case 1://简介
                titleText.setText("公司简介");
                break;
            case 2://提供
                titleText.setText("荣誉展示");
                break;
            case 3://品牌展示
                titleText.setText("品牌展示");
                break;
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogFragment.newIntance()
                        .setKeyBackable(false)
                        .setCancleable(false)
                        .setCancleBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pop();
                            }
                        })
                        .setTitle("编辑信息未完成,确定要返回?")
                        .show(getFragmentManager(), "TAG");
            }
        });

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        cardRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_info_add:
                        chooseImg();
                        break;
                    //删除一个
                    case R.id.img_un_me_img_cancle:
                        AddComdImgModel index_model = (AddComdImgModel) adapter.getPositionModel(position);
                        //判断 该图片集合为 已有图片 有id表示已有
                        String imgId = index_model.getImgId();
                        if (imgId != null) {
                            for (int i = 0; i < mList.size(); i++) {
                                if (imgId == mList.get(i).getImgId()) {
                                    AddComdImgModel imgModel = new AddComdImgModel();
                                    imgModel.setImgId(mList.get(i).getImgId());
                                    imgModel.setImgUrl(mList.get(i).getImgUrl());
                                    mDeleteImgs.add(imgModel);
                                    mList.remove(i);
                                }
                            }
                        }

                        mImgAdapter.remove(position);
                        if (mImgAdapter.getItemCount() > 0) {
                            AddComdImgModel firstItem = mImgAdapter.getPositionModel(0);
                            if (firstItem.getImgType() != 1) {//如果第一张 不是加号
                                if (mImgAdapter.getItemCount() < 9) {
                                    AddComdImgModel addModel = new AddComdImgModel();
                                    addModel.setImgType(1);

                                    mImgAdapter.add(0, addModel);
                                }
                            }
                        } else {
                            AddComdImgModel addModel = new AddComdImgModel();
                            addModel.setImgType(1);
                            mImgAdds.add(addModel);

                            mImgAdapter.update(mImgAdds);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

        mImgAdapter = new AddComdImgAdapter(getContext(), null);
        mImgAdapter.setGridLayoutManager(cardRecy, 3);
        cardRecy.setAdapter(mImgAdapter);
        cardRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_white_4));

        if (!TextUtils.isEmpty(mText)) {
            isAdd = false;
            infoEdt.setText(mText);
            if (mList.size() > 8) {
                mImgAdapter.update(mList);
            } else {
                AddComdImgModel addModel = new AddComdImgModel();
                addModel.setImgType(1);
                mImgAdds.add(addModel);
                mImgAdapter.addAll(mImgAdds);
                mImgAdapter.addAll(mList);
            }

        } else {

            AddComdImgModel addModel = new AddComdImgModel();
            addModel.setImgType(1);
            mImgAdds.add(addModel);
            mImgAdapter.update(mImgAdds);
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_add_comd;
    }

    @Override
    public void setImgList(final List<String> imgList) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < imgList.size(); i++) {
                            AddComdImgModel imgModel = new AddComdImgModel();
                            imgModel.setImgUrl(imgList.get(i));
                            imgModel.setImgType(2);

                            mImgModels.add(imgModel);
                            mImgAdapter.add(imgModel);
                        }

                        if (!isAdd) {
                            mList.addAll(mImgModels);
                        }

                        if (mImgModels.size() > 8) {
                            mImgAdapter.remove(0);
                        }
                    }
                });

            }
        }).start();
    }

    private void chooseImg() {
        new ActionSheetDialog(getActivity())
                .builder()
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        onChooseFromCamera(new ISelectPictureListener() {
                            @Override
                            public void onSuccess(String imgUrl) {
                                mPresenter.upLoadFiles(CommonUtil.StringToList(imgUrl));
                            }

                            @Override
                            public void onFail() {

                            }
                        });

                    }
                })
                .addSheetItem("相册选择", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        int indexSize = 0;
                        if (mImgAdapter.getPositionModel(0).getImgType() == 1) {//表示第一张是加号
                            indexSize = 9 - mImgAdapter.getItemCount() + 1;
                        } else {
                            indexSize = 9 - mImgAdapter.getItemCount();
                        }
                        onChooseMultiple(indexSize, new ISelectPictureListener() {
                            @Override
                            public void onSuccess(String imgUrl) {
                                mPresenter.upLoadFiles(CommonUtil.StringToList(imgUrl));
                            }

                            @Override
                            public void onFail() {

                            }
                        });

                    }
                })
                .show();
    }

    private void updateData() {

        if (TextUtils.isEmpty(infoEdt.getText().toString().trim())) {
            showToast("请输入文本内容");
            return;
        }

        if (mImgAdapter.getItemCount() < 2) {
            showToast("请至少上传1张图片");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("AddComdText", infoEdt.getText().toString().trim());

        if (isAdd) {
            bundle.putSerializable("AddComdImg", (Serializable) mImgModels);
        } else {
            bundle.putSerializable("AddComdImg", (Serializable) mList);
        }

        //删除图片集合记录
        bundle.putSerializable("DeleImg", (Serializable) mDeleteImgs);

        setFragmentResult(RESULT_OK, bundle);
        pop();
    }
}
