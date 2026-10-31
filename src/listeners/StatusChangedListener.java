/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package listeners;

import events.DeviceStatusChangedEvent;

// Tương đương EventHandler<DeviceStatusChangedEventArgs> bên C#, sender là thiết bị phát sự kiện
@FunctionalInterface
public interface StatusChangedListener {
    // Được gọi khi thiết bị đổi trạng thái.
    void onStatusChanged(Object sender, DeviceStatusChangedEvent e);
}
