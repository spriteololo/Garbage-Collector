package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes;

import by.brstu.dmitry.garbagecollector.model.BaseActionsInterface;

public interface IStels {
    void setActions(Actions stelsPresenter);

    void moveRobot(int left, int right, int time);

    interface Actions extends BaseActionsInterface {
    }
}
