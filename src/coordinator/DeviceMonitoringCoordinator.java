package coordinator;

import devices.TeachingDevice;
import listeners.AlertListener;
import listeners.StatusChangedListener;
import services.ConsoleNotificationService;
import services.DeviceAuditLogger;
import services.MaintenanceService;

import java.util.ArrayList;
import java.util.List;

public class DeviceMonitoringCoordinator implements AutoCloseable {
    private List<TeachingDevice> devices;
    private ConsoleNotificationService notificationService;
    private DeviceAuditLogger auditLogger;
    private MaintenanceService maintenanceService;

    // phai giu lai listener da dang ky, vi moi lan viet auditLogger::handleStatusChanged
    // Java tao ra 1 object moi nen remove bang object moi se khong go duoc
    private StatusChangedListener statusListener;
    private AlertListener notifyListener;
    private AlertListener maintenanceListener;

    private boolean subscribed = false;

    public DeviceMonitoringCoordinator(List<TeachingDevice> devices,
                                       ConsoleNotificationService notificationService,
                                       DeviceAuditLogger auditLogger,
                                       MaintenanceService maintenanceService) {
        this.devices = new ArrayList<>(devices);
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
        this.maintenanceService = maintenanceService;

        statusListener = this.auditLogger::handleStatusChanged;
        notifyListener = this.notificationService::handleAlert;
        maintenanceListener = this.maintenanceService::handleCriticalAlert;
    }

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

    @Override
    public void close() {
        unsubscribe();
    }
}
