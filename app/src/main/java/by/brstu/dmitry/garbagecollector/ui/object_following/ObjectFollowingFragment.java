package by.brstu.dmitry.garbagecollector.ui.object_following;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.strengthLevel.StrengthLevel;
import okhttp3.ResponseBody;

public class ObjectFollowingFragment extends BaseMvpFragment implements ObjectFollowingView {

    @InjectPresenter
    ObjectFollowingPresenter presenter;

    @BindView(R.id.object_following_backward_sensor)
    StrengthLevel backwardSensor;

    @BindView(R.id.object_following_forward_sensor)
    StrengthLevel forwardSensor;

    @BindView(R.id.object_following_start_button)
    Button startBtn;

    @BindView(R.id.object_following_stop_button)
    Button stopBtn;

    @BindView(R.id.object_following_forward_sensor_radio)
    RadioButton forwardRadio;

    @BindView(R.id.object_following_backward_sensor_radio)
    RadioButton backwardRadio;

    @BindView(R.id.object_following_radio_group)
    RadioGroup radioGroup;

    public static Fragment getInstance() {
        return new ObjectFollowingFragment();
    }

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
        return inflater.inflate(R.layout.fragment_object_following, container, false);
    }

    @Override
    protected void onViewsBinded() {
        backwardSensor.setReverse(true);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                presenter.startFollowing(true);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                presenter.startFollowing(false);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int checkedId) {
                switch (checkedId) {
                    case R.id.object_following_forward_sensor_radio:
                        //Forward
                        presenter.setSensor(true);
                        break;
                    case R.id.object_following_backward_sensor_radio:
                        //Backward
                        presenter.setSensor(false);
                        break;
                    default:
                        break;
                }
            }
        });

        forwardRadio.setChecked(true);
    }

    @Override
    public void setSensorData(final boolean isForwardSensor, final ResponseBody responseBody) {
        (isForwardSensor ? forwardSensor : backwardSensor).setInnerRadius(0.5f);//TODO
    }


}
