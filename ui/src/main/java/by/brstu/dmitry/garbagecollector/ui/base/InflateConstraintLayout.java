package by.brstu.dmitry.garbagecollector.ui.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

public abstract class InflateConstraintLayout extends ConstraintLayout {

    public InflateConstraintLayout(final Context context) {
        super(context);
    }

    public InflateConstraintLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InflateConstraintLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        View.inflate(context, getViewLayout(), this);
        onCreateView(context, attrs);
    }

    protected abstract void onCreateView(final Context pContext, final AttributeSet pAttrs);

    @LayoutRes
    protected abstract int getViewLayout();
}
