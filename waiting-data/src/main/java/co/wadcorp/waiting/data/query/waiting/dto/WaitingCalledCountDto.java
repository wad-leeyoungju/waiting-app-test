package co.wadcorp.waiting.data.query.waiting.dto;

import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class WaitingCalledCountDto {

  public static final WaitingCalledCountDto EMPTY = new WaitingCalledCountDto(0, null);

  private final long callCount;
  private final ZonedDateTime lastCalledDateTime;

  public WaitingCalledCountDto(long callCount, ZonedDateTime lastCalledDateTime) {
    this.callCount = callCount;
    this.lastCalledDateTime = lastCalledDateTime;
  }
}
