package co.wadcorp.waiting.data.service.waiting;

import co.wadcorp.waiting.data.domain.settings.HomeSettingsRepository;
import co.wadcorp.waiting.data.domain.settings.OptionSettingsRepository;
import co.wadcorp.waiting.data.domain.waiting.WaitingEntity;
import co.wadcorp.waiting.data.domain.waiting.WaitingHistories;
import co.wadcorp.waiting.data.domain.waiting.WaitingHistoryEntity;
import co.wadcorp.waiting.data.domain.waiting.WaitingHistoryRepository;
import co.wadcorp.waiting.data.domain.waiting.WaitingRepository;
import co.wadcorp.waiting.data.domain.waiting.validator.WaitingCancelValidator;
import co.wadcorp.waiting.data.exception.AppException;
import co.wadcorp.waiting.data.exception.ErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WaitingChangeStatusService {

  private final WaitingRepository waitingRepository;
  private final WaitingHistoryRepository waitingHistoryRepository;
  private final HomeSettingsRepository homeSettingsRepository;
  private final OptionSettingsRepository optionSettingsRepository;

  public WaitingHistoryEntity cancelByCustomer(String waitingId) {
    WaitingEntity waiting = findByWaitingId(waitingId);

    WaitingCancelValidator.validateStatus(waiting);

    waiting.cancelByCustomer();

    return waitingHistoryRepository.save(transFromWaitingToHistory(waiting));
  }

  private WaitingEntity findByWaitingId(String waitingId) {
    return waitingRepository.findByWaitingId(waitingId)
        .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_WAITING));
  }

  private WaitingHistoryEntity transFromWaitingToHistory(WaitingEntity waiting) {
    return new WaitingHistoryEntity(waiting);
  }

  public WaitingHistoryEntity putOff(String waitingId, LocalDate operationDate,
      Long maxWaitingOrder) {
    WaitingEntity waiting = findByWaitingId(waitingId);

    WaitingHistories waitingHistories = new WaitingHistories(
        waitingHistoryRepository.findByWaitingSeq(waiting.getSeq()));

    waiting.putOff(maxWaitingOrder.intValue(), operationDate, waitingHistories);

    return waitingHistoryRepository.save(transFromWaitingToHistory(waiting));
  }
}
