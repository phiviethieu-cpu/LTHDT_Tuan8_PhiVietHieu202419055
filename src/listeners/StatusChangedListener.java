package listeners;

import events.DeviceStatusChangedEvent;

// Tương đương EventHandler<DeviceStatusChangedEventArgs> bên C#, sender là thiết bị phát sự kiện
@FunctionalInterface
public interface StatusChangedListener {
    void onStatusChanged(Object sender, DeviceStatusChangedEvent e);
}
