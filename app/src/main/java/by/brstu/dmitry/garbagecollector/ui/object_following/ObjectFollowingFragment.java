package by.brstu.dmitry.garbagecollector.ui.object_following;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.application.DisposableManager;
import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
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

    @BindView(R.id.object_following_button)
    Button followingBtn;

    @BindView(R.id.object_read_button)
    Button readBtn;

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
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_object_following, container, false);
    }

    @Override
    protected void onViewsBinded() {
        backwardSensor.setReverse(true);

        followingBtn.setOnClickListener(view -> followingBtn.setText(presenter.startFollowing() ?
                getString(R.string.object_following_stop)
                : getString(R.string.object_following_start)));
        followingBtn.setEnabled(false);

        readBtn.setOnClickListener(view -> {
            boolean isStarted = presenter.startReading();
            if(!isStarted) {
                followingBtn.setText(getString(R.string.object_following_start));
                presenter.startFollowing(false);
            }
            followingBtn.setEnabled(isStarted);
            readBtn.setText(isStarted ?
                    getString(R.string.object_read_stop)
                    : getString(R.string.object_read_start));
        });


        forwardSensor.setOnClickListener(view -> forwardRadio.setChecked(true));

        backwardSensor.setOnClickListener(view -> backwardRadio.setChecked(true));

        forwardRadio.setChecked(true);
        radioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
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
        });
    }

    @Override
    public void setSensorData(final boolean isForwardSensor, @NonNull final RefreshData refreshData) {
        (isForwardSensor ?
                forwardSensor : backwardSensor)
                .setInnerRadius(isForwardSensor ?
                        1 - (refreshData.getFrontInfra() - Constants.MINIMUM_DISTANCE_VALUE) / (Constants.MAXIMUM_DISTANCE_VALUE - Constants.MINIMUM_DISTANCE_VALUE) :
                        1 - (refreshData.getBackInfra() - Constants.MINIMUM_DISTANCE_VALUE) / (Constants.MAXIMUM_DISTANCE_VALUE - Constants.MINIMUM_DISTANCE_VALUE));

    }

    @Override
    public void setSensorData(RefreshData refreshData) {

        forwardSensor.setInnerRadius(1 - (refreshData.getFrontInfra() - Constants.MINIMUM_DISTANCE_VALUE) / (Constants.MAXIMUM_DISTANCE_VALUE - Constants.MINIMUM_DISTANCE_VALUE));
        backwardSensor.setInnerRadius(1 - (refreshData.getBackInfra() - Constants.MINIMUM_DISTANCE_VALUE) / (Constants.MAXIMUM_DISTANCE_VALUE - Constants.MINIMUM_DISTANCE_VALUE));
    }

    @Override
    public void onPause() {
        DisposableManager.disposeRapid();
        presenter.stopReading();
        super.onPause();
    }
}
