/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package events;

import enums.AlertSeverity;
import java.time.LocalDateTime;

// Dữ liệu gửi kèm khi thiết bị phát cảnh báo: pin yếu, tín hiệu yếu, mất phản hồi.
public class DeviceAlertEvent {
    private final String deviceId;
    private final String message;
    // MaintenanceService dựa vào mức này để chỉ tạo yêu cầu bảo trì khi CRITICAL
    private final AlertSeverity severity;
    private final LocalDateTime occurredAt;

    // Tạo sự kiện cảnh báo với nội dung, mức độ và thời điểm xảy ra.
    public DeviceAlertEvent(String deviceId, String message,
                            AlertSeverity severity, LocalDateTime occurredAt) {
        this.deviceId = deviceId;
        this.message = message;
        this.severity = severity;
        this.occurredAt = occurredAt;
    }

    // Mã thiết bị phát cảnh báo.
    public String getDeviceId() {
        return deviceId;
    }

    // Nội dung cảnh báo.
    public String getMessage() {
        return message;
    }

    // Mức độ cảnh báo.
    public AlertSeverity getSeverity() {
        return severity;
    }

    // Thời điểm phát cảnh báo.
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
