/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package devices;

import enums.AlertSeverity;

// Điều khiển máy chiếu, cảnh báo khi pin yếu hoặc hết pin.
public class ProjectorRemote extends TeachingDevice {
    private static final int LOW_BATTERY = 20;

    private int batteryLevel;

    // Pin ban đầu là 100%.
    public ProjectorRemote(String id, String name) {
        super(id, name);
        this.batteryLevel = 100;
    }

    // Mức pin hiện tại (%).
    public int getBatteryLevel() {
        return batteryLevel;
    }

    // Cập nhật pin: dưới 20% báo WARNING, 0% báo CRITICAL.
    public void updateBatteryLevel(int newLevel) {
        ReadingRules.validate(getId(), newLevel, "Pin");

        int oldLevel = batteryLevel;
        batteryLevel = newLevel;

        AlertSeverity severity = ReadingRules.levelChange(oldLevel, newLevel, LOW_BATTERY);
        if (severity == AlertSeverity.CRITICAL) {
            raiseAlert("Het pin", AlertSeverity.CRITICAL);
        } else if (severity == AlertSeverity.WARNING) {
            raiseAlert("Pin yeu: " + newLevel + "%", AlertSeverity.WARNING);
        }
    }
}
