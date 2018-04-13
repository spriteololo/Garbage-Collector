package by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlPresenter;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlView;
import by.brstu.dmitry.garbagecollector.ui.seekBar.CustomSeekBar;

public class StelsFragment extends BaseMvpFragment implements ManualControlView,
        SeekBar.OnSeekBarChangeListener,
        StelsView, CustomSeekBar.TouchEventListener {

    @InjectPresenter
    ManualControlPresenter controlPresenter;

    @InjectPresenter
    StelsPresenter presenter;

    @BindView(R.id.stels_text_view)
    TextView textView;

    @BindView(R.id.stels_left_seek_bar)
    CustomSeekBar leftBar;

    @BindView(R.id.stels_right_seek_bar)
    CustomSeekBar rightBar;

    Byte touches = 0;

    public static StelsFragment getInstance(final int position) {
        StelsFragment stelsFragment = new StelsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extras.POSITION_KEY, position);
        stelsFragment.setArguments(args);
        return stelsFragment;
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

        return inflater.inflate(R.layout.fragment_stels, container, false);
    }

    @Override
    protected void onViewsBinded() {
        leftBar.setOnSeekBarChangeListener(this);
        rightBar.setOnSeekBarChangeListener(this);
        leftBar.setTouchEventListener(this);
        rightBar.setTouchEventListener(this);
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int i, final boolean b) {
        presenter.setProgressChange(seekBar.equals(rightBar), i);
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
        touches++;
        if (touches == 1) {
            ((ManualControlFragment) getParentFragment()).setPaging(false);
        }
        presenter.movingControl(true);
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
        touches--;
        if(touches == 0) {
            ((ManualControlFragment) getParentFragment()).setPaging(true);
        }
        presenter.movingControl(false);
    }
}
