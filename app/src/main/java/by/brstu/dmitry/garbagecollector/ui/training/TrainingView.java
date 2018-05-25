package by.brstu.dmitry.garbagecollector.ui.training;

import com.arellomobile.mvp.MvpView;


public interface TrainingView extends MvpView {
    void startListening();
    void stopListening();

    void updateTrainingProgress(short v);

    void updateEvaluation(short i);

    void showLogData();
}
