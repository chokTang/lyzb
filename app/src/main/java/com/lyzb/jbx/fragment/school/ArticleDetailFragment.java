package com.lyzb.jbx.fragment.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.like.longshaolib.base.BaseStatusToolbarFragment;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.school.ArticleDetailModel;
import com.lyzb.jbx.mvp.presenter.school.ArticleDetailPresenter;
import com.lyzb.jbx.mvp.view.school.IArticleDeatilView;

/**
 * 文章详情接口
 */
public class ArticleDetailFragment extends BaseStatusToolbarFragment<ArticleDetailPresenter> implements IArticleDeatilView,
        View.OnClickListener, Toolbar.OnMenuItemClickListener {

    //参数
    private static final String PARAMS_ARTICLE_ID = "PARAMS_ARTICLE_ID";
    private String mArticleId = "";

    private TextView tv_article_title;
    private TextView tv_article_from;
    private TextView tv_article_time;
    private TextView tv_article_content;
    private TextView tv_read_number;
    private TextView tv_zan_number;

    private ArticleDetailModel mModel;

    public static ArticleDetailFragment newIntance(String articleId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mArticleId = args.getString(PARAMS_ARTICLE_ID);
        }
    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_article_detail;
    }

    @Override
    public void onLazyRequest() {
        mPresenter.getArticleDetail(mArticleId);
    }

    @Override
    public void onAgainRequest() {
        mPresenter.getArticleDetail(mArticleId);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("文章详情");
        tv_article_title = findViewById(R.id.tv_article_title);
        tv_article_from = findViewById(R.id.tv_article_from);
        tv_article_time = findViewById(R.id.tv_article_time);
        tv_article_content = findViewById(R.id.tv_article_content);
        tv_read_number = findViewById(R.id.tv_read_number);
        tv_zan_number = findViewById(R.id.tv_zan_number);

        tv_zan_number.setOnClickListener(this);
//        mToolbar.inflateMenu(R.menu.share_union_menu);
//        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            //分享
            case R.id.btn_share:
                showToast("分享");
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        CommonUtil.isFastDoubleClick();
        switch (v.getId()) {
            //点赞
            case R.id.tv_zan_number:
                if (mModel != null) {
                    tv_zan_number.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), R.anim.zan_anim));
                    mPresenter.zan(mArticleId, mModel.isZan() ? 2 : 1);
                }
                break;
        }
    }

    @Override
    public void onArticleResult(ArticleDetailModel model) {
        if (model == null) return;
        mModel = model;
        tv_article_title.setText(model.getTitle());
        tv_article_from.setText(model.getAuthor());
        tv_article_time.setText(DateUtil.StringToString(model.getAddTime(), DateStyle.YYYY_MM_DD_CN));
        tv_article_content.setText(Html.fromHtml(model.getContent()));
        tv_read_number.setText(String.format("%d人阅读", model.getArticleReadnum()));
        tv_zan_number.setText(model.getArticleReadnum() == 0 ? "赞" : String.valueOf(model.getArticleReadnum()));
    }

    @Override
    public void onZanReuslt() {
        if (mModel.isZan()) {
            mModel.setArticleReadnum(mModel.getArticleReadnum() - 1);
        } else {
            mModel.setArticleReadnum(mModel.getArticleReadnum() + 1);
        }
        tv_zan_number.setText(mModel.getArticleReadnum() == 0 ? "赞" : String.valueOf(mModel.getArticleReadnum()));
        mModel.setZan(!mModel.isZan());
    }
}
