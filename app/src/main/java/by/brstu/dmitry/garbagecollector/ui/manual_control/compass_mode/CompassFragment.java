package by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.compass.CompassCustomView;
import by.brstu.dmitry.garbagecollector.ui.training.TrainingActivity;

import static android.content.Context.SENSOR_SERVICE;


public class CompassFragment extends BaseMvpFragment implements CompassView {

    @InjectPresenter
    CompassPresenter presenter;

    @BindView(R.id.manual_compass_view)
    CompassCustomView compassView;

    @BindView(R.id.clear)
    Button btnClear;

    @BindView(R.id.manual_compass_begin_studying)
    Button btnBegin;

    private SensorManager mSensorManager;

    public static CompassFragment getInstance(final int position) {
        CompassFragment compassFragment = new CompassFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extras.POSITION_KEY, position);
        compassFragment.setArguments(args);
        return compassFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);

        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compass, container, false);
    }

    @Override
    protected void onViewsBinded() {
        compassView.setRotationCallback(presenter);

        btnClear.setOnClickListener(view -> compassView.clearLastRotation());

        btnBegin.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), TrainingActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onPause() {
        stopListening();
        super.onPause();
    }

    @Override
    public void startListening() {
        mSensorManager.registerListener(presenter,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void stopListening() {
        mSensorManager.unregisterListener(presenter);
    }

    @Override
    public void setDataOnCompass(final float degree) {
        if (compassView != null) {
            compassView.setCurrentDegree(degree);
        }
    }

    @Override
    public void clearRotation() {
        compassView.clearLastRotation();
    }
}
