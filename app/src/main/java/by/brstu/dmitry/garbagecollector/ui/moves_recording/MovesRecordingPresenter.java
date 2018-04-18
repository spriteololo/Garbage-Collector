package by.brstu.dmitry.garbagecollector.ui.moves_recording;

import com.arellomobile.mvp.InjectViewState;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;

@InjectViewState
public class MovesRecordingPresenter extends BaseMvpPresenter<MovesRecordingView> {

    public MovesRecordingPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }
}
