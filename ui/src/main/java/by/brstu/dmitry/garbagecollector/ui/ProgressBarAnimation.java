package by.brstu.dmitry.garbagecollector.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import by.brstu.dmitry.ui.R;


public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float fromPrimary;
    private float toPrimary;
    private float fromSecondary;
    private float toSecondary;
    private TextView textViewInfo;

    public ProgressBarAnimation() {
        super();
    }

    public ProgressBarAnimation(ProgressBar progressBar) {
        super();
        this.progressBar = progressBar;
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.MULTIPLY);

    }

    public ProgressBarAnimation(ProgressBar progressBar, float fromPrimary, float toPrimary, float fromSecondary, float toSecondary) {
        super();
        this.progressBar = progressBar;
        this.fromPrimary = fromPrimary;
        this.toPrimary = toPrimary;
        this.fromSecondary = fromSecondary;
        this.toSecondary = toSecondary;

    }

    public void setSecondary(float toSecondary) {
        this.toSecondary = toSecondary;
    }

    public void setPrimary(float toPrimary) {
        this.toPrimary = toPrimary;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (toPrimary != 0) {
            float value = progressBar.getProgress() + (toPrimary - progressBar.getProgress()) * interpolatedTime;
            progressBar.setProgress((int) value);

            setTextViewInfo((int)value);
        }
        if (toSecondary != 0) {
            float value = progressBar.getSecondaryProgress() + (toSecondary - progressBar.getSecondaryProgress()) * interpolatedTime;
            progressBar.setSecondaryProgress((int) value);

            setTextViewInfo((int)value);
        }
    }

    private void setTextViewInfo(int value) {
        char[] a = textViewInfo.getText().toString().toCharArray();
        StringBuilder strb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (Character.isDigit(a[i])) {
                strb.append(textViewInfo.getText().toString().substring(0, i));
                i = a.length;
            }
        }
        strb.append(value).append("%");
        textViewInfo.setText(strb.toString());
    }

    public void addTextInfo(TextView textViewInfo) {
        this.textViewInfo = textViewInfo;
    }
}
