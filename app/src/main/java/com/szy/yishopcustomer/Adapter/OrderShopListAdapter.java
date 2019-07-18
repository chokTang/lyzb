package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderGoodModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 团购订单/到店付款订单适配器
 */
public class OrderShopListAdapter extends RecyclerView.Adapter {

    public List<OrderModel> data;
    public Context mContext;

    public OrderShopListAdapter(Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    /***
     * @param parent
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order_item_layout, parent, false);
        OrderHolder holder = new OrderHolder(view);
        return holder;
    }

    /***
     * ITEM 赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderHolder orderHolder = (OrderHolder) holder;
        OrderModel itemModel = data.get(position);
        int orderMent = itemModel.getOrderMent();
        dealOrderState(orderHolder, itemModel);
        //处理到店付的图片
        orderHolder.daodianfu_img.setVisibility(orderMent == 1 ? View.GONE : View.VISIBLE);
        //设置店铺名称
        orderHolder.shop_title_tv.setText(itemModel.getStoreName());
        //设置价格
        orderHolder.shop_price_tv.setText(String.format("￥%.2f", itemModel.getPayAmount()));
        //设置订单图片
        String imgUrl = "";
        if (orderMent == 1) {
            OrderGoodModel goodModel = itemModel.getGoodsList().get(0);//获取商品第一个数据
            imgUrl = goodModel == null ? "" : goodModel.getOrderProdLogo();
        } else {
            imgUrl = itemModel.getShopLogo();
        }
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("http")) {
            GlideApp.with(mContext).load(imgUrl)
                    .error(R.mipmap.img_empty)
                    .centerCrop()
                    .into(orderHolder.shop_img);
        } else {
            GlideApp.with(mContext).load(Api.API_CITY_HOME_MER_IMG_URL + imgUrl)
                    .error(R.mipmap.img_empty)
                    .centerCrop()
                    .into(orderHolder.shop_img);
        }
        //设置订单商品名称 及其他的
        if (orderMent == 1) {//团购
            OrderGoodModel goodModel = itemModel.getGoodsList().get(0);//获取商品第一个数据
            String orderName = goodModel.getProdName();
            orderHolder.shop_goods_tv.setText(orderName);

            orderHolder.shop_number_tv.setVisibility(View.VISIBLE);
            orderHolder.shop_dikou_price_tv.setVisibility(View.VISIBLE);
            orderHolder.shop_number_tv.setText(String.format("数量：%d", itemModel.getProdCount()));
            orderHolder.shop_dikou_price_tv.setText(Html.fromHtml("元宝实际抵扣<font color='#f23030'>"
                    + String.format("￥%d", itemModel.getPayPoint()) + "</font>"));
            orderHolder.shop_shiji_price_tv.setText(Html.fromHtml("应付<font color='#f23030'>"
                    + String.format("￥%.2f", itemModel.getPayAmount()) + "</font>"));
        } else {
            orderHolder.shop_goods_tv.setText(itemModel.getStoreName());

            orderHolder.shop_number_tv.setVisibility(View.GONE);
            orderHolder.shop_dikou_price_tv.setVisibility(View.GONE);
            orderHolder.shop_shiji_price_tv.setText(Html.fromHtml("实付金额<font color='#f23030'>"
                    + String.format("￥%.2f", itemModel.getPayAmount()) + "</font>"));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        public LinearLayout order_type_good;//订单内容item

        //店铺名称
        public TextView shop_title_tv;
        public TextView order_type_tv;
        public ImageView shop_img;
        //商品订单名称
        public TextView shop_goods_tv;
        //商品单价
        public TextView shop_price_tv;
        //商品数量
        public TextView shop_number_tv;
        //元宝实际抵扣
        public TextView shop_dikou_price_tv;
        //应付金额 实际支付金额
        public TextView shop_shiji_price_tv;
        public TextView gray_btn;
        public TextView red_btn;
        public ImageView daodianfu_img;

        public OrderHolder(View itemView) {
            super(itemView);
            order_type_good = (LinearLayout) itemView.findViewById(R.id.order_type_good);

            shop_title_tv = (TextView) itemView.findViewById(R.id.shop_title_tv);
            order_type_tv = (TextView) itemView.findViewById(R.id.order_type_tv);
            shop_img = (ImageView) itemView.findViewById(R.id.shop_img);

            shop_goods_tv = (TextView) itemView.findViewById(R.id.shop_goods_tv);
            shop_price_tv = (TextView) itemView.findViewById(R.id.shop_price_tv);
            shop_number_tv = (TextView) itemView.findViewById(R.id.shop_number_tv);
            shop_dikou_price_tv = (TextView) itemView.findViewById(R.id.shop_dikou_price_tv);
            shop_shiji_price_tv = (TextView) itemView.findViewById(R.id.shop_shiji_price_tv);
            gray_btn = (TextView) itemView.findViewById(R.id.gray_btn);
            red_btn = (TextView) itemView.findViewById(R.id.red_btn);

            daodianfu_img = (ImageView) itemView.findViewById(R.id.daodianfu_img);
        }
    }

    public void udpateList(List<OrderModel> data) {
        if (data!=null&&data.size() > 0) {
            this.data.clear();
            this.data.addAll(data);
        } else {
            this.data.clear();
        }
        notifyDataSetChanged();
    }

    public void addAll(List<OrderModel> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void onUpdataPosition(int position, OrderModel orderModel) {
        this.data.set(position, orderModel);
        notifyItemChanged(position);
    }

    public void onDelete(int postion) {
        this.data.remove(postion);
        notifyItemRemoved(postion);
    }

    public List<OrderModel> getData() {
        return data;
    }

    /**
     * 1:待付款;2:待使用;3:待评价;4:退款中;5:已完成;6:已退款;7:已取消;8:商家同意退款
     *
     * @return
     */
    private void dealOrderState(final OrderHolder holder, final OrderModel orderModel) {
        int orderState = orderModel.getOrderStatus();
        int orderMent = orderModel.getOrderMent();
        holder.order_type_tv.setTextColor(Color.parseColor("#f23030"));
        holder.red_btn.setTextColor(Color.parseColor("#f23030"));

        //到店付orderState=5已完成 状态
        switch (orderState) {
            //待付款
            case 1:
                holder.order_type_tv.setText("待付款");
                holder.gray_btn.setVisibility(View.VISIBLE);
                holder.gray_btn.setText("取消订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onCancleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                holder.red_btn.setVisibility(View.VISIBLE);
                holder.red_btn.setText("立即付款");
                holder.red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onPayOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                break;
            //待使用
            case 2:
                holder.order_type_tv.setText("待使用");
                holder.gray_btn.setVisibility(View.GONE);

                holder.red_btn.setVisibility(View.VISIBLE);
                holder.red_btn.setText("查看核销码");
                holder.red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onLoadMa(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                break;
            //待评价
            case 3:
                holder.order_type_tv.setText("待评价");
                holder.gray_btn.setVisibility(View.VISIBLE);
                holder.gray_btn.setText("删除订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeteleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });


                holder.red_btn.setVisibility(View.VISIBLE);
                holder.red_btn.setText("去评价");
                holder.red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onPingLunOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                break;
            //退款中
            case 4:
                holder.order_type_tv.setText("退款中");
                holder.order_type_tv.setTextColor(Color.parseColor("#666666"));
                holder.gray_btn.setVisibility(View.VISIBLE);

                if (orderMent == 1) {
                    holder.gray_btn.setText("退款详情");
                    holder.red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onOrderTuiKuaiDetail(orderModel, holder.getAdapterPosition());
                            }
                        }
                    });

                }
                holder.red_btn.setVisibility(View.GONE);
                break;
            //已完成
            case 5:
                holder.order_type_tv.setText("已完成");
                holder.gray_btn.setVisibility(View.VISIBLE);
                holder.gray_btn.setText("删除订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeteleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });

                holder.red_btn.setVisibility(orderMent == 1 ? View.VISIBLE : View.GONE);
                holder.red_btn.setText("查看评价");
                holder.red_btn.setTextColor(Color.parseColor("#666666"));
                holder.red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderPingLunDetail(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                break;
            //商家同意退款
            case 8:
                //已退款
            case 6:
                holder.order_type_tv.setText("已退款");
                holder.gray_btn.setVisibility(View.VISIBLE);
                holder.gray_btn.setText("删除订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeteleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                holder.red_btn.setVisibility(View.VISIBLE);
                holder.red_btn.setTextColor(Color.parseColor("#666666"));
                holder.red_btn.setText("退款详情");
                holder.red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderTuiKuaiDetail(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                break;
            //已取消
            case 7:
                holder.order_type_tv.setText("已取消");
                holder.order_type_tv.setTextColor(Color.parseColor("#666666"));
                holder.gray_btn.setVisibility(View.VISIBLE);
                holder.gray_btn.setText("删除订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeteleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                holder.red_btn.setVisibility(View.GONE);
                break;
            default:
                holder.order_type_tv.setText("未知状态，状态码" + orderState);
                break;
        }

        //店铺点击
        holder.shop_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLookStore(orderModel, holder.getAdapterPosition());
                }
            }
        });

        //订单点击
        holder.order_type_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOrderDetail(orderModel, holder.getAdapterPosition());
                }
            }
        });

    }

    public interface IOrderClickStatus {

        //查看店铺详情
        void onLookStore(OrderModel orderModel, int positions);

        //查看订单详情
        void onOrderDetail(OrderModel orderModel, int positions);

        //取消订单
        void onCancleOrder(OrderModel orderModel, int positions);

        //去支付订单
        void onPayOrder(OrderModel orderModel, int positions);

        //查看核销码
        void onLoadMa(OrderModel orderModel, int positions);

        //退款
        void onOrderTuiKuaiDetail(OrderModel orderModel, int positions);

        //删除订单
        void onDeteleOrder(OrderModel orderModel, int positions);

        //评论订单
        void onPingLunOrder(OrderModel orderModel, int positions);

        //差看评论
        void onOrderPingLunDetail(OrderModel orderModel, int positions);
    }

    private IOrderClickStatus listener;

    public void setListener(IOrderClickStatus listener) {
        this.listener = listener;
    }
}
