package co.wadcorp.waiting.data.service.settings;

import co.wadcorp.waiting.data.domain.settings.DefaultOperationTimeSettingDataFactory;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsEntity;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OperationTimeSettingsService {

  private final OperationTimeSettingsRepository operationTimeSettingsRepository;

  public OperationTimeSettingsEntity getOperationTimeSettings(String shopId) {
    return operationTimeSettingsRepository.findFirstByShopIdAndIsPublished(shopId, true)
        .orElseGet(() -> createDefaultOperationTimeSettings(shopId));
  }

  private OperationTimeSettingsEntity createDefaultOperationTimeSettings(String shopId) {
    return OperationTimeSettingsEntity.builder()
        .shopId(shopId)
        .operationTimeSettingsData(DefaultOperationTimeSettingDataFactory.create())
        .build();
  }

  public OperationTimeSettingsEntity saveOperationTimeSettings(
      OperationTimeSettingsEntity entity) {
    operationTimeSettingsRepository.findAllByShopIdInAndIsPublished(List.of(entity.getShopId()), true)
        .forEach(OperationTimeSettingsEntity::unPublish);

    return operationTimeSettingsRepository.save(entity);
  }

  public boolean isThereChangeInOperationTime(String shopId) {
    List<OperationTimeSettingsEntity> operationTimeSettings =
        operationTimeSettingsRepository.findTop2ByShopIdOrderBySeqDesc(shopId);

    if(operationTimeSettings.size() < 2) {
      return false;
    }

    return !operationTimeSettings.get(0).getOperationTimeSettingsData()
        .isSameOperationTimeForDays(operationTimeSettings.get(1).getOperationTimeSettingsData());
  }

}
