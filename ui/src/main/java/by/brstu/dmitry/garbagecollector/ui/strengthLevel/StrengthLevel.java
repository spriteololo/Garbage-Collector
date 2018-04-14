package by.brstu.dmitry.garbagecollector.ui.strengthLevel;

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

public class StrengthLevel extends View {

    Paint paint;
    RectF circle;
    float center_x, center_y;
    float radius = 0;

    int colors[] = {Color.CYAN, Color.GREEN, Color.RED};

    float innerRadius = 0;

    boolean setReverse = false;

    public StrengthLevel(final Context context) {
        super(context);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle = new RectF();
    }

    public StrengthLevel(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle = new RectF();
    }

    public StrengthLevel(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInnerRadius(final float innerRadius) {
        this.innerRadius = radius * innerRadius;
        invalidate();
    }

    public void setReverse(final boolean setReverse) {
        this.setReverse = setReverse;
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        center_x = getWidth() / 2;
        center_y = setReverse ? 0 : getHeight();
        paint.setStrokeWidth(5);

        radius = getWidth() > getHeight() ? getHeight() :
                (float) Math.sqrt(2 * Math.pow(center_x, 2));

        paint.setShader(new RadialGradient(center_x,
                center_y,
                radius,
                colors, new float[]{0.0f, 0.5f, 1f},
                Shader.TileMode.CLAMP));

        paint.setStyle(Paint.Style.STROKE);
        circle.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);

        paint.setColor(Color.WHITE);
        canvas.drawArc(circle, getReversedAngle(), 90, true, paint);


        paint.setStyle(Paint.Style.FILL);
        circle.set(center_x - innerRadius, center_y - innerRadius, center_x + innerRadius, center_y + innerRadius);
        canvas.drawArc(circle, getReversedAngle(), 90, true, paint);
    }

    public float getReversedAngle() {
        return setReverse ? 45 : 225;
    }
}
