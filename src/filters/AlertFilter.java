package filters;

import events.DeviceAlertEvent;

// thay cho delegate bool AlertFilter(DeviceAlertEventArgs alert) ben C#
@FunctionalInterface
public interface AlertFilter {
    boolean test(DeviceAlertEvent alert);
}
