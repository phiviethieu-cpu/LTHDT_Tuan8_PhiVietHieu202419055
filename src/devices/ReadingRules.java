/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package devices;

import enums.AlertSeverity;
import exceptions.InvalidDeviceReadingException;

// Quy tắc dùng chung cho các giá trị đo theo phần trăm (pin, tín hiệu).
// Không đặt trong TeachingDevice vì điều hòa không có pin hay tín hiệu.
final class ReadingRules {
    private ReadingRules() {
    }

    // Ném InvalidDeviceReadingException nếu giá trị nằm ngoài 0-100.
    static void validate(String deviceId, int value, String readingName) {
        if (value < 0 || value > 100) {
            throw new InvalidDeviceReadingException(deviceId, value, readingName);
        }
    }

    // Mức cảnh báo khi giá trị đổi từ oldValue sang newValue, trả về null nếu không cần cảnh báo.
    static AlertSeverity levelChange(int oldValue, int newValue, int warnBelow) {
        // chi canh bao khi vua roi xuong vung thap, con dang o vung thap roi thi thoi
        if (newValue == 0 && oldValue != 0) {
            return AlertSeverity.CRITICAL;
        }
        if (newValue > 0 && newValue < warnBelow && oldValue >= warnBelow) {
            return AlertSeverity.WARNING;
        }
        return null;
    }
}
