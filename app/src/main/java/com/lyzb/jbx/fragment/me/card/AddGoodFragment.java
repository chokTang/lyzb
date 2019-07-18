package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.dialog.original.ActionSheetDialog;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.card.CdInfoAddImgAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.fragment.base.BasePhotoFragment;
import com.lyzb.jbx.fragment.send.UnionSendTWFragment;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.model.me.AddGoodImgModel;
import com.lyzb.jbx.model.me.CardItemInfoModel;
import com.lyzb.jbx.model.me.GoodsDesModel;
import com.lyzb.jbx.model.params.AddGoodsBody;
import com.lyzb.jbx.model.params.FileBody;
import com.lyzb.jbx.mvp.presenter.me.AddGoodPresenter;
import com.lyzb.jbx.mvp.view.me.IAddGoodView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.ListChangeIndexUtil;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 发布商品
 * @time 2019 2019/3/12 11:17
 */

public class AddGoodFragment extends BasePhotoFragment<AddGoodPresenter> implements IAddGoodView {

    public static final int RESULT_CODE = 9875;
    public static final String KEY_RESULT_PAMAS = "KEY_pic_pamas";
    public static final String KEY_COMPANY_ID = "companyId";
    List<FileBody> list = new ArrayList<>();
    @BindView(R.id.img_union_me_good_back)
    ImageView backImg;
    @BindView(R.id.tv_union_me_good_add)
    TextView addGoodText;

    @BindView(R.id.tv_set_description)
    TextView tv_set_description;

    @BindView(R.id.lin_cd_good_store_app)
    LinearLayout storeAppLin;

    @BindView(R.id.edt_un_add_good_title)
    EditText titleEdt;
    @BindView(R.id.recy_un_add_good_img)
    RecyclerView imgRecy;
    @BindView(R.id.edt_un_add_good_price)
    EditText priceEdt;

    GoodsDesModel goodsDesModel;
    String imgString = "";

    private CdInfoAddImgAdapter mImgAdapter = null;
    private List<CardItemInfoModel> mImgAdds = new ArrayList<>();
    private List<CardItemInfoModel> mImgModels = new ArrayList<>();

    private String type = "java";
    private String goods_name;
    private String goods_price;
    //    private List<AddGoodImgModel> goodImgs = new ArrayList<>();
    private String mobile_desc;
    private String companyId = "";


    public static AddGoodFragment newIntance(String companyId) {
        AddGoodFragment fragment = new AddGoodFragment();
        Bundle args = new Bundle();
        args.putString(KEY_COMPANY_ID, companyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            companyId = bundle.getString(KEY_COMPANY_ID);
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


        tv_set_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsDesModel == null) {
                    startForResult(UnionSendTWFragment.Companion.newIntance("sendGoodsPic", ""), RESULT_CODE);
                } else {
                    startForResult(UnionSendTWFragment.Companion.newIntance("sendGoodsPic", goodsDesModel), RESULT_CODE);
                }
            }
        });

        storeAppLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.lyzb.jbxsj");
                    startActivity(intent);
                } catch (Exception e) {
                    Intent pfIntent = new Intent(getContext(), ProjectH5Activity.class);
                    pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_MERCHANTS);
                    startActivity(pfIntent);
                    e.printStackTrace();
                }
            }
        });

        priceEdt.setFilters(new InputFilter[]{AppCommonUtil.inputFilter});

        addGoodText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(titleEdt.getText().toString().trim())) {
                    showToast("请输入商品标题");
                    return;
                }

                if (TextUtils.isEmpty(priceEdt.getText().toString().trim())) {
                    showToast("请输入商品价格");
                    return;
                }
                if (goodsDesModel==null) {
                    showToast("请设置描述");
                    return;
                }

                if (mImgAdapter.getItemCount() < 2) {
                    showToast("请至少上传1张商品图片");
                    return;
                }

                goods_name = titleEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(goodsDesModel.getDecContent())){
                    mobile_desc = goodsDesModel.getDecContent();
                }
                goods_price = priceEdt.getText().toString().trim();

//                for (int i = 0; i < mImgModels.size(); i++) {
//                    AddGoodImgModel model = new AddGoodImgModel();
//                    model.setPath(mImgModels.get(i).getFilePath());
//
//                    goodImgs.add(model);
//                }

                addGoods();
            }
        });

        imgRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                CardItemInfoModel model = (CardItemInfoModel) adapter.getPositionModel(position);
                switch (view.getId()) {
                    case R.id.img_un_me_img_cancle:
                        for (int i = 0; i < mImgModels.size(); i++) {
                            if (model.getId() == mImgModels.get(i).getId()) {
                                mImgModels.remove(i);
                                i--;
                            }
                        }
                        mImgAdapter.remove(position);
                        if (mImgAdapter.getItemCount() > 0) {
                            CardItemInfoModel firstItem = mImgAdapter.getPositionModel(mImgAdapter.getList().size() - 1);
                            if (firstItem.getType() != 1) {//如果最后一张 不是加号
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

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                CardItemInfoModel model = (CardItemInfoModel) adapter.getPositionModel(position);
                if (model.getType() == 1) {
                    chooseImg();
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

        mImgAdapter = new CdInfoAddImgAdapter(getContext(), null);
        mImgAdapter.setGridLayoutManager(imgRecy, 3);
        imgRecy.setAdapter(mImgAdapter);
        imgRecy.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_white_10));


        CardItemInfoModel addModel = new CardItemInfoModel();
        addModel.setType(1);
        mImgAdds.add(addModel);

        mImgAdapter.update(mImgAdds);

        helper.attachToRecyclerView(imgRecy);
        imgRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemLongClick(BaseRecyleAdapter adapter, View view, int position) {
                if (mImgAdapter.getList().get(position).getType() != 1) {
                    helper.startDrag(imgRecy.getChildViewHolder(view));
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
    public Object getResId() {
        return R.layout.fg_un_me_card_add_good;
    }

    private void chooseImg() {

        new ActionSheetDialog(getActivity())
                .builder()
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                        onChooseFromCameraRound(false, new ISelectPictureListener() {
                            @Override
                            public void onSuccess(String imgUrl) {
                                mPresenter.upLoadFiles(Arrays.asList(imgUrl));
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
                        //多选的时候
//                        int indexSize = 0;
//                        if (mImgAdapter.getPositionModel(0).getType() == 1) {//表示第一张是加号
//                            indexSize = 5 - mImgAdapter.getItemCount() + 1;
//                        } else {
//                            indexSize = 5 - mImgAdapter.getItemCount();
//                        }
                        onChooseFromPhotoRound(false, new ISelectPictureListener() {
                            @Override
                            public void onSuccess(String imgUrl) {
                                mPresenter.upLoadFiles(Arrays.asList(imgUrl));

                            }

                            @Override
                            public void onFail() {

                            }
                        });


                    }
                })
                .show();
    }

    private void addGoods() {
        Map<String, Object> goods = new HashMap<>();

        goods.put("userId", App.getInstance().userId);
        goods.put("type", type);
        goods.put("goods_name", goods_name);
        goods.put("goods_price", goods_price);
        if (!TextUtils.isEmpty(companyId)){
            goods.put("distributor_id", companyId);
        }
        JSONArray goods_imgs = new JSONArray();
        for (int i = 0; i < mImgAdapter.getList().size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("path", mImgAdapter.getList().get(i).getFilePath());
                goods_imgs.put(object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        goods.put("goods_imgs", goods_imgs.toString());
        JSONArray mobile_desc_images = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            mobile_desc_images.put(list.get(i).getFile());
        }
        goods.put("mobile_desc_images", mobile_desc_images.toString());
        goods.put("mobile_desc", mobile_desc);

        mPresenter.addGood(goods);
    }

    @Override
    public void onSuceess() {

        AppPreference.getIntance().setKeySendProduct(true);
        AlertDialogFragment.newIntance()
                .setKeyBackable(false)
                .setCancleable(false)
                .setContent("发布商品成功!")
                .setCancleBtn("返回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        setFragmentResult(RESULT_OK, bundle);
                        pop();
                    }
                })
                .setSureBtn("继续发布", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        titleEdt.setText("");
                        priceEdt.setText("");
                        tv_set_description.setText("点击设置");

                        mImgAdds.clear();
                        CardItemInfoModel addModel = new CardItemInfoModel();
                        addModel.setType(1);
                        mImgAdds.add(addModel);

                        mImgAdapter.update(mImgAdds);
                    }
                })
                .show(getFragmentManager(), "");
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
                        mImgAdapter.notifyDataSetChanged();

                        if (mImgModels.size() > 4) {
                            mImgAdapter.remove(mImgAdapter.getList().size() - 1);
                        }
                    }
                });

            }
        }).start();

    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE:
                if (data != null) {
                    goodsDesModel = (GoodsDesModel) data.getSerializable(KEY_RESULT_PAMAS);
                    tv_set_description.setText("已设置");
                    list = GSONUtil.getEntityList(goodsDesModel.getDecPic(), FileBody.class);
//                    for (int i = 0; i < list.size(); i++) {
//                        if (i == 0) {
//                            imgString = list.get(i).getFile();
//                        } else {
//                            imgString = list.get(i).getFile() + "," + imgString;
//                        }
//                    }
                }
                break;
        }

    }
}
