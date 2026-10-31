/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package services;

import events.DeviceStatusChangedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Ghi lại lịch sử đổi trạng thái của các thiết bị.
public class DeviceAuditLogger {
    private List<String> entries = new ArrayList<>();

    // Danh sách lịch sử, chỉ đọc.
    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    // Thêm một dòng lịch sử khi thiết bị đổi trạng thái.
    public void handleStatusChanged(Object sender, DeviceStatusChangedEvent e) {
        entries.add(e.getChangedAt() + ": " + e.getDeviceId() + ", "
                + e.getOldStatus() + " -> " + e.getNewStatus());
    }
}
