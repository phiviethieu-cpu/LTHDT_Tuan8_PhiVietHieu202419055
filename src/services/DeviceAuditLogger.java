package services;

import events.DeviceStatusChangedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceAuditLogger {
    private List<String> entries = new ArrayList<>();

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void handleStatusChanged(Object sender, DeviceStatusChangedEvent e) {
        entries.add(e.getChangedAt() + ": " + e.getDeviceId() + ", "
                + e.getOldStatus() + " -> " + e.getNewStatus());
    }
}
