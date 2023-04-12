package co.wadcorp.waiting.data.event;

import org.springframework.context.ApplicationEvent;

public record ShopOperationUpdatedEvent(String shopId) {

}
