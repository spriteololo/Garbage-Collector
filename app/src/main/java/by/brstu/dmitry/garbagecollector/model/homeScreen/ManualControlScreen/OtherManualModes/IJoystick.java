package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes;

import by.brstu.dmitry.garbagecollector.model.BaseActionsInterface;

public interface IJoystick {
    void setActions(Actions joystickPresenter);

    interface Actions extends BaseActionsInterface {

    }
}
