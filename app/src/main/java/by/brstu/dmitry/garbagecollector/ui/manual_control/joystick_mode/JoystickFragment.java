package by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlView;

public class JoystickFragment extends BaseMvpFragment implements ManualControlView {

    @BindView(R.id.joystick_text_view)
    TextView textView;

    public static JoystickFragment getInstance(final int position) {
        JoystickFragment joystickFragment = new JoystickFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extras.POSITION_KEY, position);
        joystickFragment.setArguments(args);
        return joystickFragment;
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

        return inflater.inflate(R.layout.fragment_joystick, container, false);
    }

    @Override
    protected void onViewsBinded() {

    }
}