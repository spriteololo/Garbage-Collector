package by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode;

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

import static android.content.Context.SENSOR_SERVICE;


public class CompassFragment extends BaseMvpFragment implements CompassView, SensorEventListener, CompassCustomView.RotationCallback {

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
        compassView.setRotationCallback(this);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("LastRot", compassView.getLastRotation() + "");
                compassView.clearLastRotation();
            }
        });

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.beginStudying(compassView);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        compassView.setCurrentDegree(degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void rotationStart() {
        Log.e("TIME2", "START");
    }

    @Override
    public void rotationEnd(final long millis, final float rotation) {
        Log.e("TIME2", "END");
        Log.e("TIME2", millis + " " + rotation + "degrees");
    }
}
