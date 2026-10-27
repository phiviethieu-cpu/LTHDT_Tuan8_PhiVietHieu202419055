package events;

import enums.AlertSeverity;
import java.time.LocalDateTime;

// Dữ liệu gửi kèm khi thiết bị phát cảnh báo: pin yếu, tín hiệu yếu, mất phản hồi
public class DeviceAlertEvent {
    private final String deviceId;
    private final String message;
    // MaintenanceService dựa vào mức này để chỉ tạo yêu cầu bảo trì khi CRITICAL
    private final AlertSeverity severity;
    private final LocalDateTime occurredAt;

    public DeviceAlertEvent(String deviceId, String message,
                            AlertSeverity severity, LocalDateTime occurredAt) {
        this.deviceId = deviceId;
        this.message = message;
        this.severity = severity;
        this.occurredAt = occurredAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMessage() {
        return message;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
