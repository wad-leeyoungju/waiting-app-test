package co.wadcorp.waiting.data.event;

import java.time.LocalDate;
import org.springframework.context.ApplicationEvent;

public record PutOffEvent(String shopId, Long waitingHistorySeq, LocalDate operationDate,
                          int beforeWaitingOrder, int afterWaitingOrder, String deviceId) {

}
