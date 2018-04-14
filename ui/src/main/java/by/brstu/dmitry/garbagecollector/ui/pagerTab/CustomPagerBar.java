package by.brstu.dmitry.garbagecollector.ui.pagerTab;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomPagerBar extends PagerTabStrip {

    Guideline guideLine;

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
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:

                float getP = event.getYPrecision() * event.getY();
                //TODO
                Log.i("Motion", "getPre " + getP);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideLine.getLayoutParams();
                /*params.guideBegin =
                guideLine.setLayoutParams(params);*/
                break;
        }

        return super.onTouchEvent(event);
    }
}
