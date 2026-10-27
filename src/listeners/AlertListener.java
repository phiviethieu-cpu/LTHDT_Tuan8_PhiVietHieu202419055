package listeners;

import events.DeviceAlertEvent;

@FunctionalInterface
public interface AlertListener {
    void onAlert(Object sender, DeviceAlertEvent e);
}
