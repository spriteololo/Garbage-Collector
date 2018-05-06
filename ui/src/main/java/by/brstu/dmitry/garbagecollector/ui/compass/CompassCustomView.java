package by.brstu.dmitry.garbagecollector.ui.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CompassCustomView extends View {

    private static final short REFRESH_RATE = 25;
    Paint paint;
    RectF rect;
    boolean firstTime = true;
    float absoluteFirstDegree;

    private float width;
    private float height;
    private float lineLength;
    private float radius;

    private float center_x;
    private float center_y;

    private float currentDegree = 0;
    private float previousAngle = 0;
    private boolean otherSide = false;

    private float lastRotation;
    private final long[] lastTime = new long[2];

    private RotationCallback rotationCallback;
    private boolean isStarted = false;
    private boolean oneValue = true;

    private final float[] size = new float[4];

    public CompassCustomView(Context context) {
        super(context);
        init();
    }

    public CompassCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompassCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new RectF();

        paint.setColor(Color.WHITE);

        paint.setTextSize(50);
        paint.setStrokeWidth(5);
    }

    short i = 0;

    public void setCurrentDegree(float currentAngle) {

        if (i++ == REFRESH_RATE) {
            if (isStarted) {
                if (rotationCallback != null) {
                    rotationCallback.rotationEnd(lastTime[1] - lastTime[0], getLastRotation());
                }
                for (int i = 0; i < size.length; i++) {
                    size[i] = 0;
                }
                lastTime[0] = lastTime[1] = 0;
                isStarted = false;
                otherSide = false;
                firstTime = true;
                i=0;
                oneValue = true;
            }
        }
        if (currentAngle != previousAngle) {
            oneValue = true;
            if (!isStarted) {
                if (rotationCallback != null) {
                    rotationCallback.rotationStart();
                }
                lastTime[0] = System.currentTimeMillis();

                isStarted = true;


            }
            i = 0;
            this.currentDegree = currentAngle;

            if (firstTime) {
                this.absoluteFirstDegree = currentAngle;
                firstTime = false;
            }
            boolean clockWise = previousAngle > currentAngle;
            if (Math.abs(previousAngle - currentAngle) > 350) {
                otherSide = !otherSide;
                clockWise = currentAngle > previousAngle;
            }

            if (otherSide) {
                if (clockWise) {
                    if (360 - currentAngle > size[2] && 360 - currentAngle - size[2] < 10) {
                        size[2] = 360 - currentAngle;
                    }
                } else {
                    if (currentAngle > size[3] && currentAngle - size[3] < 10) {
                        size[3] = currentAngle;
                    }
                }


            } else {
                if (clockWise) {
                    if (absoluteFirstDegree - currentAngle > size[0] && absoluteFirstDegree - currentAngle - size[0] < 10) {
                        size[0] = absoluteFirstDegree - currentAngle;
                    }
                } else {
                    if (currentAngle - absoluteFirstDegree > size[1] && currentAngle - absoluteFirstDegree - size[1] < 10) {
                        size[1] = currentAngle - absoluteFirstDegree;
                    }
                }
            }

            previousAngle = currentAngle;
            invalidate();
        } else {
            if(oneValue) {
                lastTime[1] = System.currentTimeMillis();
                oneValue = false;
            }
        }
    }

    public float getLastRotation() {
        return lastRotation;
    }

    public void clearLastRotation() {
        this.lastRotation = 0;
        this.firstTime = true;
        for (int i = 0; i < size.length; i++) {
            size[i] = 0;
        }
        otherSide = false;

        lastTime[0] = lastTime[1] = 0;
        i=0;
        isStarted = false;
        oneValue = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        width = getWidth();
        height = getHeight();

        center_x = width / 2;
        center_y = height / 2;

        lineLength = width < height ? width * 0.1f : height * 0.1f;
        radius = width < height ? center_x - lineLength : center_y - lineLength;

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(center_x, center_y, radius, paint);

        for (int i = 0; i < 4; i++) {

            if (i > 0) paint.setColor(Color.GREEN);
            final float x1 = center_x - radius * (float) Math.sin(currentDegree * Math.PI / 180);
            final float x2 = x1 - lineLength * (float) Math.sin(currentDegree * Math.PI / 180);

            final float y1 = center_y - radius * (float) Math.cos(currentDegree * Math.PI / 180);
            final float y2 = y1 - lineLength * (float) Math.cos(currentDegree * Math.PI / 180);

            canvas.drawLine(x1, y1, x2, y2, paint);
            if (i == 0) {
                canvas.drawText(currentDegree + "", x2, y2, paint);
            }
            currentDegree += 90;

        }
        currentDegree -= 360;

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        rect.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
        lastRotation = size[0] + size[1] + size[2] + size[3];
        canvas.drawArc(rect, -90 - size[1] - size[3], lastRotation, true, paint);

       /* paint.setColor(Color.CYAN);
        canvas.drawLine(center_x, center_y, center_x, center_y - radius, paint);*/
        paint.setColor(Color.RED);
        canvas.drawLine(center_x, center_y, center_x - radius * (float) Math.sin((currentDegree - absoluteFirstDegree) * Math.PI / 180), center_y - radius * (float) Math.cos((currentDegree - absoluteFirstDegree) * Math.PI / 180), paint);
      /*   canvas.drawText((currentDegree - absoluteFirstDegree) + "", center_x - radius * (float) Math.sin((currentDegree - absoluteFirstDegree) * Math.PI / 180), center_y - radius * (float) Math.cos((currentDegree - absoluteFirstDegree) * Math.PI / 180), paint);
*/
    }

    public void setRotationCallback(RotationCallback rotationCallback) {
        this.rotationCallback = rotationCallback;
    }

    public interface RotationCallback {
        void rotationStart();

        void rotationEnd(long l, float angle);
    }
}
