package by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode;

import com.arellomobile.mvp.MvpView;

import io.reactivex.functions.Consumer;

public interface StelsView extends MvpView {
    void movingAvailability(final boolean a);
}
