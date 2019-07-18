package com.lyzb.jbx.fragment.send

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.gson.Gson
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration
import com.like.longshaolib.base.BaseToolbarFragment
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.screen.DensityUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.send.ChosenAdapter
import com.lyzb.jbx.adapter.send.ProductAdapter
import com.lyzb.jbx.inter.IRecycleHolderAnyClilck
import com.lyzb.jbx.model.send.GoodsModel
import com.lyzb.jbx.mvp.presenter.send.ChoiceProductPresenter
import com.lyzb.jbx.mvp.view.send.IChoiceProductView
import com.lyzb.jbx.util.ListChangeIndexUtil
import com.szy.yishopcustomer.Util.App
import kotlinx.android.synthetic.main.fragment_choice_product.*
import kotlinx.android.synthetic.main.layout_title.*
import me.yokeyword.fragmentation.ISupportFragment
import java.io.Serializable
import java.util.*


/**
 * Created by :TYK
 * Date: 2019/5/8  9:48
 * Desc: 选择商品
 */
class ChoiceProductFragment : BaseToolbarFragment<ChoiceProductPresenter>(), IChoiceProductView, View.OnClickListener {


    companion object {
        const val KEY_PRODUCT = "key_select_product"
        const val KEY_TYPE = "key_select_type"
        const val KEY_HISTORY_PRODUCT = 78778
        fun newIntance(list: MutableList<GoodsModel>, type: String): ChoiceProductFragment {
            val fragment = ChoiceProductFragment()
            val bundle = Bundle()
            bundle.putString(KEY_TYPE, type)
            bundle.putSerializable(KEY_PRODUCT, list as Serializable)
            fragment.arguments = bundle
            return fragment
        }
    }

    var selectProductList: MutableList<GoodsModel> = arrayListOf()
    //缓存数据 点击完成就不管点击返回就 删除
    var cacheSelectList: MutableList<GoodsModel> = arrayListOf()
    var type = "" //这里判断是 图文跳转过来还是是  视频挑过来  用于历史记录那点 点击完成后跳转相应界面
    var defaultAdapter: ProductAdapter? = null
    var chosenAdapter: ChosenAdapter? = null
    var list: MutableList<GoodsModel> = arrayListOf()
    var helper: ItemTouchHelper? = null
    var keyword = ""

    override fun getResId(): Any {
        return R.layout.fragment_choice_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (null != bundle) {
            type = bundle.getString(KEY_TYPE)
            selectProductList = bundle.getSerializable(KEY_PRODUCT) as MutableList<GoodsModel>
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        mToolbar.setNavigationOnClickListener { pop(false) }
        setToolbarTitle("选择商品")
        tv_right.text = "完成"

        //商品列表
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_choice_product.layoutManager = linearLayoutManager
        defaultAdapter = ProductAdapter()
        rv_choice_product.adapter = defaultAdapter


        //已选择列表
        val gridLayoutManager = GridLayoutManager(activity, 5)
        rv_chosen.layoutManager = gridLayoutManager
        chosenAdapter = ChosenAdapter()
        rv_chosen.adapter = chosenAdapter
        chosenAdapter?.setNewData(selectProductList)
        rv_chosen.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10))

        edt_search_value.setOnTextChangeListener {
            keyword = it
            if (!TextUtils.isEmpty(it)) {
                mPresenter.searchProduct(true, it)
            }
        }

        refresh_choice_product.setOnRefreshListener {
            if (TextUtils.isEmpty(keyword)) {
                mPresenter.defaultProduct(true)
            } else {
                mPresenter.searchProduct(true, keyword)
            }
        }
        refresh_choice_product.setOnLoadMoreListener {
            if (TextUtils.isEmpty(keyword)) {
                mPresenter.defaultProduct(false)
            } else {
                mPresenter.searchProduct(false, keyword)
            }
        }

        defaultAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val choiceBean = adapter.data[position] as GoodsModel
            when (view.id) {
                R.id.tv_choice -> {//选择
                    if (chosenAdapter!!.data.size < 5) {
                        if (!isContain(chosenAdapter!!.data, choiceBean)) {
                            choiceBean.isSelected = true
                            adapter.notifyItemChanged(position)
                            chosenAdapter!!.data.add(choiceBean)
                            //缓存数据 点击完成就不管点击返回就 删除
                            cacheSelectList.add(choiceBean)
                            chosenAdapter!!.notifyDataSetChanged()
                        } else {
                            showToast("你已经选择过该商品了")
                        }
                    } else {
                        showToast("最多只能选择5个商品")
                    }
                }
            }
        }

        chosenAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val bean = adapter.data[position] as GoodsModel
            when (view.id) {
                R.id.ll_delete -> {//删除  并且刷新默认列表中的数据
                    for (i in 0 until defaultAdapter!!.data.size) {
                        if (bean.goods_id == defaultAdapter!!.data[i].goods_id) {
                            val defaultBean = defaultAdapter!!.data[i] as GoodsModel
                            defaultBean.isSelected = false
                            defaultAdapter!!.notifyItemChanged(i)
                        }
                    }
                    adapter.remove(position)
                }
            }
        }

        helper = ItemTouchHelper(callback)
        helper?.attachToRecyclerView(rv_chosen)
        //长按时间
        chosenAdapter?.invoke(object : IRecycleHolderAnyClilck {
            override fun onItemLongClick(holder: RecyclerView.ViewHolder, position: Int, obj: Any): Boolean {
                helper?.startDrag(holder)
                return true
            }
        })

        tv_right.setOnClickListener(this)
        tv_history.setOnClickListener(this)

    }

    /**
     * 判断该商品是否被选择了
     * 根据ID来判断  因为其他状态有可能被改变了 所以model也改变了
     */
    fun isContain(list: MutableList<GoodsModel>, bean: GoodsModel): Boolean {
        var isContain = false
        for (i in 0 until list.size) {
            if (bean.goods_id == list[i].goods_id) {
                isContain = true
            }
        }
        return isContain
    }


    /**
     * 点击返回时候删除缓存数据  完成的时候不需要
     * 这里删除 以选中的和缓存中的 共同数据 删除，
     * 因为点击选中数据删除时候 有可能也删除了和缓存数据一样的数据
     */
    fun removeCacheList(chosenList: MutableList<GoodsModel>, cacheList: MutableList<GoodsModel>): MutableList<GoodsModel> {
        val list :MutableList<GoodsModel> = arrayListOf()
        list.addAll(cacheList)
        for (i in list.size - 1 downTo 0) {
            if (cacheList.size > 0) {
                for (j in 0 until  cacheList.size) {
                    if (list[i].goods_id == cacheList[j].goods_id) {
                         chosenList.removeAt(i)
                    }
                }
            }
        }
        return chosenList
    }

    var callback: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragflag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return ItemTouchHelper.Callback.makeMovementFlags(dragflag, 0)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            chosenAdapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            ListChangeIndexUtil.swap(chosenAdapter?.data, viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

        override fun canDropOver(recyclerView: RecyclerView?, current: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            return true
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                val layoutParams = RelativeLayout.LayoutParams(DensityUtil.dpTopx(70f), DensityUtil.dpTopx(70f))
                viewHolder!!.itemView.findViewById<ImageView>(R.id.img_product).layoutParams = layoutParams
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            val layoutParams = RelativeLayout.LayoutParams(DensityUtil.dpTopx(60f), DensityUtil.dpTopx(60f))
            viewHolder.itemView.findViewById<ImageView>(R.id.img_product).layoutParams = layoutParams
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }
    }


    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.defaultProduct(true)
    }

    /**
     * 默认数据请求成功
     */
    override fun defaultSuccess(isRefresh: Boolean, list1: MutableList<GoodsModel>) {
        //这里是 刷新后查看 哪些商品是已经被选中  从而刷新默认列表 选中效果
        if (isRefresh) {// 刷新
            refresh_choice_product.finishRefresh()
            if(list1!=null&&list1.size>0){
                this.list.clear()
                this.list.addAll(list1)
            }
        } else {//加载
            refresh_choice_product.finishLoadMore()
            this.list.addAll(list1)
            if (list1.size < App.PageSize) {//添加完数据
                refresh_choice_product.finishLoadMoreWithNoMoreData()
            }
        }
        if (this.list.size == 0) {//没有数据
            val emptyView = LayoutInflater.from(context).inflate(R.layout.empty_layout, null, false)
            defaultAdapter!!.emptyView = emptyView
        } else {//有数据
            for (i in 0 until chosenAdapter!!.data.size) {
                val bean = chosenAdapter!!.data[i] as GoodsModel
                if (bean.isSelected) {//选中了
                    for (j in 0 until this.list.size) {
                        if (bean.goods_id == this.list[j].goods_id) {
                            this.list[j].isSelected = true
                        }
                    }
                }
            }
            defaultAdapter!!.setNewData(this.list)
        }
    }

    /**
     * 默认数据请求失败
     */
    override fun defaultFail(isRefresh: Boolean) {
        if (isRefresh) {
            refresh_choice_product.finishRefresh()
        } else {
            refresh_choice_product.finishLoadMore()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right -> {//完成
                pop(true)
            }

            R.id.tv_history -> {//历史选择
                startForResult(HistoryChoiceProductFragment.newIntance(chosenAdapter?.data!!, type), KEY_HISTORY_PRODUCT)
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        val gson = Gson()
        var listString = ""
        if (chosenAdapter!!.data.size > 0) {
            listString = gson.toJson(removeCacheList(chosenAdapter!!.data, cacheSelectList))
        }
        val bundle = Bundle()
        bundle.putString(KEY_PRODUCT, listString)
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }

    /**
     * 点击完成和点击返回键的回退操作不一样
     * true  是完成  false 是点击返回
     */
    fun pop(boolean: Boolean) {
        val gson = Gson()
        var listString = ""
        if (boolean) {
            listString = gson.toJson(chosenAdapter!!.data)
        } else {
            if (chosenAdapter!!.data.size > 0) {
                listString = gson.toJson(removeCacheList(chosenAdapter!!.data, cacheSelectList))
            }
        }

        val bundle = Bundle()
        bundle.putString(KEY_PRODUCT, listString)
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        pop()
    }


    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (data != null && resultCode == ISupportFragment.RESULT_OK) {
            when (requestCode) {
                KEY_HISTORY_PRODUCT -> {//选择商品数据回调
                    selectProductList = GSONUtil.getEntityList(data.getString(KEY_PRODUCT), GoodsModel::class.java)
                    chosenAdapter?.setNewData(selectProductList)
                    //刷新默认列表中的  选中效果
                    if (TextUtils.isEmpty(keyword)) {
                        mPresenter.defaultProduct(true)
                    } else {
                        mPresenter.searchProduct(true, keyword)
                    }
                }
            }
        }

    }

}