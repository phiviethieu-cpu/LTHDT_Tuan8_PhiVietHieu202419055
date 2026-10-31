/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package events;

import enums.DeviceStatus;
import java.time.LocalDateTime;

// Dữ liệu gửi kèm khi thiết bị đổi trạng thái (AVAILABLE, BORROWED, UNDER_MAINTENANCE).
public class DeviceStatusChangedEvent {
    // final + chỉ có getter: listener chỉ đọc, không sửa được dữ liệu mà listener khác cũng nhận
    private final String deviceId;
    private final DeviceStatus oldStatus;
    private final DeviceStatus newStatus;
    private final LocalDateTime changedAt;

    // Tạo sự kiện với trạng thái cũ, trạng thái mới và thời điểm đổi.
    public DeviceStatusChangedEvent(String deviceId, DeviceStatus oldStatus,
                                    DeviceStatus newStatus, LocalDateTime changedAt) {
        this.deviceId = deviceId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }

    // Mã thiết bị phát sự kiện.
    public String getDeviceId() {
        return deviceId;
    }

    // Trạng thái trước khi đổi.
    public DeviceStatus getOldStatus() {
        return oldStatus;
    }

    // Trạng thái sau khi đổi.
    public DeviceStatus getNewStatus() {
        return newStatus;
    }

    // Thời điểm đổi trạng thái.
    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
