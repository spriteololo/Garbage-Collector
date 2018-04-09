package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.support.v4.widget.NestedScrollView;

public abstract class BlurScrollListener implements NestedScrollView.OnScrollChangeListener {

    @Override
    public void onScrollChange(final NestedScrollView v, final int scrollX, final int scrollY, final int oldScrollX, final int oldScrollY) {
        boolean clickable = true;
        float transparencyValue = 0;

        transparencyValue += scrollY / 10;
        transparencyValue /= 100;

        if (transparencyValue > 0.1)
            clickable = false;

        if (transparencyValue < 0)
            transparencyValue = 0;

        if (transparencyValue > 1)
            transparencyValue = 1;

        changeBlurAndTransparentLevel(transparencyValue, clickable);
    }

    public abstract void changeBlurAndTransparentLevel(float transparencyValue, boolean clickable);
}
