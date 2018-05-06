package by.brstu.dmitry.garbagecollector.application;

import java.io.IOException;

import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import okhttp3.ResponseBody;

public class RefreshDataConverter {
    public static RefreshData convertData(ResponseBody responseBody) throws IOException {
        String[] arr = responseBody.string().split("&");
        RefreshData refreshData = new RefreshData();

        for(int i = 0; i< arr.length; i++) {
            short num = getNum(arr[i]);
            switch (i) {
                case 0: refreshData.setFrontInfra(num);
                    break;
                case 1: refreshData.setBackInfra(num);
                    break;
                case 2: refreshData.setCharge(num);
                    break;
                case 3: refreshData.setTemperature(num);
                    break;
                case 4: refreshData.setFullness(num);
                    break;
                case 5: refreshData.setMotorWork(num == 1);
                    break;
                case 6: refreshData.setLidOpen(num == 1);
                    break;
            }
        }
        return refreshData;
    }

    private static short getNum(String string) {

        for (int i = 0; i < string.length(); i++) {
            string = string.replaceAll("[^0-9]+", "");
        }
        return Short.parseShort(string);
    }
}
