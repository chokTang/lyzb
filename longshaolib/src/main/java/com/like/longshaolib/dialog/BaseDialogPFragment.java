package com.like.longshaolib.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.like.longshaolib.base.presenter.BasePresenter;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

/**
 * 基础的DialogFragmnt
 *
 * @author longshao 2018年9月5日 10:47:19
 */
public abstract class BaseDialogPFragment<P extends BasePresenter> extends BaseDialogFragment {
    protected P mPresenter;

    public BaseDialogPFragment() {
        try {
            if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
                Class<P> pClass = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
                Constructor[] constructors = pClass.getDeclaredConstructors();
                constructors[0].setAccessible(true);
                mPresenter = pClass.newInstance();
                mPresenter.attachView(this);
            }
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachContext(getContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected void onDailogDismiss() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDailogDismiss();
    }
}
