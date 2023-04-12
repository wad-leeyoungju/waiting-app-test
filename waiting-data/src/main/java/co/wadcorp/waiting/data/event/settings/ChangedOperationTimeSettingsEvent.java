package co.wadcorp.waiting.data.event.settings;

import org.springframework.context.ApplicationEvent;

public record ChangedOperationTimeSettingsEvent(String shopId) {

}
