package by.brstu.dmitry.garbagecollector.ui.pagerTab;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomPagerBar extends PagerTabStrip {

    Guideline guideLine;
    ConstraintLayout parent;
    float parentTop;
    float height;


    public void setParent(final ConstraintLayout parent) {
        this.parent = parent;
    }

    public void setGuideline(final Guideline guideline) {
        this.guideLine = guideline;
    }

    public CustomPagerBar(final Context context) {
        super(context);
    }

    public CustomPagerBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        parentTop = getRelativeTop((View) getParent().getParent());
        height = ((View) getParent().getParent()).getHeight() ;
        switch (event.getAction()) {


            case MotionEvent.ACTION_MOVE:
                float relative_y = event.getRawY() - parentTop;
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideLine.getLayoutParams();
                params.guidePercent = relative_y / height;
                guideLine.setLayoutParams(params);

                break;
        }

        return super.onTouchEvent(event);
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
}
