package exceptions;

// Ném khi pin hoặc tín hiệu nằm ngoài 0-100
public class InvalidDeviceReadingException extends RuntimeException {
    private final String deviceId;
    private final int invalidValue;

    public InvalidDeviceReadingException(String deviceId, int invalidValue, String readingName) {
        super(readingName + " cua " + deviceId + " khong hop le: " + invalidValue + " (phai tu 0 den 100)");
        this.deviceId = deviceId;
        this.invalidValue = invalidValue;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getInvalidValue() {
        return invalidValue;
    }
}
