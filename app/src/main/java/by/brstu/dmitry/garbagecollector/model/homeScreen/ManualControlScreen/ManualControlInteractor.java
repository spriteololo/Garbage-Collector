package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.BaseInteractor;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ManualControlInteractor implements IManualControl {

    @Inject
    BaseInteractor baseInteractor;

    @Inject
    RequestInterface requestInterface;

    private IManualControl.Actions actions;

    public ManualControlInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(Actions manualControlPresenter) {
        this.actions = manualControlPresenter;
    }

    @Override
    public void openLid() {
        final Observable<ResponseBody> a = requestInterface.openLid()
                .subscribeOn(Schedulers.io());
        baseInteractor.openLid(a, actions);
    }

    @Override
    public void closeLid() {
        final Observable<ResponseBody> a = requestInterface.closeLid()
                .subscribeOn(Schedulers.io());
        baseInteractor.closeLid(a, actions);
    }

    @Override
    public void checkBaseData() {
        final Observable<ResponseBody> a = requestInterface.refreshBaseData()
                .subscribeOn(Schedulers.io());
        baseInteractor.getBaseData(a, actions);
    }
}
