package events;

import enums.DeviceStatus;
import java.time.LocalDateTime;

// Dữ liệu gửi kèm khi thiết bị đổi trạng thái (AVAILABLE, BORROWED, UNDER_MAINTENANCE)
public class DeviceStatusChangedEvent {
    // final + chỉ có getter: listener chỉ đọc, không sửa được dữ liệu mà listener khác cũng nhận
    private final String deviceId;
    private final DeviceStatus oldStatus;
    private final DeviceStatus newStatus;
    private final LocalDateTime changedAt;

    public DeviceStatusChangedEvent(String deviceId, DeviceStatus oldStatus,
                                    DeviceStatus newStatus, LocalDateTime changedAt) {
        this.deviceId = deviceId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceStatus getOldStatus() {
        return oldStatus;
    }

    public DeviceStatus getNewStatus() {
        return newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
