package co.wadcorp.waiting.data.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

public record CanceledByCustomerEvent(String shopId, Long waitingHistorySeq, LocalDate operationDate, String deviceId) {
}
