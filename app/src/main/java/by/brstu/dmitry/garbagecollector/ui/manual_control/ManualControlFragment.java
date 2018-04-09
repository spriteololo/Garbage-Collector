package by.brstu.dmitry.garbagecollector.ui.manual_control;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseActivity;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.home.HomeScreenActivity;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import static android.content.Context.SENSOR_SERVICE;


public class ManualControlFragment extends BaseMvpFragment implements View.OnClickListener, SensorEventListener, ManualControlView {
    private boolean isOn = false;

    SensorManager sensorManager;

    @InjectPresenter
    ManualControlPresenter manualControlPresenter;

    public static ManualControlFragment getInstance() {
        return new ManualControlFragment();
    }

    @BindView(R.id.mmm)
    TextView tv;

    @BindView(R.id.joystickView)
    JoystickView joystick;

    @BindView(R.id.btn_start_gyro)
    Button btnGyro;

    @Inject
    RequestInterface requestInterface;

    View view;

    private float[] sensorFirstEvent = new float[3];

    float[] valuesResult = new float[3];
    short calibrator;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manual_control, container, false);
        return view;

    }

    @Override
    protected void onViewsBinded() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        btnGyro.setOnClickListener(this);

        if (actionBar != null) {
            actionBar.setTitle(R.string.manual_control);
        }

        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(final int angle, final int strength) {
                int rightDir = 0;
                final int leftDir = 0;
                int rightSpeed = 0;
                int leftSpeed = 0;
                final int mainSpeed = (int) Math.floor(strength * 2.5);

                if (angle > 90 && angle <= 165) {
                    rightSpeed = mainSpeed;
                    rightDir = 0;

                    leftSpeed = mainSpeed - ((angle - 90) * 90 / 75);
                }

                if (angle > 165 && angle <= 165) {
                    rightSpeed = (int) Math.floor(strength * 2.5);
                    rightDir = 0;
                }

                /*if (angle < 180) {//forward
                    requestInterface.forward(0, 0, 0, 0, 0)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {


                                    Log.i("FORWARD", System.currentTimeMillis() + "");
                                    try {
                                        tv.setText(responseBody.string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                    Log.e("FORWARD", System.currentTimeMillis() + "" + e.toString());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    requestInterface.backward()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    Log.i("BACKWARD", System.currentTimeMillis() + "");
                                    try {
                                        tv.setText(responseBody.string());

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                    Log.e("BACKWARD", System.currentTimeMillis() + "" + e.toString());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }*/
            }
        }, 500);

    }


    @Override
    public void onClick(final View view) {

        isOn = !isOn;

        if (isOn) {
            sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            if (sensorManager != null) {

                final Sensor sensorRotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

                sensorManager.registerListener(this, sensorRotation, SensorManager.SENSOR_DELAY_NORMAL);

            }
            calibrator = 10;
        } else {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {

        System.arraycopy(sensorEvent.values, 0, valuesResult, 0, 3);

        if (calibrator <= 0) {
            final float[] toSend = new float[3];
            for (int i = 0; i < valuesResult.length; i++) {

                toSend[i] = valuesResult[i] - sensorFirstEvent[i]; // x - наклон вперед - (0; -1), наклон назад (0; +1)
                // y - наклон влево - (0; -1), наклон вправо (0; +1)
                // z - поворот по часовой стрелке - (0; -1), поворот против часовой стрелки  (0; +1)

                if (toSend[i] > 1) {
                    toSend[i] -= 2;
                } else {
                    if (toSend[i] < -1) {
                        toSend[i] += 2;
                    }
                }
            }

            Log.e("FUCK", Arrays.toString(toSend));
            manualControlPresenter.sendData(toSend);
        } else {
            calibrator--;
            System.arraycopy(valuesResult, 0, sensorFirstEvent, 0, valuesResult.length);
        }
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int i) {
        if (i == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            Snackbar.make(view, "Unreliable", Snackbar.LENGTH_LONG).show();
        }

        if (i == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM) {
            Snackbar.make(view, "Medium accuracy", Snackbar.LENGTH_LONG).show();
        }

        if (i == SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
            Snackbar.make(view, "High accuracy", Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void connectionState(@ConnectionType final int connectingToRobotState) {
        ((BaseActivity)getActivity()).connectionState(connectingToRobotState);
    }
}
