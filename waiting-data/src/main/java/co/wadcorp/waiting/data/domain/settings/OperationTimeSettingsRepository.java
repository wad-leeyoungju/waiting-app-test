package co.wadcorp.waiting.data.domain.settings;

import java.util.List;
import java.util.Optional;

public interface OperationTimeSettingsRepository {

  Optional<OperationTimeSettingsEntity> findFirstByShopIdAndIsPublished(String shopId, Boolean isPublished);

  List<OperationTimeSettingsEntity> findAllByShopIdInAndIsPublished(List<String> shopIds, Boolean isPublished);

  List<OperationTimeSettingsEntity> findTop2ByShopIdOrderBySeqDesc(String shopId);

  OperationTimeSettingsEntity save(OperationTimeSettingsEntity operationTimeSettingsEntity);

  void deleteAllInBatch();

  <S extends OperationTimeSettingsEntity> List<S> saveAll(Iterable<S> entities);

}
