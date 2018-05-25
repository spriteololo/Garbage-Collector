package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.BaseInteractor;

public class JoystickInteractor implements IJoystick {

    @Inject
    BaseInteractor baseInteractor;

    @Inject
    RequestInterface requestInterface;

    private IJoystick.Actions actions;

    public JoystickInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(Actions joystickPresenter) {
        this.actions = joystickPresenter;
    }
}
