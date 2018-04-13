package by.brstu.dmitry.garbagecollector.ui.seekBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class CustomSeekBar extends AppCompatSeekBar {

    private TouchEventListener touchEventListener;

    public void setTouchEventListener(final TouchEventListener touchEventListener) {
        this.touchEventListener = touchEventListener;
    }

    public CustomSeekBar(final Context context) {
        super(context);
    }

    public CustomSeekBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
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
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setProgressDrawable(createGradient(progress));
        }*/

        getProgressDrawable().setColorFilter(createColor(Math.abs(progress - 255)), PorterDuff.Mode.DARKEN);
        super.setProgress(progress);
    }

    private int createColor(int progress) {
        if (progress < 128) {
            progress <<= 16;
            progress += 0xFF00;
        } else {
            progress <<= 8;
            progress = 0xFF00 - progress;
            progress += 0xFF0000;
        }
        return progress;
    }

    private LayerDrawable createGradient(final int progress) {



       /* final int bckgrnd[] = {Color.parseColor("#ffe9e9e9"), Color.parseColor("#ffc6c6c6"), Color.parseColor("#ffe9e9e9")};
        final GradientDrawable bckgrndgd = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP, bckgrnd);
        bckgrndgd.setGradientCenter(0.5f, 0.75f);


        final int prgrss[] = {Color.parseColor("#ffe9e9e9"), Color.parseColor("#ff2165ca")};
        final GradientDrawable prgrssgd = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP, prgrss);


        LayerDrawable resultDr = new LayerDrawable(new Drawable[]{bckgrndgd, prgrssgd});
        //setting ids is important
        resultDr.setId(0, android.R.id.background);
        resultDr.setId(1, android.R.id.progress);*/


        return null;
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
