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

import com.google.gson.Gson;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.AddComdImgAdapter;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.me.AddComdImgModel;
import com.lyzb.jbx.mvp.presenter.me.card.CardComdPresenter;
import com.lyzb.jbx.mvp.view.me.ICardComdImgView;
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
 * @role 智能卡片-企业信息 修改or添加(简介,荣誉,品牌)
 * @time 2019 2019/3/6 8:56
 */

public class CardCompanyEditFragment extends BasePhotoFragment<CardComdPresenter> implements ICardComdImgView {

    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String TYPE_TEXT = "TYPE_TEXT";
    private static final String TYPE_LIST = "TYPE_LIST";
    GuideView guideView;
    private int mType = 0;
    private List<AddComdImgModel> mList = null;

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

    private List<AddComdImgModel> mDeleteImgs = new ArrayList<>();        //记录删除的图片

    public static CardCompanyEditFragment newIntance(int mType, String text, List<AddComdImgModel> imgList) {
        CardCompanyEditFragment fragment = new CardCompanyEditFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_KEY, mType);
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
            mList = (List<AddComdImgModel>) mBundle.getSerializable(TYPE_LIST);
            //传来的图中含有 加号图标
            for (int i = mList.size() - 1; i > 0; i--) {
                if (mList.get(i).getImgType() == 1) {
                    mList.remove(i);
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
                titleText.setText("公司简介");
                break;
            case 3://提供
                titleText.setText("荣誉展示");
                break;
            case 4://需求
                titleText.setText("品牌展示");
                break;
            default:
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
                        .setContent("编辑信息未完成,确定要返回?")
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
                    case R.id.img_un_me_img_cancle:

                        AddComdImgModel index_model = (AddComdImgModel) adapter.getPositionModel(position);
                        String imgId = index_model.getImgId();

                        if (mList.size() > 0) {
                            for (int i = 0; i < mList.size(); i++) {
                                if (!TextUtils.isEmpty(imgId)) {
                                    if (imgId.equals(mList.get(i).getImgId())) {//点的是已经上传了的图片(上个页面传过来的)
                                        AddComdImgModel imgModel = new AddComdImgModel();
                                        imgModel.setImgId(mList.get(i).getImgId());
                                        imgModel.setImgUrl(mList.get(i).getImgUrl());
                                        mDeleteImgs.add(imgModel);

                                        mList.remove(i);
                                    }
                                }
                            }
                        }


                        mImgAdapter.remove(position);
                        if (mImgAdapter.getItemCount() > 0) {
                            AddComdImgModel firstItem = mImgAdapter.getPositionModel(mImgAdapter.getList().size() - 1);
                            if (firstItem.getImgType() != 1) {//如果第一张 不是加号
                                if (mImgAdapter.getItemCount() < 9) {
                                    AddComdImgModel addModel = new AddComdImgModel();
                                    addModel.setImgType(1);

                                    mImgAdapter.add(0, addModel);
                                    for (int i = 0; i < mImgAdapter.getList().size(); i++) {
                                        if (mImgAdapter.getList().get(i).getImgType() == 1) {
                                            Collections.swap(mImgAdapter.getList(), i, mImgAdapter.getList().size() - 1);
                                        }
                                    }
                                    mImgAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            AddComdImgModel addModel = new AddComdImgModel();
                            addModel.setImgType(1);
                            mImgAdds.add(addModel);
                            mImgAdapter.update(mImgAdds);
                        }

                        break;
                    default:
                        break;
                }
            }
        });


        helper.attachToRecyclerView(cardRecy);
        cardRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemLongClick(BaseRecyleAdapter adapter, View view, int position) {
                if (mImgAdapter.getList().get(position).getImgType()!=1){
                    helper.startDrag(cardRecy.getChildViewHolder(view));
                }
            }
        });
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
    public void onInitData(@Nullable Bundle savedInstanceState) {

        mImgAdapter = new AddComdImgAdapter(getContext(), null);
        mImgAdapter.setGridLayoutManager(cardRecy, 3);
        cardRecy.setAdapter(mImgAdapter);
        cardRecy.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_white_4));

        if (mList != null) {

            if (mList.size() > 8) {//全是图片

                mImgAdapter.update(mList);

            } else {//添加加号图标  第一个是加号图标
                mImgAdapter.addAll(mList);

                AddComdImgModel addModel = new AddComdImgModel();
                addModel.setImgType(1);
                mImgAdds.add(addModel);
                mImgAdapter.addAll(mImgAdds);

            }
        } else {//前面没有传图片的时候   添加加号图标  第一个是加号图标

            AddComdImgModel addModel = new AddComdImgModel();
            addModel.setImgType(1);
            mImgAdds.add(addModel);

            mImgAdapter.update(mImgAdds);
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_card_edtcomd;
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
                        for (int i = 0; i < mImgAdapter.getList().size(); i++) {
                            if (mImgAdapter.getList().get(i).getImgType() == 1) {
                                Collections.swap(mImgAdapter.getList(), i, mImgAdapter.getList().size() - 1);
                            }
                        }
                        if (mImgModels.size() > 8) {
                            mImgAdapter.remove(mImgAdapter.getList().size() - 1);
                        }
                        mImgAdapter.notifyDataSetChanged();
                        if (imgList.size() >= 2) {
                            int num = AppPreference.getIntance().getKeyWebHintOne();
                            AppPreference.getIntance().setKeyWebHintOne(num + 1);
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

    @Override
    public void toCardInfo(String data) {

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
                        if (mImgAdapter.getPositionModel(mImgAdapter.getList().size() - 1).getImgType() == 1) {//表示第一张是加号
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

        if (TextUtils.isEmpty(infoEdt.getText().toString().trim())&&mImgAdapter.getItemCount() < 2) {
            showToast("请输入文本内容或则上传图片");
            return;
        }


        Bundle bundle = new Bundle();
        bundle.putInt("ComdType", mType);
        bundle.putString("AddComdText", infoEdt.getText().toString().trim());

        Gson gson = new Gson();
        if (mImgAdapter.getList().get(mImgAdapter.getList().size()-1).getImgType() == 1) {//有加号
            bundle.putString("AddComdImg", gson.toJson(mImgAdapter.getList().subList(0, mImgAdapter.getList().size()-1)));
        } else {//没得加号
            bundle.putString("AddComdImg", gson.toJson(mImgAdapter.getList()));
        }
        bundle.putString("DeleImg", gson.toJson(mDeleteImgs));
        setFragmentResult(RESULT_OK, bundle);
        pop();

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
