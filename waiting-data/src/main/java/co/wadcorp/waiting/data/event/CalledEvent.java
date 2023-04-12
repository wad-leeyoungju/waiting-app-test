package co.wadcorp.waiting.data.event;

import java.time.LocalDateTime;

public record CalledEvent(String shopId, Long waitingHistorySeq, LocalDateTime currentDateTime) {
}
