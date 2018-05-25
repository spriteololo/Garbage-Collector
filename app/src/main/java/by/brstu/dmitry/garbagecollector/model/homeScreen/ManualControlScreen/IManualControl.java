package by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen;

import by.brstu.dmitry.garbagecollector.model.BaseActionsInterface;
import by.brstu.dmitry.garbagecollector.model.homeScreen.HomeScreenInteractor;

public interface IManualControl{
    void setActions(Actions manualControlPresenter);

    void openLid();
    void closeLid();

    void checkBaseData();

    interface Actions extends BaseActionsInterface {

    }
}
