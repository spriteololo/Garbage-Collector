package by.brstu.dmitry.garbagecollector.ui.home;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

public abstract class DrawerListener implements DrawerLayout.DrawerListener {

    @Override
    public void onDrawerSlide(final View drawerView, final float slideOffset) {

    }

    @Override
    public void onDrawerOpened(final View drawerView) {

    }

    @Override
    public void onDrawerClosed(final View drawerView) {
        onClosed();
    }

    @Override
    public void onDrawerStateChanged(final int newState) {

    }

    public abstract void onClosed();
}

