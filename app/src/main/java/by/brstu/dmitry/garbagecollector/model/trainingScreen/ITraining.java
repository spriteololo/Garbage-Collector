package by.brstu.dmitry.garbagecollector.model.trainingScreen;

import by.brstu.dmitry.garbagecollector.model.BaseActionsInterface;
import by.brstu.dmitry.garbagecollector.ui.training.TrainingPresenter;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface ITraining {
    void setActions(Actions trainingPresenter);

    void moveRobot(int left, int right, int time);

    interface Actions extends BaseActionsInterface {

    }
}
