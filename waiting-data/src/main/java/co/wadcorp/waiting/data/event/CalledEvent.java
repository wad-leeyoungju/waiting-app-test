package co.wadcorp.waiting.data.event;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record CalledEvent(String shopId, Long waitingHistorySeq, ZonedDateTime currentDateTime, String deviceId) {
}
