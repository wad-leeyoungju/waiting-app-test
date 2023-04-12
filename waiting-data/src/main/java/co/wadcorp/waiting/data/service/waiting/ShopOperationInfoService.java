package co.wadcorp.waiting.data.service.waiting;

import co.wadcorp.waiting.data.domain.settings.DefaultOperationTimeSettingDataFactory;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings.PauseReason;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsEntity;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsRepository;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoHistoryEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoHistoryRepository;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoRepository;
import co.wadcorp.waiting.data.exception.AppException;
import co.wadcorp.waiting.data.exception.ErrorCode;
import co.wadcorp.waiting.shared.util.ZonedDateTimeUtils;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ShopOperationInfoService {

  private static final LocalTime DEFAULT_OPERATION_END_TIME = LocalTime.of(5, 30);

  private final ShopOperationInfoRepository shopOperationInfoRepository;
  private final ShopOperationInfoHistoryRepository shopOperationInfoHistoryRepository;

  private final OperationTimeSettingsRepository operationTimeSettingsRepository;

  private final JdbcTemplate jdbcTemplate;

  public ShopOperationInfoEntity getByShopIdAndOperationDate(String shopId,
      LocalDate operationDate) {
    return shopOperationInfoRepository.findByShopIdAndOperationDate(shopId, operationDate)
        .orElse(ShopOperationInfoEntity.EMPTY_OPERATION_INFO);
  }

  public List<ShopOperationInfoEntity> findByShopIdAndOperationDateAfterOrEqual(String shopId,
      LocalDate operationDate) {
    return shopOperationInfoRepository.findByShopIdAndOperationDateAfterOrEqual(shopId,
        operationDate);
  }

  public ShopOperationInfoEntity findByShopIdAndOperationDate(String shopId,
      LocalDate operationDate) {
    return shopOperationInfoRepository.findByShopIdAndOperationDate(shopId, operationDate)
        .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_OPERATION));
  }

  public ShopOperationInfoEntity save(ShopOperationInfoEntity shopOperationInfoEntity) {
    shopOperationInfoRepository.findByShopIdAndOperationDate(shopOperationInfoEntity.getShopId(),
            shopOperationInfoEntity.getOperationDate())
        .ifPresent(item -> {
          throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_CREATED_OPERATION);
        });

    ShopOperationInfoEntity savedEntity = shopOperationInfoRepository.save(shopOperationInfoEntity);
    saveHistory(shopOperationInfoEntity);

    return savedEntity;
  }

  private void saveHistory(ShopOperationInfoEntity shopOperationInfoEntity) {
    shopOperationInfoHistoryRepository.save(
        ShopOperationInfoHistoryEntity.of(shopOperationInfoEntity));
  }

  public ShopOperationInfoEntity open(String shopId, LocalDate operationDate, ZonedDateTime nowLocalDateTime) {
    ShopOperationInfoEntity shopOperationInfoEntity = findByShopIdAndOperationDate(shopId,
        operationDate);

    if(shopOperationInfoEntity.isBeforeOperationDateTime(nowLocalDateTime)) {
      shopOperationInfoEntity.updateOperationStartDateTime(nowLocalDateTime);
    }

    if(shopOperationInfoEntity.isAfterOperationEndDateTime(nowLocalDateTime)) {
      LocalDate localDate = operationDate.plusDays(1);
      ZonedDateTime operationEndDateTime = ZonedDateTimeUtils.ofSeoul(localDate,
          DEFAULT_OPERATION_END_TIME);
      shopOperationInfoEntity.updateOperationEndDateTime(operationEndDateTime);
    }

    if(shopOperationInfoEntity.isBetweenAutoPauseRange(nowLocalDateTime)) {
      shopOperationInfoEntity.clearAutoPauseInfo();
    }

    shopOperationInfoEntity.open();
    saveHistory(shopOperationInfoEntity);

    return shopOperationInfoEntity;
  }

  public ShopOperationInfoEntity close(String shopId, LocalDate operationDate) {
    ShopOperationInfoEntity shopOperationInfoEntity = findByShopIdAndOperationDate(shopId,
        operationDate);

    shopOperationInfoEntity.close();
    saveHistory(shopOperationInfoEntity);

    return shopOperationInfoEntity;
  }

  public ShopOperationInfoEntity byPass(String shopId, LocalDate operationDate) {
    ShopOperationInfoEntity shopOperationInfoEntity = findByShopIdAndOperationDate(shopId,
        operationDate);

    shopOperationInfoEntity.byPass();
    saveHistory(shopOperationInfoEntity);

    return shopOperationInfoEntity;
  }

  public ShopOperationInfoEntity pause(String shopId, LocalDate operationDate, String pauseReasonId,
      Integer pausePeriod) {
    if (pausePeriod != -1 && (pausePeriod < 10 || pausePeriod > 180)) {
      throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.OUT_OF_RANGE_PAUSE_PERIOD);
    }

    ShopOperationInfoEntity shopOperationInfoEntity = findByShopIdAndOperationDate(shopId,
        operationDate);

    OperationTimeSettingsEntity operationTimeSettingsEntity =
        operationTimeSettingsRepository.findFirstByShopIdAndIsPublished(shopId, true)
            .orElseGet(() -> new OperationTimeSettingsEntity(shopId,
                DefaultOperationTimeSettingDataFactory.create()));

    PauseReason reason = operationTimeSettingsEntity.findReason(pauseReasonId);
    shopOperationInfoEntity.pause(reason.getId(), reason.getReason(), pausePeriod);
    saveHistory(shopOperationInfoEntity);

    return shopOperationInfoEntity;
  }
}
