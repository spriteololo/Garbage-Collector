package by.brstu.dmitry.garbagecollector.application;

import java.util.ArrayList;
import java.util.List;

import by.brstu.dmitry.garbagecollector.pojo.TrainingPart;

public class TrainingData {


    private static TrainingPart[] rawArray;
    private static float leftAngularSpeed; // degree per second
    private static float rightAngularSpeed; // degree per second
    private static float bothAngularSpeed; //degree per second
    private static float leftSpeed; // metres per second
    private static float rightSpeed; // metres per second

    private static float array[][];

    public static void setRaw(Object[] array) {
        rawArray = new TrainingPart[array.length];
        for(int i = 0; i < array.length; i++){
            rawArray[i] = (TrainingPart) array[i];
        }
    }

    public static void process() {
        List<TrainingPart> left = new ArrayList<>();
        List<TrainingPart> right = new ArrayList<>();
        List<TrainingPart> both = new ArrayList<>();

        array = new float[3][];

        for (TrainingPart item : rawArray) {
            if (Math.abs(item.getLeftWheelSpeed()) == 255 && item.getRightWheelSpeed() == 0) {
                //left
                left.add(item);

            }
            if (item.getLeftWheelSpeed() == 0 && Math.abs(item.getRightWheelSpeed()) == 255) {
                //right
                right.add(item);
            }

            if (Math.abs(item.getLeftWheelSpeed()) == 255 && Math.abs(item.getRightWheelSpeed()) == 255) {
                //both
                both.add(item);
            }
        }

        array[0] = new float[left.size()];
        for (int i = 0; i < left.size(); i++) {
            TrainingPart item = left.get(i);
            array[0][i] = item.getAngle() * 1000 / item.getTimeOfRotationReal();
            leftAngularSpeed += array[0][i];
        }
        array[1] = new float[right.size()];
        for (int i = 0; i < right.size(); i++) {
            TrainingPart item = right.get(i);
            array[1][i] = item.getAngle() * 1000 / item.getTimeOfRotationReal();
            rightAngularSpeed += array[1][i];
        }
        array[2] = new float[both.size()];
        for (int i = 0; i < both.size(); i++) {
            TrainingPart item = both.get(i);
            array[2][i] = item.getAngle() * 1000 / item.getTimeOfRotationReal();
            bothAngularSpeed += array[2][i];
        }

        leftAngularSpeed /= left.size();
        rightAngularSpeed /= right.size();
        bothAngularSpeed /= both.size();

        leftSpeed = (float)Math.PI * Constants.WIDTH_OF_ROBIN / 180 * leftAngularSpeed;
        rightSpeed = (float)Math.PI * Constants.WIDTH_OF_ROBIN / 180 * rightAngularSpeed;
    }

    public static String getData() {
        return "Left angular speed: " + leftAngularSpeed + "\n" +
                "Right angular speed: " + rightAngularSpeed + "\n" +
                "Left speed: " + leftSpeed + "\n" +
                "Right speed: " + rightSpeed + "\n" +
                "Both angular speed:" + bothAngularSpeed;
    }
}
