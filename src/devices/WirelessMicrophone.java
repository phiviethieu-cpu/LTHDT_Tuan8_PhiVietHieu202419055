/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package devices;

import enums.AlertSeverity;

// Micro không dây, cảnh báo theo mức pin và chất lượng tín hiệu.
public class WirelessMicrophone extends TeachingDevice {
    private static final int LOW_BATTERY = 30;
    private static final int LOW_SIGNAL = 40;

    private int batteryLevel;
    private int signalQuality;

    // Pin và tín hiệu ban đầu đều là 100%.
    public WirelessMicrophone(String id, String name) {
        super(id, name);
        this.batteryLevel = 100;
        this.signalQuality = 100;
    }

    // Mức pin hiện tại (%).
    public int getBatteryLevel() {
        return batteryLevel;
    }

    // Chất lượng tín hiệu hiện tại (%).
    public int getSignalQuality() {
        return signalQuality;
    }

    // Cập nhật pin: dưới 30% báo WARNING, 0% báo CRITICAL.
    public void updateBatteryLevel(int newLevel) {
        ReadingRules.validate(getId(), newLevel, "Pin");

        int oldLevel = batteryLevel;
        batteryLevel = newLevel;

        AlertSeverity severity = ReadingRules.levelChange(oldLevel, newLevel, LOW_BATTERY);
        if (severity == AlertSeverity.CRITICAL) {
            raiseAlert("Micro het pin", AlertSeverity.CRITICAL);
        } else if (severity == AlertSeverity.WARNING) {
            raiseAlert("Pin micro yeu: " + newLevel + "%", AlertSeverity.WARNING);
        }
    }

    // Cập nhật tín hiệu: dưới 40% báo WARNING, 0% báo CRITICAL.
    public void updateSignalQuality(int newQuality) {
        ReadingRules.validate(getId(), newQuality, "Tin hieu");

        int oldQuality = signalQuality;
        signalQuality = newQuality;

        AlertSeverity severity = ReadingRules.levelChange(oldQuality, newQuality, LOW_SIGNAL);
        if (severity == AlertSeverity.CRITICAL) {
            raiseAlert("Micro mat tin hieu", AlertSeverity.CRITICAL);
        } else if (severity == AlertSeverity.WARNING) {
            raiseAlert("Tin hieu micro yeu: " + newQuality + "%", AlertSeverity.WARNING);
        }
    }
}
