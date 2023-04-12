package co.wadcorp.waiting.data.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

public record CanceledByShopEvent(String shopId, Long waitingHistorySeq, LocalDate operationDate) {
}
