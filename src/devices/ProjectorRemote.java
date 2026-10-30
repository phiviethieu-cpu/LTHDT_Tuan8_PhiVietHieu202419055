package devices;

import enums.AlertSeverity;
import exceptions.InvalidDeviceReadingException;

public class ProjectorRemote extends TeachingDevice {
    private int batteryLevel;

    public ProjectorRemote(String id, String name) {
        super(id, name);
        this.batteryLevel = 100;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void updateBatteryLevel(int newLevel) {
        if (newLevel < 0 || newLevel > 100) {
            throw new InvalidDeviceReadingException(getId(), newLevel, "Pin");
        }

        int oldLevel = batteryLevel;
        batteryLevel = newLevel;

        // chi canh bao khi vua roi xuong vung thap, con dang o vung thap roi thi thoi
        if (newLevel == 0 && oldLevel != 0) {
            raiseAlert("Het pin", AlertSeverity.CRITICAL);
        } else if (newLevel > 0 && newLevel < 20 && oldLevel >= 20) {
            raiseAlert("Pin yeu: " + newLevel + "%", AlertSeverity.WARNING);
        }
    }
}
