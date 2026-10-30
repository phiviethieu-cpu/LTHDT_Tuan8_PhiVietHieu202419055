package devices;

import enums.AlertSeverity;
import exceptions.InvalidDeviceReadingException;

public class WirelessMicrophone extends TeachingDevice {
    private int batteryLevel;
    private int signalQuality;

    public WirelessMicrophone(String id, String name) {
        super(id, name);
        this.batteryLevel = 100;
        this.signalQuality = 100;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public int getSignalQuality() {
        return signalQuality;
    }

    public void updateBatteryLevel(int newLevel) {
        if (newLevel < 0 || newLevel > 100) {
            throw new InvalidDeviceReadingException(getId(), newLevel, "Pin");
        }

        int oldLevel = batteryLevel;
        batteryLevel = newLevel;

        if (newLevel == 0 && oldLevel != 0) {
            raiseAlert("Micro het pin", AlertSeverity.CRITICAL);
        } else if (newLevel > 0 && newLevel < 30 && oldLevel >= 30) {
            raiseAlert("Pin micro yeu: " + newLevel + "%", AlertSeverity.WARNING);
        }
    }

    public void updateSignalQuality(int newQuality) {
        if (newQuality < 0 || newQuality > 100) {
            throw new InvalidDeviceReadingException(getId(), newQuality, "Tin hieu");
        }

        int oldQuality = signalQuality;
        signalQuality = newQuality;

        if (newQuality == 0 && oldQuality != 0) {
            raiseAlert("Micro mat tin hieu", AlertSeverity.CRITICAL);
        } else if (newQuality > 0 && newQuality < 40 && oldQuality >= 40) {
            raiseAlert("Tin hieu micro yeu: " + newQuality + "%", AlertSeverity.WARNING);
        }
    }
}
