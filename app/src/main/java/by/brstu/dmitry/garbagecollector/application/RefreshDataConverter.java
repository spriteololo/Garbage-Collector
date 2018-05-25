package by.brstu.dmitry.garbagecollector.application;

import java.io.IOException;

import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import okhttp3.ResponseBody;

public class RefreshDataConverter {
    public static RefreshData convertData(ResponseBody responseBody) throws IOException {

        RefreshData refreshData = new RefreshData();
        String source = responseBody.string();

        refreshData.setFrontInfra(getNumByString(source, "frir"));

        refreshData.setBackInfra(getNumByString(source, "bkir"));

        refreshData.setCharge(getNumByString(source, "chr"));

        refreshData.setTemperature(getNumByString(source, "tmp"));

        refreshData.setFullness(getNumByString(source, "ful"));

        refreshData.setMotorWork(getNumByString(source, "mtr") == 1);

        refreshData.setLidOpen(getNumByString(source, "bin") == 1);


        return refreshData;
    }

    private static short getNumByString(String input, String pattern) {
        if (input.contains(pattern)) {
            int start = input.indexOf(pattern) + pattern.length() + 1;
            char array[] = input.substring(start).toCharArray();
            final StringBuilder strb = new StringBuilder();
            for (char ch : array) {
                if (Character.isDigit(ch)) {
                    strb.append(ch);
                } else {
                    return Short.parseShort(strb.toString());
                }
            }
            return Short.parseShort(strb.toString());
        } else {
            return -1;
        }
    }
}
