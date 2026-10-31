/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package devices;

import enums.AlertSeverity;
import enums.DeviceStatus;
import events.DeviceAlertEvent;
import events.DeviceStatusChangedEvent;
import listeners.AlertListener;
import listeners.StatusChangedListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Lớp cha của các thiết bị, phát sự kiện khi đổi trạng thái hoặc có cảnh báo.
public abstract class TeachingDevice {
    private String id;
    private String name;
    private DeviceStatus status;

    // private de ben ngoai chi add/remove duoc, khong tu goi su kien
    private List<StatusChangedListener> statusChangedListeners = new ArrayList<>();
    private List<AlertListener> alertListeners = new ArrayList<>();

    // Thiết bị mới tạo luôn ở trạng thái AVAILABLE.
    protected TeachingDevice(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = DeviceStatus.AVAILABLE;
    }

    // Mã thiết bị.
    public String getId() {
        return id;
    }

    // Tên thiết bị.
    public String getName() {
        return name;
    }

    // Trạng thái hiện tại.
    public DeviceStatus getStatus() {
        return status;
    }

    // Đăng ký nhận sự kiện đổi trạng thái.
    public void addStatusChangedListener(StatusChangedListener listener) {
        statusChangedListeners.add(listener);
    }

    // Hủy đăng ký sự kiện đổi trạng thái.
    public void removeStatusChangedListener(StatusChangedListener listener) {
        statusChangedListeners.remove(listener);
    }

    // Đăng ký nhận cảnh báo.
    public void addAlertListener(AlertListener listener) {
        alertListeners.add(listener);
    }

    // Hủy đăng ký nhận cảnh báo.
    public void removeAlertListener(AlertListener listener) {
        alertListeners.remove(listener);
    }

    // Đổi trạng thái rồi phát sự kiện, bỏ qua nếu trạng thái mới trùng trạng thái cũ.
    public void changeStatus(DeviceStatus newStatus) {
        if (newStatus == status) {
            return;
        }

        DeviceStatus oldStatus = status;
        status = newStatus;

        onStatusChanged(new DeviceStatusChangedEvent(id, oldStatus, newStatus, LocalDateTime.now()));
    }

    // Lớp con gọi hàm này để phát một cảnh báo.
    protected void raiseAlert(String message, AlertSeverity severity) {
        onAlertRaised(new DeviceAlertEvent(id, message, severity, LocalDateTime.now()));
    }

    // Gọi lần lượt các listener đã đăng ký sự kiện đổi trạng thái.
    protected void onStatusChanged(DeviceStatusChangedEvent e) {
        for (StatusChangedListener l : statusChangedListeners) {
            l.onStatusChanged(this, e);
        }
    }

    // Gọi lần lượt các listener đã đăng ký nhận cảnh báo.
    protected void onAlertRaised(DeviceAlertEvent e) {
        for (AlertListener l : alertListeners) {
            l.onAlert(this, e);
        }
    }
}
