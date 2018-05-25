package by.brstu.dmitry.garbagecollector.inject.modules;

import javax.inject.Singleton;

import by.brstu.dmitry.garbagecollector.model.BaseInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.IManualControl;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.ManualControlInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.CompassInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.ICompass;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.IJoystick;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.IStels;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.JoystickInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.StelsInteractor;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ObjectFollowingScreen.IObjectFollowing;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ObjectFollowingScreen.ObjectFollowingInteractor;
import by.brstu.dmitry.garbagecollector.model.trainingScreen.ITraining;
import by.brstu.dmitry.garbagecollector.model.trainingScreen.TrainingInteractor;
import dagger.Module;
import dagger.Provides;

@Module
public class InteractorsModule {

    @Provides
    @Singleton
    BaseInteractor provideBaseInteractor() {
        return new BaseInteractor();
    }

    @Provides
    @Singleton
    ITraining provideTraining() {
        return new TrainingInteractor();
    }

    @Provides
    @Singleton
    IObjectFollowing provideObjectFollowing() {
        return new ObjectFollowingInteractor();
    }

    @Provides
    @Singleton
    IManualControl provideManualControl() {
        return new ManualControlInteractor();
    }

    @Provides
    @Singleton
    IStels provideStels() {
        return new StelsInteractor();
    }

    @Provides
    @Singleton
    IJoystick provideJoystick() {
        return new JoystickInteractor();
    }

    @Provides
    @Singleton
    ICompass provideCompass() {
        return new CompassInteractor();
    }

}
