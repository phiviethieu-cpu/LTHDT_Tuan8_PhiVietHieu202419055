/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package services;

import enums.AlertSeverity;
import events.DeviceAlertEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Tạo yêu cầu bảo trì khi thiết bị có lỗi nghiêm trọng.
public class MaintenanceService {
    private final List<String> requests = new ArrayList<>();

    // Danh sách yêu cầu bảo trì, chỉ đọc.
    public List<String> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    // Nhận cảnh báo, chỉ tạo yêu cầu khi mức CRITICAL.
    public void handleCriticalAlert(Object sender, DeviceAlertEvent e) {
        // chi tao yeu cau bao tri voi loi nghiem trong
        if (e.getSeverity() != AlertSeverity.CRITICAL) {
            return;
        }

        requests.add("Maintenance request for " + e.getDeviceId() + ": " + e.getMessage());
    }
}
