package by.brstu.dmitry.garbagecollector.ui.training;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.TrainingData;
import by.brstu.dmitry.garbagecollector.ui.ProgressBarAnimation;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpActivity;
import by.brstu.dmitry.garbagecollector.ui.compass.CompassCustomView;

public class TrainingActivity extends BaseMvpActivity implements TrainingView, SensorEventListener {

    @InjectPresenter
    TrainingPresenter presenter;

    @BindView(R.id.training_compass_view)
    CompassCustomView compassView;

    @BindView(R.id.textView_info)
    TextView textViewInfo;

    @BindView(R.id.training_progress_view)
    ProgressBar progressBar;

    @BindView(R.id.textView_log_data)
    TextView textViewLogInfo;

    private SensorManager mSensorManager;

    ProgressBarAnimation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_screen);

        BaseApplication.getApplicationComponent().inject(this);

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        presenter.setListener(compassView);
        startListening();
        super.onResume();

        presenter.startTraining();
        textViewInfo.setText(getString(R.string.training_str) + "0%");

    }

    @Override
    protected void onPause() {
        presenter.clean();
        stopListening();
        super.onPause();
    }

    @Override
    protected void onViewsBinded() {

    }

    @Override
    public void startListening() {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void stopListening() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void updateTrainingProgress(short v) {
        anim = new ProgressBarAnimation(progressBar);
        anim.addTextInfo(textViewInfo);
        anim.setDuration(1000);
        anim.setSecondary(v);
        progressBar.startAnimation(anim);

    }

    @Override
    public void updateEvaluation(short v) {
        textViewInfo.setText(getString(R.string.evaluation) + "0%");
        if(anim == null) {
            anim = new ProgressBarAnimation(progressBar);
        }

        anim.addTextInfo(textViewInfo);
        anim.setDuration(1000);
        anim.setPrimary(v);
        progressBar.startAnimation(anim);
    }

    @Override
    public void showLogData() {
        textViewLogInfo.setText(TrainingData.getData());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        //presenter.onSensorChanged(degree);
        compassView.setCurrentDegree(degree);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
