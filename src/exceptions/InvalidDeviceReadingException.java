/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package exceptions;

// Ném khi pin hoặc tín hiệu nằm ngoài 0-100
public class InvalidDeviceReadingException extends RuntimeException {
    private final String deviceId;
    private final int invalidValue;

    // readingName là tên giá trị bị sai, ví dụ "Pin" hoặc "Tin hieu".
    public InvalidDeviceReadingException(String deviceId, int invalidValue, String readingName) {
        super(readingName + " cua " + deviceId + " khong hop le: " + invalidValue + " (phai tu 0 den 100)");
        this.deviceId = deviceId;
        this.invalidValue = invalidValue;
    }

    // Mã thiết bị gửi giá trị sai.
    public String getDeviceId() {
        return deviceId;
    }

    // Giá trị sai đã gửi lên.
    public int getInvalidValue() {
        return invalidValue;
    }
}
