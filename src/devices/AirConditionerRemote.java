/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package devices;

import enums.AlertSeverity;

// Điều khiển điều hòa, cảnh báo khi điều hòa không phản hồi.
public class AirConditionerRemote extends TeachingDevice {
    private String assignedRoom;
    private boolean isResponsive;

    // Điều hòa mới tạo được coi là đang phản hồi.
    public AirConditionerRemote(String id, String name, String assignedRoom) {
        super(id, name);
        this.assignedRoom = assignedRoom;
        this.isResponsive = true;
    }

    // Phòng đặt điều hòa.
    public String getAssignedRoom() {
        return assignedRoom;
    }

    // Điều hòa có đang phản hồi hay không.
    public boolean isResponsive() {
        return isResponsive;
    }

    // Cập nhật trạng thái phản hồi, báo CRITICAL khi chuyển sang không phản hồi.
    public void updateResponseState(boolean isResponsive) {
        boolean wasResponsive = this.isResponsive;
        this.isResponsive = isResponsive;

        // dang khong phan hoi ma cap nhat tiep false thi khong bao lai
        if (wasResponsive && !isResponsive) {
            raiseAlert("Dieu hoa phong " + assignedRoom + " khong phan hoi", AlertSeverity.CRITICAL);
        }
    }
}
