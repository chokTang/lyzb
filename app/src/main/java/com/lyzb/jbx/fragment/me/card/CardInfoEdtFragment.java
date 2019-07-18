package com.lyzb.jbx.fragment.me.card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.card.CdInfoAddImgAdapter;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.me.CardFileDeModel;
import com.lyzb.jbx.model.me.CardImgTextModel;
import com.lyzb.jbx.model.me.CardItemInfoModel;
import com.lyzb.jbx.mvp.presenter.me.card.CardImgTextPresenter;
import com.lyzb.jbx.mvp.view.me.ICardImgTextView;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.ListChangeIndexUtil;
import com.lyzb.jbx.widget.GuideView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 智能卡片-个人信息 添加or修改
 * @time 2019 2019/3/6 8:56
 */

public class CardInfoEdtFragment extends BasePhotoFragment<CardImgTextPresenter> implements ICardImgTextView {

    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String TYPE_TEXT = "TYPE_TEXT";
    private static final String TYPE_LIST = "TYPE_LIST";
    private static final String TYPE_TITLE = "TYPE_TITLE";

    GuideView guideView;
    private int mType = 0;
    private String mText = null;
    private String mTitle = null;
    private List<CardItemInfoModel> mList = new ArrayList<>();

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

    private CdInfoAddImgAdapter mImgAdapter = null;
    private List<CardItemInfoModel> mImgAdds = new ArrayList<>();
    private List<CardItemInfoModel> mImgModels = new ArrayList<>();

    private int index_item_position = 99;

    public static CardInfoEdtFragment newIntance(int mType,String title, String text, List<CardItemInfoModel> imgList) {
        CardInfoEdtFragment fragment = new CardInfoEdtFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_KEY, mType);
        args.putString(TYPE_TEXT, text);
        args.putString(TYPE_TITLE, title);
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
            mTitle = mBundle.getString(TYPE_TITLE);
            mList = (List<CardItemInfoModel>) mBundle.getSerializable(TYPE_LIST);
            if (mList != null) {
                //传来的图中含有 加号图标
                for (int i = mList.size() - 1; i > 0; i--) {
                    if (mList.get(i).getType() == 1) {
                        mList.remove(i);
                    }
                }
            }
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);

        ButterKnife.bind(this, mView);

        switch (mType) {
            case 1://简介
                titleText.setText("个人信息");
                break;
            case 3://提供
                titleText.setText("我能提供");
                break;
            case 4://需求
                titleText.setText("我需要的");
                break;
            default:
                titleText.setText(mTitle);
                break;
        }


        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogFragment.newIntance()
                        .setKeyBackable(false)
                        .setCancleable(false)
                        .setContent("编辑信息未完成,确定要返回?")
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
                        .show(getFragmentManager(), "");
            }
        });

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("CardEdtInfo", infoEdt.getText().toString().trim());
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });

        cardRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_un_me_info_add:
                        chooseImg();
                        break;
                    case R.id.img_un_me_img_cancle:

                        CardItemInfoModel model = (CardItemInfoModel) adapter.getPositionModel(position);

                        if (null != mList) {
                            for (int i = 0; i < mList.size(); i++) {
                                //移除网络 图片集合内的图片
                                if (model.getId() == mList.get(i).getId()) {
                                    CardFileDeModel delete = new CardFileDeModel();
                                    delete.setId(mList.get(i).getId());
                                    mPresenter.deleImg(delete);

                                    mList.remove(i);
                                    i--;
                                }
                            }
                        } else {
                            //移除本地 新增加的图片
                            for (int i = 0; i < mImgModels.size(); i++) {
                                if (model.getId() == mImgModels.get(i).getId()) {
                                    mImgModels.remove(i);
                                    i--;
                                }
                            }
                        }

                        mImgAdapter.remove(position);
                        if (mImgAdapter.getItemCount() > 0) {
                            CardItemInfoModel firstItem = mImgAdapter.getPositionModel(mImgAdapter.getList().size() - 1);
                            if (firstItem.getType() != 1) {//如果第一张 不是加号
                                if (mImgAdapter.getItemCount() < 9) {
                                    CardItemInfoModel addModel = new CardItemInfoModel();
                                    addModel.setType(1);

                                    mImgAdapter.add(0, addModel);
                                    for (int i = 0; i < mImgAdapter.getList().size(); i++) {
                                        if (mImgAdapter.getList().get(i).getType() == 1) {
                                            Collections.swap(mImgAdapter.getList(), i, mImgAdapter.getList().size() - 1);
                                        }
                                    }
                                    mImgAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            CardItemInfoModel addModel = new CardItemInfoModel();
                            addModel.setType(1);
                            mImgAdds.add(addModel);

                            mImgAdapter.update(mImgAdds);
                        }
                        break;
                }
            }
        });

        helper.attachToRecyclerView(cardRecy);
        cardRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemLongClick(BaseRecyleAdapter adapter, View view, int position) {
                if (mImgAdapter.getList().get(position).getType() != 1) {
                    helper.startDrag(cardRecy.getChildViewHolder(view));
                }
            }
        });

    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

        mImgAdapter = new CdInfoAddImgAdapter(getContext(), null);
        mImgAdapter.setGridLayoutManager(cardRecy, 3);
        cardRecy.setAdapter(mImgAdapter);
        cardRecy.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_white_4));

        if (mList != null) {//图片为空
            if (mList.size() > 8) {
                mImgAdapter.update(mList);
            } else {
                mImgAdapter.addAll(mList);


                CardItemInfoModel addModel = new CardItemInfoModel();
                addModel.setType(1);
                mImgAdds.add(addModel);
                mImgAdapter.addAll(mImgAdds);
            }
        } else {
            CardItemInfoModel addModel = new CardItemInfoModel();
            addModel.setType(1);
            mImgAdds.add(addModel);

            mImgAdapter.update(mImgAdds);
        }

        if (mText != null) {
            infoEdt.setText(mText);
        }


    }


    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return ItemTouchHelper.Callback.makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (mImgAdapter.getList().size() - 1 != target.getAdapterPosition()) {
                ListChangeIndexUtil.swap(mImgAdapter.getList(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
                mImgAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            }
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dpTopx(110f), DensityUtil.dpTopx(110f));
                viewHolder.itemView.findViewById(R.id.img_un_me_info_add).setLayoutParams(layoutParams);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dpTopx(100f), DensityUtil.dpTopx(100f));
            viewHolder.itemView.findViewById(R.id.img_un_me_info_add).setLayoutParams(layoutParams);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    });

    @Override
    public Object getResId() {
        return R.layout.fragment_un_card_edtinfo;
    }

    @Override
    public void toCardInfo(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("CardEdtInfo", data);
        setFragmentResult(RESULT_OK, bundle);
        pop();
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
                            CardItemInfoModel imgModel = new CardItemInfoModel();
                            imgModel.setFilePath(imgList.get(i));
                            imgModel.setId(null);
                            imgModel.setType(2);

                            mImgModels.add(imgModel);
                            mImgAdapter.add(imgModel);
                        }
                        for (int i = 0; i < mImgAdapter.getList().size(); i++) {
                            if (mImgAdapter.getList().get(i).getType() == 1) {
                                Collections.swap(mImgAdapter.getList(), i, mImgAdapter.getList().size() - 1);
                            }
                        }
                        if (mImgAdapter.getItemCount() >= 9) {
                            mImgAdapter.remove(mImgAdapter.getList().size() - 1);
                        }
                        mImgAdapter.notifyDataSetChanged();


                        if (imgList.size() >= 2) {
                            int num = AppPreference.getIntance().getKeyCardHintOne();
                            AppPreference.getIntance().setKeyCardHintOne(num + 1);
                            if (num <= 0) {
                                showGuideView();
                            }
                        }
                    }
                });

            }
        }).start();
    }

    @Override
    public void deleImg() {

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
                        if (mImgAdapter.getPositionModel(mImgAdapter.getList().size() - 1).getType() == 1) {//表示第一张是加号
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

        if (!TextUtils.isEmpty(infoEdt.getText().toString().trim()) || mImgAdapter.getItemCount() >= 2) {
            CardImgTextModel model = new CardImgTextModel();
            model.setContent(infoEdt.getText().toString().trim());
            model.setFileType(1);
            model.setTagType(mType);

            List<CardImgTextModel.Imgs> imgs = new ArrayList<>();

            for (int i = 0; i < mImgAdapter.getList().size(); i++) {
                if (mImgAdapter.getList().get(i).getType() != 1) {
                    CardImgTextModel.Imgs filePath = new CardImgTextModel.Imgs();
                    filePath.setFilePath(mImgAdapter.getList().get(i).getFilePath());
                    filePath.setId(mImgAdapter.getList().get(i).getId());

                    imgs.add(filePath);
                }
            }

            model.setFilePath(imgs);
            mPresenter.postImgText(model);
        } else {
            showToast("请添加信息后再发布");
        }

    }


    private synchronized void showGuideView() {
        final ImageView iv1 = new ImageView(getContext());
        iv1.setImageResource(R.drawable.union_guide_access);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv1.setLayoutParams(params1);

        final TextView iv2 = new TextView(getContext());
        iv2.setText("长按拖动排序");
        iv2.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        iv2.setTextSize(18);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv2.setLayoutParams(params2);

        guideView = GuideView.Builder
                .newInstance(getBaseActivity())
                .setTargetView(cardRecy)  //设置目标view
                .setTextGuideView(iv1)      //设置文字图片
                .setCustomGuideView(iv2)    //设置 我知道啦图片
                .setOffset(0, 50)           //偏移量  x=0 y=80
                .setDirction(GuideView.Direction.BOTTOM)   //方向
                .setShape(GuideView.MyShape.RECTANGULAR)   //矩形
                .setRadius(0)                             //圆角
                .setContain(true)                         //透明的方块时候包含目标view  默认false
                .setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
                        AppPreference.getIntance().setUserFristInterAccess(false);
                        guideView.hide();
                    }
                })
                .build();
        guideView.show();
    }
}
