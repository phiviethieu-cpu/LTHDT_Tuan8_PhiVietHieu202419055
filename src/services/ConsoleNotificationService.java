package services;

import events.DeviceAlertEvent;

public class ConsoleNotificationService {
    public void handleAlert(Object sender, DeviceAlertEvent e) {
        System.out.println("[" + e.getSeverity() + "] " + e.getDeviceId() + ": " + e.getMessage());
    }
}
