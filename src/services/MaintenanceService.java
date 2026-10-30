package services;

import enums.AlertSeverity;
import events.DeviceAlertEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaintenanceService {
    private List<String> requests = new ArrayList<>();

    public List<String> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public void handleCriticalAlert(Object sender, DeviceAlertEvent e) {
        // chi tao yeu cau bao tri voi loi nghiem trong
        if (e.getSeverity() != AlertSeverity.CRITICAL) {
            return;
        }

        requests.add("Maintenance request for " + e.getDeviceId() + ": " + e.getMessage());
    }
}
