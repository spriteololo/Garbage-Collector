package by.brstu.dmitry.garbagecollector.ui.temperatureView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TemperatureView extends View {

    Paint paint;
    RectF circle;
    float center_x, center_y;

    final short MIN_TEMPERATURE = 15;
    final short MAX_TEMPERATURE = 60;

    float radius = 0;
    float currentTemperature = 50; //TODO MIN_TEMPERATURE;

    int colors[] = {Color.CYAN, Color.GREEN, Color.RED};

    public TemperatureView(Context context) {
        super(context);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle = new RectF();

        paint.setTextSize(50);
        paint.setStrokeWidth(5);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle = new RectF();

        paint.setTextSize(50);
        paint.setStrokeWidth(5);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle = new RectF();

        paint.setTextSize(50);
        paint.setStrokeWidth(5);
    }

    public void setCurrentTemperature(float currentTemperature) {
        if (currentTemperature > MAX_TEMPERATURE) {
            danger();
        } else {
            if (currentTemperature < 0) {
                wrongBehavior();
            } else {
                this.currentTemperature = currentTemperature;
            }

        }

        invalidate();
    }

    private void wrongBehavior() {
        //TODO
    }

    private void danger() {
        //TODO
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        radius = 70;
        radius = getWidth() < radius ? getWidth() / 2 : radius;
        radius = getHeight() < radius ? getHeight() / 2 : radius;

        center_x = getWidth() / 2;
        center_y = getHeight() - radius;
        paint.setShader(new RadialGradient(center_x,
                center_y + radius,
                getHeight(),
                colors, new float[]{0.0f, 0.15f, 1f},
                Shader.TileMode.CLAMP));

        paint.setStyle(Paint.Style.FILL);
        circle.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);

        canvas.drawCircle(center_x, center_y, radius, paint);

        circle.set(center_x - 1 / (float) Math.sqrt(3) * radius, paint.getTextSize() / 2, center_x + 1 / (float) Math.sqrt(3) * radius, center_y - 1 / (float) Math.sqrt(3) * radius);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(circle, paint);

        paint.setStyle(Paint.Style.FILL);
        circle.set(circle.left, circle.bottom - ((currentTemperature - MIN_TEMPERATURE) / (MAX_TEMPERATURE - MIN_TEMPERATURE)) * (circle.bottom - circle.top), circle.right, circle.bottom);
        canvas.drawRect(circle, paint);

        float textY = circle.top > 0 ? circle.top + paint.getTextSize() / 2 : paint.getTextSize() / 2;
        if (circle.top > circle.bottom) {
            textY = circle.bottom + paint.getTextSize() / 2;
        }
        canvas.drawText(currentTemperature + "", circle.right + paint.getTextSize() / 4, textY, paint);
    }
}
