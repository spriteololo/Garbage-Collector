package by.brstu.dmitry.garbagecollector.ui.lidState;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import by.brstu.dmitry.garbagecollector.ui.base.InflateFrameLayout;
import by.brstu.dmitry.ui.R;


public class LidStateView extends InflateFrameLayout {
    ImageView lidImage;
    TextView lidText;

    boolean isClosed;

    public LidStateView(Context context) {
        super(context);
    }

    public LidStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LidStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(Context pContext, AttributeSet pAttrs) {
        lidImage = findViewById(R.id.lid_state_image);
        lidText = findViewById(R.id.lid_state_text);
        setClosed(true);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.lid_state_view;
    }

    public void setChangeLidState() {
        if(isClosed) {
            lidImage.setImageResource(R.drawable.ic_recycle_bin_closed_lid);
        } else {
            lidImage.setImageResource(R.drawable.ic_recycle_bin_open_lid);
        }
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
        setChangeLidState();
    }
}
