package filters;

import events.DeviceAlertEvent;

import java.util.function.Consumer;

public class FilteredAlertSubscriber {
    private AlertFilter filter;
    private Consumer<DeviceAlertEvent> action;

    public FilteredAlertSubscriber(AlertFilter filter, Consumer<DeviceAlertEvent> action) {
        this.filter = filter;
        this.action = action;
    }

    public void handleAlert(Object sender, DeviceAlertEvent e) {
        if (filter.test(e)) {
            action.accept(e);
        }
    }
}
