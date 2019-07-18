package com.lyzb.jbx.fragment.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.common.ColorAdapter;
import com.lyzb.jbx.adapter.common.SizeAdapter;
import com.lyzb.jbx.util.richtext.RichEditor;

/**
 * 富文本编辑器
 */
public class HomeHtmlTextFragment extends BaseFragment implements View.OnClickListener {

    //参数 返回去的参数是data
    public static final String PARAMS_VALUE = "params_value";
    public static final String TYPE_TITLE = "TYPE_TITLE";
    public static final String KEY_DATA = "data";
    private String mValue = "";
    private String mTitle = null;
    private ImageView statistics_back_iv;
    private TextView statistics_title_tv;
    private TextView statistics_right_tv;
    private RichEditor re_main_editor;
    private LinearLayout ll_main_color;
    private RecyclerView recycle_color;
    private RecyclerView recycle_textSize;

    //底部的操作按钮
    private TextView button_image;
    private ImageView button_bold;
    private TextView button_text_color;
    private ImageView button_list_ol;
    private ImageView button_list_ul;
    private ImageView button_underline;
    private ImageView button_italic;
    private ImageView button_align_left;
    private ImageView button_align_center;
    private ImageView button_align_right;
    private ImageView action_strikethrough;
    private TextView tv_main_preview;
    private TextView button_text_size;

    //颜色值
    private ColorAdapter colorAdapter;
    //字体适配器
    private SizeAdapter sizeAdapter;

    //折叠视图的宽高
    private int mFoldedViewMeasureHeight = DensityUtil.dpTopx(48);
    private boolean isAnimating = false;

    public static HomeHtmlTextFragment newIntance(String value) {
        HomeHtmlTextFragment fragment = new HomeHtmlTextFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeHtmlTextFragment newIntance(String title, String text) {
        HomeHtmlTextFragment fragment = new HomeHtmlTextFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_VALUE, text);
        args.putString(TYPE_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mValue = args.getString(PARAMS_VALUE);
            mTitle = args.getString(TYPE_TITLE);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        statistics_back_iv = (ImageView) findViewById(R.id.statistics_back_iv);
        statistics_title_tv = (TextView) findViewById(R.id.statistics_title_tv);
        statistics_right_tv = (TextView) findViewById(R.id.statistics_right_tv);
        re_main_editor = (RichEditor) findViewById(R.id.re_main_editor);
        ll_main_color = (LinearLayout) findViewById(R.id.ll_main_color);
        button_image = (TextView) findViewById(R.id.button_image);
        button_bold = (ImageView) findViewById(R.id.button_bold);
        button_text_color = (TextView) findViewById(R.id.button_text_color);
        button_list_ol = (ImageView) findViewById(R.id.button_list_ol);
        button_list_ul = (ImageView) findViewById(R.id.button_list_ul);
        button_underline = (ImageView) findViewById(R.id.button_underline);
        button_italic = (ImageView) findViewById(R.id.button_italic);
        button_align_left = (ImageView) findViewById(R.id.button_align_left);
        button_align_center = (ImageView) findViewById(R.id.button_align_center);
        button_align_right = (ImageView) findViewById(R.id.button_align_right);
        action_strikethrough = (ImageView) findViewById(R.id.action_strikethrough);
        tv_main_preview = (TextView) findViewById(R.id.tv_main_preview);
        recycle_color = (RecyclerView) findViewById(R.id.recycle_color);
        recycle_textSize = (RecyclerView) findViewById(R.id.recycle_textSize);
        button_text_size = (TextView) findViewById(R.id.button_text_size);

        statistics_back_iv.setOnClickListener(this);
        statistics_right_tv.setOnClickListener(this);
        button_image.setOnClickListener(this);
        button_bold.setOnClickListener(this);
        button_text_color.setOnClickListener(this);
        button_list_ol.setOnClickListener(this);
        button_list_ul.setOnClickListener(this);
        button_underline.setOnClickListener(this);
        button_italic.setOnClickListener(this);
        button_align_left.setOnClickListener(this);
        button_align_center.setOnClickListener(this);
        button_align_right.setOnClickListener(this);
        action_strikethrough.setOnClickListener(this);
        tv_main_preview.setOnClickListener(this);
        button_text_size.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        initEditor();
//        statistics_title_tv.setText("编辑文字");
        statistics_title_tv.setText(mTitle);
        statistics_right_tv.setText("保存");
        statistics_right_tv.setVisibility(View.VISIBLE);

        colorAdapter = new ColorAdapter(getContext());
        colorAdapter.setLayoutManager(recycle_color, LinearLayoutManager.HORIZONTAL);
        recycle_color.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_window_10));
        recycle_color.setAdapter(colorAdapter);
        recycle_color.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                re_main_editor.setTextColor(Color.parseColor(colorAdapter.getPositionModel(position)));
                button_text_color.setBackgroundColor(Color.parseColor(colorAdapter.getPositionModel(position)));
                animateClose(ll_main_color);
            }
        });

        sizeAdapter = new SizeAdapter(getContext());
        sizeAdapter.setLayoutManager(recycle_textSize, LinearLayoutManager.HORIZONTAL);
        recycle_textSize.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_window_10));
        recycle_textSize.setAdapter(sizeAdapter);
        recycle_textSize.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                re_main_editor.setFontSize(position + 1);
                button_text_size.setText(String.format("H%d", position + 1));
                animateClose(ll_main_color);
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_home_html_text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statistics_back_iv:
                pop();
                break;
            //完成按钮
            case R.id.statistics_right_tv:
                Bundle args = new Bundle();
                args.putString(KEY_DATA, re_main_editor.getHtml());
                setFragmentResult(RESULT_OK, args);
                pop();
                break;
            //插入图片
            case R.id.button_image:
                break;
            //加粗
            case R.id.button_bold:
                if (CommonUtil.converToT(button_bold.getTag().toString(), false)) {
                    button_bold.setTag(false);
                    button_bold.setImageResource(R.mipmap.bold);
                } else {
                    button_bold.setTag(true);
                    button_bold.setImageResource(R.mipmap.bold_);
                }
                re_main_editor.setBold();
                break;
            //选择字体大小
            case R.id.button_text_size:
                if (recycle_color.getVisibility() == View.VISIBLE && ll_main_color.getVisibility() == View.VISIBLE) {
                    recycle_color.setVisibility(View.GONE);
                    recycle_textSize.setVisibility(View.VISIBLE);
                    return;
                }
                recycle_color.setVisibility(View.GONE);
                recycle_textSize.setVisibility(View.VISIBLE);
                if (isAnimating) return;
                isAnimating = true;
                if (ll_main_color.getVisibility() == View.GONE) {
                    //打开动画
                    animateOpen(ll_main_color);
                } else {
                    //关闭动画
                    animateClose(ll_main_color);
                }
                break;
            //选择颜色值
            case R.id.button_text_color:
                if (recycle_textSize.getVisibility() == View.VISIBLE && ll_main_color.getVisibility() == View.VISIBLE) {
                    recycle_color.setVisibility(View.VISIBLE);
                    recycle_textSize.setVisibility(View.GONE);
                    return;
                }
                recycle_color.setVisibility(View.VISIBLE);
                recycle_textSize.setVisibility(View.GONE);
                if (isAnimating) return;
                isAnimating = true;
                if (ll_main_color.getVisibility() == View.GONE) {
                    //打开动画
                    animateOpen(ll_main_color);
                } else {
                    //关闭动画
                    animateClose(ll_main_color);
                }
                break;
            //段落 123 这种
            case R.id.button_list_ol:
                if (CommonUtil.converToT(button_list_ol.getTag().toString(), false)) {
                    button_list_ol.setTag(false);
                    button_list_ol.setImageResource(R.mipmap.list_ol);
                } else {
                    button_list_ol.setTag(true);
                    button_list_ol.setImageResource(R.mipmap.list_ol_);
                }
                re_main_editor.setNumbers();
                break;
            //段落 。。。这种
            case R.id.button_list_ul:
                if (CommonUtil.converToT(button_list_ul.getTag().toString(), false)) {
                    button_list_ul.setTag(false);
                    button_list_ul.setImageResource(R.mipmap.list_ul);
                } else {
                    button_list_ul.setTag(true);
                    button_list_ul.setImageResource(R.mipmap.list_ul_);
                }
                re_main_editor.setBullets();
                break;
            //下划线
            case R.id.button_underline:
                if (CommonUtil.converToT(button_underline.getTag().toString(), false)) {
                    button_underline.setTag(false);
                    button_underline.setImageResource(R.mipmap.underline);
                } else {
                    button_underline.setTag(true);
                    button_underline.setImageResource(R.mipmap.underline_);
                }
                re_main_editor.setUnderline();
                break;
            //斜体字
            case R.id.button_italic:
                if (CommonUtil.converToT(button_italic.getTag().toString(), false)) {
                    button_italic.setTag(false);
                    button_italic.setImageResource(R.mipmap.lean);
                } else {
                    button_italic.setTag(true);
                    button_italic.setImageResource(R.mipmap.lean_);
                }
                re_main_editor.setItalic();
                break;
            //居左
            case R.id.button_align_left:
                if (CommonUtil.converToT(button_align_left.getTag().toString(), false)) {
                    button_align_left.setTag(false);
                    button_align_left.setImageResource(R.mipmap.align_left);
                } else {
                    button_align_left.setTag(true);
                    button_align_left.setImageResource(R.mipmap.align_left_);
                }
                re_main_editor.setAlignLeft();
                break;
            //居中
            case R.id.button_align_center:
                if (CommonUtil.converToT(button_align_center.getTag().toString(), false)) {
                    button_align_center.setTag(false);
                    button_align_center.setImageResource(R.mipmap.align_center);
                } else {
                    button_align_center.setTag(true);
                    button_align_center.setImageResource(R.mipmap.align_center_);
                }
                re_main_editor.setAlignCenter();
                break;
            //居右
            case R.id.button_align_right:
                if (CommonUtil.converToT(button_align_right.getTag().toString(), false)) {
                    button_align_right.setTag(false);
                    button_align_right.setImageResource(R.mipmap.align_right);
                } else {
                    button_align_right.setTag(true);
                    button_align_right.setImageResource(R.mipmap.align_right_);
                }
                re_main_editor.setAlignRight();
                break;
            //中横线
            case R.id.action_strikethrough:
                if (CommonUtil.converToT(action_strikethrough.getTag().toString(), false)) {
                    action_strikethrough.setTag(false);
                    action_strikethrough.setImageResource(R.mipmap.strikethrough);
                } else {
                    action_strikethrough.setTag(true);
                    action_strikethrough.setImageResource(R.mipmap.strikethrough_);
                }
                re_main_editor.setStrikeThrough();
                break;
            //清除格式
            case R.id.tv_main_preview:
                if (ll_main_color.getVisibility() == View.VISIBLE) {
                    //关闭动画
                    animateClose(ll_main_color);
                }

                if (CommonUtil.converToT(button_bold.getTag().toString(), false)) {
                    button_bold.setTag(false);
                    button_bold.setImageResource(R.mipmap.bold);
                }

                if (CommonUtil.converToT(button_underline.getTag().toString(), false)) {
                    button_underline.setTag(false);
                    button_underline.setImageResource(R.mipmap.underline);
                }

                if (CommonUtil.converToT(button_italic.getTag().toString(), false)) {
                    button_italic.setTag(false);
                    button_italic.setImageResource(R.mipmap.lean);
                }

                if (CommonUtil.converToT(button_align_left.getTag().toString(), false)) {
                    button_align_left.setTag(false);
                    button_align_left.setImageResource(R.mipmap.align_left);
                }
                if (CommonUtil.converToT(button_align_right.getTag().toString(), false)) {
                    button_align_right.setTag(false);
                    button_align_right.setImageResource(R.mipmap.align_right);
                }
                if (CommonUtil.converToT(button_align_center.getTag().toString(), false)) {
                    button_align_center.setTag(false);
                    button_align_center.setImageResource(R.mipmap.align_center);
                }

                re_main_editor.setAlignLeft();
                re_main_editor.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor1));
                button_text_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.fontcColor1));
                button_text_size.setText("H");
                re_main_editor.clearFormat();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化编辑框
     */
    private void initEditor() {
        re_main_editor.setHtml(mValue);
        re_main_editor.focusEditor();
        showSoftInput(re_main_editor);
        //re_main_editor.setEditorHeight(400);
        //输入框显示字体的大小
        re_main_editor.setEditorFontSize(14);
        //输入框显示字体的颜色
        re_main_editor.setEditorFontColor(ContextCompat.getColor(getContext(), R.color.fontcColor1));
        //输入框背景设置
        re_main_editor.setEditorBackgroundColor(Color.WHITE);
        //re_main_editor.setBackgroundColor(Color.BLUE);
        //re_main_editor.setBackgroundResource(R.drawable.bg);
        //re_main_editor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //输入框文本padding
        re_main_editor.setPadding(10, 10, 10, 10);
        //输入提示文本
        re_main_editor.setPlaceholder("请输入编辑内容");
        //是否允许输入
        //re_main_editor.setInputEnabled(false);
        //文本输入框监听事件
        re_main_editor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Log.e("mEditor", "html文本：" + text);
            }
        });
    }

    /**
     * 开启动画
     *
     * @param view 开启动画的view
     */
    private void animateOpen(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mFoldedViewMeasureHeight);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 关闭动画
     *
     * @param view 关闭动画的view
     */
    private void animateClose(final LinearLayout view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 创建动画
     *
     * @param view  开启和关闭动画的view
     * @param start view的高度
     * @param end   view的高度
     * @return ValueAnimator对象
     */
    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
