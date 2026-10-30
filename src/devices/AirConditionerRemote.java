package devices;

import enums.AlertSeverity;

public class AirConditionerRemote extends TeachingDevice {
    private String assignedRoom;
    private boolean isResponsive;

    public AirConditionerRemote(String id, String name, String assignedRoom) {
        super(id, name);
        this.assignedRoom = assignedRoom;
        this.isResponsive = true;
    }

    public String getAssignedRoom() {
        return assignedRoom;
    }

    public boolean isResponsive() {
        return isResponsive;
    }

    public void updateResponseState(boolean isResponsive) {
        boolean wasResponsive = this.isResponsive;
        this.isResponsive = isResponsive;

        // dang khong phan hoi ma cap nhat tiep false thi khong bao lai
        if (wasResponsive && !isResponsive) {
            raiseAlert("Dieu hoa phong " + assignedRoom + " khong phan hoi", AlertSeverity.CRITICAL);
        }
    }
}
