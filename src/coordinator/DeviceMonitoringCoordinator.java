/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package coordinator;

import devices.TeachingDevice;
import listeners.AlertListener;
import listeners.StatusChangedListener;
import services.ConsoleNotificationService;
import services.DeviceAuditLogger;
import services.MaintenanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Gom việc đăng ký và hủy đăng ký các service với thiết bị vào một chỗ.
public class DeviceMonitoringCoordinator implements AutoCloseable {
    private final List<TeachingDevice> devices;
    private final ConsoleNotificationService notificationService;
    private final DeviceAuditLogger auditLogger;
    private final MaintenanceService maintenanceService;

    // phai giu lai listener da dang ky, vi moi lan viet auditLogger::handleStatusChanged
    // Java tao ra 1 object moi nen remove bang object moi se khong go duoc
    private final StatusChangedListener statusListener;
    private final AlertListener notifyListener;
    private final AlertListener maintenanceListener;

    private boolean subscribed = false;

    // Nhận danh sách thiết bị và 3 service cần nối với thiết bị.
    public DeviceMonitoringCoordinator(List<TeachingDevice> devices,
                                       ConsoleNotificationService notificationService,
                                       DeviceAuditLogger auditLogger,
                                       MaintenanceService maintenanceService) {
        this.devices = new ArrayList<>(devices);
        this.notificationService = Objects.requireNonNull(notificationService, "notificationService");
        this.auditLogger = Objects.requireNonNull(auditLogger, "auditLogger");
        this.maintenanceService = Objects.requireNonNull(maintenanceService, "maintenanceService");

        statusListener = this.auditLogger::handleStatusChanged;
        notifyListener = this.notificationService::handleAlert;
        maintenanceListener = this.maintenanceService::handleCriticalAlert;
    }

    // Đăng ký các service cho mọi thiết bị, gọi nhiều lần cũng chỉ đăng ký một lần.
    public void subscribe() {
        if (subscribed) {
            return;
        }

        for (TeachingDevice d : devices) {
            d.addStatusChangedListener(statusListener);
            d.addAlertListener(notifyListener);
            d.addAlertListener(maintenanceListener);
        }
        subscribed = true;
    }

    // Hủy đúng các đăng ký đã tạo trong subscribe().
    public void unsubscribe() {
        if (!subscribed) {
            return;
        }

        for (TeachingDevice d : devices) {
            d.removeStatusChangedListener(statusListener);
            d.removeAlertListener(notifyListener);
            d.removeAlertListener(maintenanceListener);
        }
        subscribed = false;
    }

    // Gọi unsubscribe(), dùng được với try-with-resources.
    @Override
    public void close() {
        unsubscribe();
    }
}
