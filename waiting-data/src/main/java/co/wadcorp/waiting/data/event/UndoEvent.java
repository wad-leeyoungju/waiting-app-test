package co.wadcorp.waiting.data.event;

import java.time.LocalDate;
import org.springframework.context.ApplicationEvent;

public record UndoEvent(String shopId, Long waitingHistorySeq, LocalDate operationDate, String deviceId) {

}
