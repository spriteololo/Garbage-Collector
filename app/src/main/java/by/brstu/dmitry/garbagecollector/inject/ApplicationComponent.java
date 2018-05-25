package by.brstu.dmitry.garbagecollector.inject;

import javax.inject.Singleton;

import by.brstu.dmitry.garbagecollector.inject.modules.DetailsModule;
import by.brstu.dmitry.garbagecollector.inject.modules.InteractorsModule;
import by.brstu.dmitry.garbagecollector.inject.modules.NetworkModule;
import by.brstu.dmitry.garbagecollector.inject.modules.RetrofitModule;
import by.brstu.dmitry.garbagecollector.inject.modules.RootModule;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.ManualControlInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.CompassInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.JoystickInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.StelsInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ObjectFollowingScreen.ObjectFollowingInteractor;
import by.brstu.dmitry.garbagecollector.model.trainingScreen.TrainingInteractor;
import by.brstu.dmitry.garbagecollector.ui.home.HomePresenter;
import by.brstu.dmitry.garbagecollector.ui.home.HomeScreenActivity;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlPresenter;
import by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode.CompassFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode.CompassPresenter;
import by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode.JoystickFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode.JoystickPresenter;
import by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode.StelsFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode.StelsPresenter;
import by.brstu.dmitry.garbagecollector.ui.moves_recording.MovesRecordingFragment;
import by.brstu.dmitry.garbagecollector.ui.moves_recording.MovesRecordingPresenter;
import by.brstu.dmitry.garbagecollector.ui.object_following.ObjectFollowingFragment;
import by.brstu.dmitry.garbagecollector.ui.object_following.ObjectFollowingPresenter;
import by.brstu.dmitry.garbagecollector.ui.training.TrainingActivity;
import by.brstu.dmitry.garbagecollector.ui.training.TrainingPresenter;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RetrofitModule.class, DetailsModule.class,
        RootModule.class, InteractorsModule.class})
public interface ApplicationComponent {
    void inject(ManualControlFragment manualControlFragment);

    void inject(ManualControlPresenter manualControlPresenter);

    void inject(HomePresenter homePresenter);

    void inject(HomeScreenActivity homeScreenActivity);

    void inject(JoystickFragment joystickFragment);

    void inject(StelsFragment stelsFragment);

    void inject(StelsPresenter stelsPresenter);

    void inject(JoystickPresenter joystickPresenter);

    void inject(MovesRecordingPresenter movesRecordingPresenter);

    void inject(MovesRecordingFragment movesRecordingFragment);

    void inject(ObjectFollowingFragment objectFollowingFragment);

    void inject(ObjectFollowingPresenter objectFollowingPresenter);

    void inject(CompassFragment compassFragment);

    void inject(TrainingActivity trainingActivity);

    void inject(TrainingPresenter trainingPresenter);

    void inject(TrainingInteractor trainingInteractor);

    void inject(ObjectFollowingInteractor objectFollowingInteractor);

    void inject(ManualControlInteractor manualControlInteractor);

    void inject(StelsInteractor stelsInteractor);

    void inject(JoystickInteractor joystickInteractor);

    void inject(CompassPresenter compassPresenter);

    void inject(CompassInteractor compassInteractor);
}