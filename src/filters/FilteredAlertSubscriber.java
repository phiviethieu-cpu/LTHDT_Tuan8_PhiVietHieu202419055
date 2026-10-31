/****************
 * Mã sinh viên: 202419055
 * Họ tên: Phí Việt Hiếu
 ****************/
package filters;

import events.DeviceAlertEvent;

import java.util.Objects;
import java.util.function.Consumer;

// Subscriber chỉ xử lý những cảnh báo qua được bộ lọc.
public class FilteredAlertSubscriber {
    private final AlertFilter filter;
    private final Consumer<DeviceAlertEvent> action;

    // filter quyết định có xử lý hay không, action là việc cần làm.
    public FilteredAlertSubscriber(AlertFilter filter, Consumer<DeviceAlertEvent> action) {
        this.filter = Objects.requireNonNull(filter, "filter");
        this.action = Objects.requireNonNull(action, "action");
    }

    // Chạy action nếu cảnh báo qua được bộ lọc.
    public void handleAlert(Object sender, DeviceAlertEvent e) {
        if (filter.test(e)) {
            action.accept(e);
        }
    }
}
