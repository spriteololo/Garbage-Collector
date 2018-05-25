package by.brstu.dmitry.garbagecollector.pojo;

public class RefreshData {
    private short frontInfra;
    private short backInfra;
    private short charge;
    private short temperature;
    private short fullness;
    private boolean isMotorWork;
    private boolean isLidOpen;

    public short getFrontInfra() {
        return frontInfra;
    }

    public void setFrontInfra(final short frontInfra) {
        this.frontInfra = frontInfra == 0 ? 1 : frontInfra;
    }

    public short getBackInfra() {
        return backInfra;
    }

    public void setBackInfra(final short backInfra) {
        this.backInfra = backInfra == 0 ? 1 : backInfra;
    }

    public short getCharge() {
        return charge;
    }

    public void setCharge(final short charge) {
        this.charge = charge;
    }

    public short getTemperature() {
        return temperature;
    }

    public void setTemperature(final short temperature) {
        this.temperature = temperature;
    }

    public short getFullness() {
        return fullness;
    }

    public void setFullness(final short fullness) {
        this.fullness = fullness;
    }

    public boolean isMotorWork() {
        return isMotorWork;
    }

    public void setMotorWork(final boolean motorWork) {
        isMotorWork = motorWork;
    }

    public boolean isLidOpen() {
        return isLidOpen;
    }

    public void setLidOpen(final boolean lidOpen) {
        isLidOpen = lidOpen;
    }
}
