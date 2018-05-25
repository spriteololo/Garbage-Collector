package by.brstu.dmitry.garbagecollector.ui.seekBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class CustomSeekBar extends AppCompatSeekBar {

    private final short CONST_MINIMUM_WHEEL_VALUE = 130;//Ref to constant
    private final short CONST_SECOND_STAGE = (short) ((((getMax() / 2) - CONST_MINIMUM_WHEEL_VALUE) / 2) + CONST_MINIMUM_WHEEL_VALUE);

    private boolean active;

    private TouchEventListener touchEventListener;

    public void setTouchEventListener(final TouchEventListener touchEventListener) {
        this.touchEventListener = touchEventListener;
    }

    public CustomSeekBar(final Context context) {
        super(context);
        active = true;
    }

    public CustomSeekBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        active = true;
    }

    public CustomSeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        active = true;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    public void setActive(boolean active) {
        this.active = !active;
        postInvalidate();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }

    @Override
    public synchronized void setProgress(final int progress) {
        getProgressDrawable().setColorFilter(createColor(Math.abs(progress - 255)), PorterDuff.Mode.DARKEN);
        super.setProgress(progress);
    }

    private int createColor(int progress) {
        Log.i("Color", "color = " + progress);
        if (progress > 250) {
            return 0xFFFF0000;
        }
        if (!active) {
            if (progress < CONST_MINIMUM_WHEEL_VALUE) {
                progress = 0xFF - progress * 255 / CONST_MINIMUM_WHEEL_VALUE;
                progress += 0xFF00;
            } else {
                if (progress <= CONST_SECOND_STAGE) {
                    progress = (progress - CONST_MINIMUM_WHEEL_VALUE) * 255 / (CONST_SECOND_STAGE - CONST_MINIMUM_WHEEL_VALUE);
                    progress <<= 16;
                    progress += 0xFF00;
                } else {
                    progress = progress * 255 / (getMax() / 2 - CONST_SECOND_STAGE);
                    progress <<= 8;
                    progress = 0xFF00 - progress;
                    progress += 0xFF0000;
                }
            }

            Log.i("Color", "color 2 = " + Integer.toHexString(progress));
            return progress + 0xFF000000;
        } else {
            return 0xFF828282;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (touchEventListener != null) {
                    touchEventListener.onStartTrackingTouch(this);
                }
            case MotionEvent.ACTION_MOVE:
                int i;
                i = getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;

            case MotionEvent.ACTION_UP:
                if (touchEventListener != null) {
                    touchEventListener.onStopTrackingTouch(this);
                }
                setProgress(255);
                break;
        }
        return true;
    }

    public interface TouchEventListener {
        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }
}
