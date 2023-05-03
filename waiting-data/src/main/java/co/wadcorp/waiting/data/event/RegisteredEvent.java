package co.wadcorp.waiting.data.event;

import java.time.LocalDate;
import java.util.Objects;
import org.springframework.context.ApplicationEvent;

public record RegisteredEvent(String shopId, Long waitingHistorySeq, LocalDate operationDate, String deviceId) {

}
