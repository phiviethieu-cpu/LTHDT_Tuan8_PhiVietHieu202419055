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
import java.util.Objects;

// Lớp cha của các thiết bị, phát sự kiện khi đổi trạng thái hoặc có cảnh báo.
public abstract class TeachingDevice {
    private final String id;
    private final String name;
    private DeviceStatus status;

    // private de ben ngoai chi add/remove duoc, khong tu goi su kien
    private final List<StatusChangedListener> statusChangedListeners = new ArrayList<>();
    private final List<AlertListener> alertListeners = new ArrayList<>();

    // Thiết bị mới tạo luôn ở trạng thái AVAILABLE.
    protected TeachingDevice(String id, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
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
        statusChangedListeners.add(Objects.requireNonNull(listener, "listener"));
    }

    // Hủy đăng ký sự kiện đổi trạng thái.
    public void removeStatusChangedListener(StatusChangedListener listener) {
        statusChangedListeners.remove(listener);
    }

    // Đăng ký nhận cảnh báo.
    public void addAlertListener(AlertListener listener) {
        alertListeners.add(Objects.requireNonNull(listener, "listener"));
    }

    // Hủy đăng ký nhận cảnh báo.
    public void removeAlertListener(AlertListener listener) {
        alertListeners.remove(listener);
    }

    // Đổi trạng thái rồi phát sự kiện, bỏ qua nếu trạng thái mới trùng trạng thái cũ.
    public void changeStatus(DeviceStatus newStatus) {
        Objects.requireNonNull(newStatus, "newStatus");
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
        // duyet tren ban sao: listener tu remove trong luc chay thi listener sau van duoc goi
        for (StatusChangedListener l : new ArrayList<>(statusChangedListeners)) {
            l.onStatusChanged(this, e);
        }
    }

    // Gọi lần lượt các listener đã đăng ký nhận cảnh báo.
    protected void onAlertRaised(DeviceAlertEvent e) {
        for (AlertListener l : new ArrayList<>(alertListeners)) {
            l.onAlert(this, e);
        }
    }
}
