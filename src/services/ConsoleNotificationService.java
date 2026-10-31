/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package services;

import events.DeviceAlertEvent;

// In cảnh báo của thiết bị ra màn hình console.
public class ConsoleNotificationService {
    // In cảnh báo theo dạng [MỨC ĐỘ] mã thiết bị: nội dung.
    public void handleAlert(Object sender, DeviceAlertEvent e) {
        System.out.println("[" + e.getSeverity() + "] " + e.getDeviceId() + ": " + e.getMessage());
    }
}
