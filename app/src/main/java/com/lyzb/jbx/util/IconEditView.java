package com.lyzb.jbx.util;

import android.content.Context;
import android.util.AttributeSet;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.internal.HasOnViewAttachListener;

public class IconEditView extends android.support.v7.widget.AppCompatEditText implements HasOnViewAttachListener{
    private HasOnViewAttachListener.HasOnViewAttachListenerDelegate delegate;

    public IconEditView(Context context) {
        super(context);
        init();
    }

    public IconEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTransformationMethod(null);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(Iconify.compute(getContext(), text, this), type);
    }

    @Override
    public void setOnViewAttachListener(HasOnViewAttachListener.OnViewAttachListener listener) {
        if (delegate == null)
            delegate = new HasOnViewAttachListener.HasOnViewAttachListenerDelegate(this);
        delegate.setOnViewAttachListener(listener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        delegate.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        delegate.onDetachedFromWindow();
    }
}
