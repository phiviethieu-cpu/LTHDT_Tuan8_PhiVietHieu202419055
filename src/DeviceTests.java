/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
import coordinator.DeviceMonitoringCoordinator;
import devices.AirConditionerRemote;
import devices.ProjectorRemote;
import devices.TeachingDevice;
import devices.WirelessMicrophone;
import enums.AlertSeverity;
import enums.DeviceStatus;
import exceptions.InvalidDeviceReadingException;
import listeners.AlertListener;
import services.ConsoleNotificationService;
import services.DeviceAuditLogger;
import services.MaintenanceService;

import java.util.ArrayList;
import java.util.List;

// Kiểm thử tự động: mỗi ca tự so kết quả với mong đợi và in PASS/FAIL.
public class DeviceTests {
    private static int passed = 0;
    private static int failed = 0;

    // So sánh kết quả thực tế với mong đợi rồi in ra PASS hoặc FAIL.
    private static void check(String name, Object expected, Object actual) {
        if (expected.equals(actual)) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name + " | mong doi: " + expected + " | thuc te: " + actual);
        }
    }

    // Gắn một listener ghi lại mức độ của mọi cảnh báo vào danh sách.
    private static List<AlertSeverity> recordAlerts(TeachingDevice d) {
        List<AlertSeverity> alerts = new ArrayList<>();
        d.addAlertListener((sender, e) -> alerts.add(e.getSeverity()));
        return alerts;
    }

    // Chạy toàn bộ các ca kiểm thử.
    public static void main(String[] args) {
        testProjectorThresholds();
        testMicrophoneThresholds();
        testInvalidReadings();
        testChangeStatus();
        testNullChecks();
        testSelfRemovingListener();
        testAirConditioner();
        testCoordinator();

        System.out.println();
        System.out.println("Ket qua: " + passed + " PASS, " + failed + " FAIL");
        if (failed > 0) {
            System.exit(1);
        }
    }

    // Ngưỡng 20% của máy chiếu: đúng ngưỡng, dưới ngưỡng, nạp lại cảnh báo, 0 lên 5.
    private static void testProjectorThresholds() {
        ProjectorRemote p = new ProjectorRemote("PJ", "May chieu");
        List<AlertSeverity> alerts = recordAlerts(p);

        p.updateBatteryLevel(20);
        check("May chieu 100 -> 20: chua canh bao", List.of(), alerts);
        p.updateBatteryLevel(19);
        check("May chieu 20 -> 19: WARNING", List.of(AlertSeverity.WARNING), alerts);
        p.updateBatteryLevel(50);
        p.updateBatteryLevel(15);
        check("May chieu 19 -> 50 -> 15: WARNING lan 2", 2, alerts.size());
        p.updateBatteryLevel(10);
        check("May chieu 15 -> 10: khong bao lai", 2, alerts.size());
        p.updateBatteryLevel(0);
        check("May chieu 10 -> 0: CRITICAL", AlertSeverity.CRITICAL, alerts.get(2));
        p.updateBatteryLevel(0);
        p.updateBatteryLevel(5);
        check("May chieu 0 -> 0 -> 5: khong bao them", 3, alerts.size());
    }

    // Ngưỡng 30% pin và 40% tín hiệu của micro, hai giá trị độc lập với nhau.
    private static void testMicrophoneThresholds() {
        WirelessMicrophone m = new WirelessMicrophone("MIC", "Micro");
        List<AlertSeverity> alerts = recordAlerts(m);

        m.updateBatteryLevel(30);
        m.updateSignalQuality(40);
        check("Micro pin 30, tin hieu 40: chua canh bao", 0, alerts.size());
        m.updateBatteryLevel(29);
        m.updateSignalQuality(39);
        check("Micro pin 29, tin hieu 39: 2 WARNING",
                List.of(AlertSeverity.WARNING, AlertSeverity.WARNING), alerts);
        m.updateSignalQuality(0);
        check("Micro tin hieu 0: CRITICAL", AlertSeverity.CRITICAL, alerts.get(2));
    }

    // Giá trị ngoài 0-100 phải ném lỗi và không làm đổi giá trị cũ.
    private static void testInvalidReadings() {
        ProjectorRemote p = new ProjectorRemote("PJ", "May chieu");
        WirelessMicrophone m = new WirelessMicrophone("MIC", "Micro");

        check("Pin -1 nem loi", true, throwsInvalid(() -> p.updateBatteryLevel(-1)));
        check("Pin 101 nem loi", true, throwsInvalid(() -> p.updateBatteryLevel(101)));
        check("Pin giu nguyen sau loi", 100, p.getBatteryLevel());
        check("Tin hieu 101 nem loi", true, throwsInvalid(() -> m.updateSignalQuality(101)));
        check("Tin hieu giu nguyen sau loi", 100, m.getSignalQuality());
    }

    // Kiểm tra một đoạn code có ném InvalidDeviceReadingException hay không.
    private static boolean throwsInvalid(Runnable r) {
        try {
            r.run();
            return false;
        } catch (InvalidDeviceReadingException ex) {
            return true;
        }
    }

    // Trạng thái trùng không phát sự kiện, listener thấy trạng thái mới.
    private static void testChangeStatus() {
        ProjectorRemote p = new ProjectorRemote("PJ", "May chieu");
        List<String> seen = new ArrayList<>();
        p.addStatusChangedListener((sender, e) -> seen.add(e.getNewStatus() + "/" + p.getStatus()));

        p.changeStatus(DeviceStatus.BORROWED);
        p.changeStatus(DeviceStatus.BORROWED);
        check("changeStatus trung chi phat 1 lan", 1, seen.size());
        check("Listener thay trang thai da cap nhat", "BORROWED/BORROWED", seen.get(0));
    }

    // Truyền null phải bị chặn ngay tại chỗ gọi.
    private static void testNullChecks() {
        ProjectorRemote p = new ProjectorRemote("PJ", "May chieu");
        check("changeStatus(null) bi chan", true, throwsNpe(() -> p.changeStatus(null)));
        check("addAlertListener(null) bi chan", true, throwsNpe(() -> p.addAlertListener(null)));
        check("Trang thai van la AVAILABLE", DeviceStatus.AVAILABLE, p.getStatus());
    }

    // Kiểm tra một đoạn code có ném NullPointerException hay không.
    private static boolean throwsNpe(Runnable r) {
        try {
            r.run();
            return false;
        } catch (NullPointerException ex) {
            return true;
        }
    }

    // Listener tự hủy đăng ký trong lúc được gọi không được làm sót listener phía sau.
    private static void testSelfRemovingListener() {
        ProjectorRemote p = new ProjectorRemote("PJ", "May chieu");
        List<String> calls = new ArrayList<>();
        AlertListener[] self = new AlertListener[1];
        self[0] = (sender, e) -> {
            calls.add("A");
            p.removeAlertListener(self[0]);
        };
        p.addAlertListener(self[0]);
        p.addAlertListener((sender, e) -> calls.add("B"));

        p.updateBatteryLevel(10);
        check("Listener A tu huy, B van duoc goi", List.of("A", "B"), calls);
        p.updateBatteryLevel(0);
        check("Lan sau chi con B", List.of("A", "B", "B"), calls);
    }

    // Điều hòa: báo một lần khi mất phản hồi, phản hồi lại rồi mất lần nữa thì báo lại.
    private static void testAirConditioner() {
        AirConditionerRemote ac = new AirConditionerRemote("AC", "Dieu hoa", "P101");
        List<AlertSeverity> alerts = recordAlerts(ac);

        ac.updateResponseState(false);
        ac.updateResponseState(false);
        check("Dieu hoa false 2 lan: 1 CRITICAL", 1, alerts.size());
        ac.updateResponseState(true);
        ac.updateResponseState(false);
        check("Dieu hoa true -> false: bao lai", 2, alerts.size());
    }

    // Coordinator: không đăng ký trùng, close() chỉ gỡ listener của nó, subscribe lại được.
    private static void testCoordinator() {
        ProjectorRemote p = new ProjectorRemote("PJ", "May chieu");
        List<AlertSeverity> external = recordAlerts(p);
        DeviceAuditLogger logger = new DeviceAuditLogger();
        MaintenanceService maintenance = new MaintenanceService();
        DeviceMonitoringCoordinator coordinator = new DeviceMonitoringCoordinator(
                List.of(p), new ConsoleNotificationService(), logger, maintenance);

        coordinator.unsubscribe();
        coordinator.subscribe();
        coordinator.subscribe();
        p.updateBatteryLevel(0);
        check("Subscribe 2 lan: chi 1 yeu cau bao tri", 1, maintenance.getRequests().size());

        coordinator.close();
        p.updateBatteryLevel(50);
        p.updateBatteryLevel(0);
        p.changeStatus(DeviceStatus.BORROWED);
        check("Sau close: khong them yeu cau bao tri", 1, maintenance.getRequests().size());
        check("Sau close: khong ghi lich su", 0, logger.getEntries().size());
        check("Sau close: listener ben ngoai van nhan", 2, external.size());

        coordinator.subscribe();
        p.changeStatus(DeviceStatus.AVAILABLE);
        check("Subscribe lai sau close: ghi lich su", 1, logger.getEntries().size());
    }
}
