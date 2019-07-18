package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;
import com.szy.yishopcustomer.Util.DateStyle;
import com.szy.yishopcustomer.Util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 同城生活-外卖订单适配器
 */
public class OrderTakeOutListAdapter extends RecyclerView.Adapter {

    public List<OrderModel> data;
    public Context mContext;

    public OrderTakeOutListAdapter(Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    /***
     * @param parent
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order_takeout_item_layout, parent, false);
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
        dealOrderState(orderHolder, itemModel);
        //设置订单状态
        orderHolder.order_type_tv.setText(itemModel.getOrderStatusMsg());
        //设置店铺名称
        orderHolder.shop_goods_tv.setText(itemModel.getStoreName());
        //设置下单时间
        orderHolder.order_time_tv.setText(String.format("下单时间：%s",
                DateUtil.StringToString(itemModel.getGmtCreate(), DateStyle.YYYY_MM_DD_HH_MM)));
        //设置订单图片
        String imgUrl = itemModel.getShopLogo();
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
        orderHolder.shop_number_tv.setText(String.format("总价：￥%.2f", itemModel.getPayAmount()));
        orderHolder.shop_dikou_price_tv.setText(String.format("元宝实际抵扣数￥%d", itemModel.getPayPoint()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        public LinearLayout order_type_good;//订单内容item

        public ImageView shop_img;
        //商品订单名称
        public TextView shop_goods_tv;
        public TextView order_type_tv;
        public TextView order_time_tv;
        public TextView shop_number_tv;
        public TextView shop_dikou_price_tv;
        public TextView gray_btn;
        public TextView red_btn;

        public OrderHolder(View itemView) {
            super(itemView);
            order_type_good = (LinearLayout) itemView.findViewById(R.id.order_type_good);

            order_type_tv = (TextView) itemView.findViewById(R.id.order_type_tv);
            shop_img = (ImageView) itemView.findViewById(R.id.shop_img);

            shop_goods_tv = (TextView) itemView.findViewById(R.id.shop_goods_tv);
            order_time_tv = (TextView) itemView.findViewById(R.id.order_time_tv);
            shop_number_tv = (TextView) itemView.findViewById(R.id.shop_number_tv);
            shop_dikou_price_tv = (TextView) itemView.findViewById(R.id.shop_dikou_price_tv);

            gray_btn = (TextView) itemView.findViewById(R.id.gray_btn);
            red_btn = (TextView) itemView.findViewById(R.id.red_btn);
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
     * 1:待付款;2:待使用;3:待评价;4:退款中;5:已完成;6:已退款;7:已取消;8:商家同意退款 9:商家拒单
     * 待发货 等待商家接单 待自提&配送中 这几种状态orderStatus都是2
     *
     * @return
     */
    private void dealOrderState(final OrderHolder holder, final OrderModel orderModel) {
        int orderState = orderModel.getOrderStatus();
        int shippingStatus = orderModel.getShippingStatus();
        holder.gray_btn.setTextColor(Color.parseColor("#666666"));
        holder.gray_btn.setBackgroundResource(R.drawable.shape_gray_line_white_background);
        holder.red_btn.setTextColor(Color.parseColor("#f23030"));
        holder.red_btn.setBackgroundResource(R.drawable.shape_red_line_white_background);
        holder.gray_btn.setVisibility(View.VISIBLE);
        holder.red_btn.setVisibility(View.VISIBLE);
        switch (orderState) {
            //待使用
            case 2:
                //待发货
                if (shippingStatus == 1) {
                    holder.order_type_tv.setText("待发货");
                    holder.gray_btn.setVisibility(View.GONE);
                    holder.red_btn.setText("申请退款");
                    holder.red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onApplyRefund(orderModel, holder.getAdapterPosition());
                            }
                        }
                    });
                } else if (shippingStatus == 0) {//待接单
                    holder.order_type_tv.setText("等待商家接单");
                    holder.gray_btn.setVisibility(View.GONE);
                    holder.red_btn.setText("申请退款");
                    holder.red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onApplyRefund(orderModel, holder.getAdapterPosition());
                            }
                        }
                    });
                } else if (shippingStatus == 2) {//配送中
                    holder.order_type_tv.setText("配送中");
                    holder.gray_btn.setText("查看订单");
                    holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onOrderDetail(orderModel, holder.getAdapterPosition());
                            }
                        }
                    });
                    holder.red_btn.setVisibility(View.GONE);
                }else {  //商家端确认送达
                    holder.order_type_tv.setText("待自提");
                    holder.gray_btn.setText("联系商家");
                    holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onContactShop(orderModel, holder.getAdapterPosition());
                            }
                        }
                    });
                    holder.red_btn.setText("查看地图");
                    holder.red_btn.setTextColor(Color.parseColor("#666666"));
                    holder.red_btn.setBackgroundResource(R.drawable.shape_gray_line_white_background);
                    holder.red_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onSeeMapDetail(orderModel, holder.getAdapterPosition());
                            }
                        }
                    });
                }
                break;
            //已完成-已评价
            case 5:
                holder.order_type_tv.setText("已完成");
                holder.gray_btn.setText("删除订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeteleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });

                holder.red_btn.setText("查看评价");
                holder.red_btn.setTextColor(Color.parseColor("#666666"));
                holder.red_btn.setBackgroundResource(R.drawable.shape_gray_line_white_background);
                holder.red_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderPingLunDetail(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                break;
            //已完成-未评价
            case 3:
                holder.order_type_tv.setText("已完成");
                holder.gray_btn.setText("删除订单");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeteleOrder(orderModel, holder.getAdapterPosition());
                        }
                    }
                });

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
            case 8:
            case 4:
                holder.order_type_tv.setText("退款中");
                holder.gray_btn.setText("退款进度");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onTuiKuaiJinDu(orderModel, holder.getAdapterPosition());
                        }
                    }
                });

                holder.red_btn.setVisibility(View.GONE);
                break;
            //已退款
            case 6:
                holder.order_type_tv.setText("已退款");
                holder.gray_btn.setText("退款详情");
                holder.gray_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderTuiKuaiDetail(orderModel, holder.getAdapterPosition());
                        }
                    }
                });
                holder.red_btn.setVisibility(View.GONE);
                break;
                //商家拒单
            case 9:
                holder.order_type_tv.setText("商家拒单");
                holder.gray_btn.setVisibility(View.GONE);
                holder.red_btn.setVisibility(View.GONE);
                break;
            default:
                holder.order_type_tv.setText("未知状态，状态码" + orderState);
                break;
        }

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

        //查看订单详情
        void onOrderDetail(OrderModel orderModel, int positions);

        //退款
        void onOrderTuiKuaiDetail(OrderModel orderModel, int positions);

        //删除订单
        void onDeteleOrder(OrderModel orderModel, int positions);

        //评论订单
        void onPingLunOrder(OrderModel orderModel, int positions);

        //差看评论
        void onOrderPingLunDetail(OrderModel orderModel, int positions);

        //退款进度
        void onTuiKuaiJinDu(OrderModel orderModel, int position);

        //申请退款
        void onApplyRefund(OrderModel orderModel, int position);

        //联系商家
        void onContactShop(OrderModel orderModel, int position);

        //查看地图
        void onSeeMapDetail(OrderModel orderModel, int position);
    }

    private IOrderClickStatus listener;

    public void setListener(IOrderClickStatus listener) {
        this.listener = listener;
    }
}
