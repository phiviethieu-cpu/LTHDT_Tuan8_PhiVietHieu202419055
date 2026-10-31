/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package filters;

import events.DeviceAlertEvent;

// thay cho delegate bool AlertFilter(DeviceAlertEventArgs alert) ben C#
@FunctionalInterface
public interface AlertFilter {
    // Trả về true nếu cảnh báo cần được xử lý.
    boolean test(DeviceAlertEvent alert);
}
