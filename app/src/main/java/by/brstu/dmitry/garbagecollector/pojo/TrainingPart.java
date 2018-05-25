package by.brstu.dmitry.garbagecollector.pojo;

public class TrainingPart {

    private int leftWheelSpeed;
    private int rightWheelSpeed;

    private long timeOfRotationSent;
    private long timeOfRotationReal;
    private long timeOfRotationInPresenter;
    private long timeOfResponse;
    private float angle;

    public TrainingPart() {
        this.leftWheelSpeed = 0;
        this.rightWheelSpeed = 0;
        this.timeOfRotationSent = 0;
        this.timeOfRotationReal = 0;
        this.timeOfRotationInPresenter = 0;
        this.timeOfResponse = 0;
        this.angle = 0f;
    }

    public int getLeftWheelSpeed() {
        return leftWheelSpeed;
    }

    public void setLeftWheelSpeed(int leftWheelSpeed) {
        this.leftWheelSpeed = leftWheelSpeed;
    }

    public int getRightWheelSpeed() {
        return rightWheelSpeed;
    }

    public void setRightWheelSpeed(int rightWheelSpeed) {
        this.rightWheelSpeed = rightWheelSpeed;
    }

    public long getTimeOfResponse() {
        return timeOfResponse;
    }

    public void setTimeOfResponse(long timeOfResponse) {
        this.timeOfResponse = timeOfResponse;
    }

    public long getTimeOfRotationSent() {
        return timeOfRotationSent;
    }

    public void setTimeOfRotationSent(long timeOfRotationSent) {
        this.timeOfRotationSent = timeOfRotationSent;
    }

    public long getTimeOfRotationReal() {
        return timeOfRotationReal;
    }

    public void setTimeOfRotationReal(long timeOfRotationReal) {
        this.timeOfRotationReal = timeOfRotationReal;
    }

    public long getTimeOfRotationInPresenter() {
        return timeOfRotationInPresenter;
    }

    public void setTimeOfRotationInPresenter(long timeOfRotationInPresenter) {
        this.timeOfRotationInPresenter = timeOfRotationInPresenter;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "\nTime of rotation sent by retrofit: " + timeOfRotationSent + "\n" + //Time sent in retrofit request (Constants.BaseTime)
                "Time of real rotation: " + timeOfRotationReal + "\n" +// Time of rotation from sensor (view of activity)
                "Time of rotation in app presenter: " + timeOfRotationInPresenter + "\n" + // Time of rotation in presenter to find delays
                "Time of from start to onNext " + timeOfResponse + "\n" + // Time of response from  server
                "Angle = " + angle + "\n" +
                "Left wheel speed = " + leftWheelSpeed + "\n" +
                "Right wheel speed  = " + rightWheelSpeed + "\n" +
                "=====================================END";

    }
}
