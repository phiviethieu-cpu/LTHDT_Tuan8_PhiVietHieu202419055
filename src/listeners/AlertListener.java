/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package listeners;

import events.DeviceAlertEvent;

// Listener nhận cảnh báo từ thiết bị.
@FunctionalInterface
public interface AlertListener {
    // Được gọi khi thiết bị phát cảnh báo.
    void onAlert(Object sender, DeviceAlertEvent e);
}
