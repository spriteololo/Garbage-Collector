package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.BaseInteractor;

public class CompassInteractor implements ICompass {

    @Inject
    BaseInteractor baseInteractor;

    @Inject
    RequestInterface requestInterface;

    private ICompass.Actions actions;

    public CompassInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(Actions compassPresenter) {
        this.actions = compassPresenter;
    }
}
