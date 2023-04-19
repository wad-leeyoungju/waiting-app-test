package co.wadcorp.waiting.data.event;

import java.time.LocalDate;
import java.util.Objects;
import org.springframework.context.ApplicationEvent;

public final class RegisteredEvent {

  private final String shopId;
  private final Long waitingHistorySeq;
  private final LocalDate operationDate;

  public RegisteredEvent(String shopId, Long waitingHistorySeq, LocalDate operationDate) {
    this.shopId = shopId;
    this.waitingHistorySeq = waitingHistorySeq;
    this.operationDate = operationDate;
  }

  public String shopId() {
    return shopId;
  }

  public Long waitingHistorySeq() {
    return waitingHistorySeq;
  }

  public LocalDate operationDate() {
    return operationDate;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (RegisteredEvent) obj;
    return Objects.equals(this.shopId, that.shopId) &&
        Objects.equals(this.waitingHistorySeq, that.waitingHistorySeq) &&
        Objects.equals(this.operationDate, that.operationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shopId, waitingHistorySeq, operationDate);
  }

  @Override
  public String toString() {
    return "RegisteredEvent[" +
        "shopId=" + shopId + ", " +
        "waitingHistorySeq=" + waitingHistorySeq + ", " +
        "operationDate=" + operationDate + ']';
  }

}
