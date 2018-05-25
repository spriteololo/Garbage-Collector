package by.brstu.dmitry.garbagecollector.model.homeScreen.ObjectFollowingScreen;

import by.brstu.dmitry.garbagecollector.model.BaseActionsInterface;

public interface IObjectFollowing {
    void setActions(Actions objectFollowingPresenter);

    void moveRobot(int speed, int time);

    void getFrontInfra();
    void getBackInfra();


    interface Actions extends BaseActionsInterface {
    }
}
