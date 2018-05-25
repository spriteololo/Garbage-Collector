package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.BaseInteractor;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class StelsInteractor implements IStels {

    @Inject
    BaseInteractor baseInteractor;

    @Inject
    RequestInterface requestInterface;

    private IStels.Actions actions;

    public StelsInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(IStels.Actions stelsPresenter) {
        this.actions = stelsPresenter;
    }

    @Override
    public void moveRobot(int left, int right, int time) {
        final Observable<ResponseBody> a =
                requestInterface.move(left >= 0 ? 1 : 0,
                        Math.abs(left),
                        right >= 0 ? 1 : 0,
                        Math.abs(right),
                        time)
                        .subscribeOn(Schedulers.io());
        baseInteractor.move(a, actions);
    }
}
