package devices;

import enums.AlertSeverity;
import enums.DeviceStatus;
import events.DeviceAlertEvent;
import events.DeviceStatusChangedEvent;
import listeners.AlertListener;
import listeners.StatusChangedListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class TeachingDevice {
    private String id;
    private String name;
    private DeviceStatus status;

    // private de ben ngoai chi add/remove duoc, khong tu goi su kien
    private List<StatusChangedListener> statusChangedListeners = new ArrayList<>();
    private List<AlertListener> alertListeners = new ArrayList<>();

    protected TeachingDevice(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = DeviceStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void addStatusChangedListener(StatusChangedListener listener) {
        statusChangedListeners.add(listener);
    }

    public void removeStatusChangedListener(StatusChangedListener listener) {
        statusChangedListeners.remove(listener);
    }

    public void addAlertListener(AlertListener listener) {
        alertListeners.add(listener);
    }

    public void removeAlertListener(AlertListener listener) {
        alertListeners.remove(listener);
    }

    public void changeStatus(DeviceStatus newStatus) {
        if (newStatus == status) {
            return;
        }

        DeviceStatus oldStatus = status;
        status = newStatus;

        onStatusChanged(new DeviceStatusChangedEvent(id, oldStatus, newStatus, LocalDateTime.now()));
    }

    protected void raiseAlert(String message, AlertSeverity severity) {
        onAlertRaised(new DeviceAlertEvent(id, message, severity, LocalDateTime.now()));
    }

    protected void onStatusChanged(DeviceStatusChangedEvent e) {
        for (StatusChangedListener l : statusChangedListeners) {
            l.onStatusChanged(this, e);
        }
    }

    protected void onAlertRaised(DeviceAlertEvent e) {
        for (AlertListener l : alertListeners) {
            l.onAlert(this, e);
        }
    }
}
