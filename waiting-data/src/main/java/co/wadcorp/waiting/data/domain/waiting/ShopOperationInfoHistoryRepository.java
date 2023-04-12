package co.wadcorp.waiting.data.domain.waiting;

import java.time.LocalDate;
import java.util.List;

public interface ShopOperationInfoHistoryRepository {

  List<ShopOperationInfoHistoryEntity> findByShopIdAndOperationDate(String shopId, LocalDate operationDate);

  ShopOperationInfoHistoryEntity save(ShopOperationInfoHistoryEntity shopOperationInfoHistoryEntity);

  List<ShopOperationInfoHistoryEntity> findAll();

  void deleteAllInBatch();

}
