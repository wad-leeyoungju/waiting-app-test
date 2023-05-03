package co.wadcorp.waiting.data.event;

import java.time.LocalDate;
import org.springframework.context.ApplicationEvent;

public record CanceledByOutOfStockEvent(String shopId, Long seq, LocalDate operationDate, String deviceId) {

}
