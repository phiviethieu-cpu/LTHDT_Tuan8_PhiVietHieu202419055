import coordinator.DeviceMonitoringCoordinator;
import devices.AirConditionerRemote;
import devices.ProjectorRemote;
import devices.TeachingDevice;
import devices.WirelessMicrophone;
import enums.AlertSeverity;
import enums.DeviceStatus;
import exceptions.InvalidDeviceReadingException;
import filters.FilteredAlertSubscriber;
import services.ConsoleNotificationService;
import services.DeviceAuditLogger;
import services.MaintenanceService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("He thong giam sat thiet bi phong day");

        ProjectorRemote projector = new ProjectorRemote("PJ01", "May chieu P101");
        AirConditionerRemote ac = new AirConditionerRemote("AC01", "Dieu hoa", "P101");
        WirelessMicrophone mic = new WirelessMicrophone("MIC01", "Micro 01");

        List<TeachingDevice> devices = new ArrayList<>();
        devices.add(projector);
        devices.add(ac);
        devices.add(mic);

        ConsoleNotificationService notificationService = new ConsoleNotificationService();
        DeviceAuditLogger auditLogger = new DeviceAuditLogger();
        MaintenanceService maintenanceService = new MaintenanceService();

        FilteredAlertSubscriber criticalOnly = new FilteredAlertSubscriber(
                alert -> alert.getSeverity() == AlertSeverity.CRITICAL,
                alert -> System.out.println("CRITICAL: " + alert.getDeviceId() + " - " + alert.getMessage()));
        for (TeachingDevice d : devices) {
            d.addAlertListener(criticalOnly::handleAlert);
        }

        try (DeviceMonitoringCoordinator coordinator = new DeviceMonitoringCoordinator(
                devices, notificationService, auditLogger, maintenanceService)) {
            coordinator.subscribe();
            coordinator.subscribe();

            System.out.println("\n--- Doi trang thai ---");
            projector.changeStatus(DeviceStatus.BORROWED);
            projector.changeStatus(DeviceStatus.BORROWED);
            projector.changeStatus(DeviceStatus.AVAILABLE);
            ac.changeStatus(DeviceStatus.UNDER_MAINTENANCE);

            System.out.println("\n--- Pin may chieu ---");
            projector.updateBatteryLevel(50);
            projector.updateBatteryLevel(15);
            projector.updateBatteryLevel(10);
            projector.updateBatteryLevel(0);

            System.out.println("\n--- Dieu hoa ---");
            ac.updateResponseState(false);
            ac.updateResponseState(false);

            System.out.println("\n--- Micro ---");
            mic.updateBatteryLevel(25);
            mic.updateSignalQuality(35);
            mic.updateSignalQuality(0);

            System.out.println("\n--- Gia tri khong hop le ---");
            try {
                mic.updateBatteryLevel(120);
            } catch (InvalidDeviceReadingException ex) {
                System.out.println("Loi: " + ex.getMessage());
            }
        }

        System.out.println("\n--- Sau khi close ---");
        mic.updateBatteryLevel(0);
        ac.changeStatus(DeviceStatus.AVAILABLE);

        System.out.println("\nLich su trang thai:");
        for (String s : auditLogger.getEntries()) {
            System.out.println(s);
        }

        System.out.println("\nYeu cau bao tri:");
        for (String s : maintenanceService.getRequests()) {
            System.out.println(s);
        }
    }
}
