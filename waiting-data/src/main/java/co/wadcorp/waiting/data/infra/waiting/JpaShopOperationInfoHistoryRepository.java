package co.wadcorp.waiting.data.infra.waiting;

import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoHistoryEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShopOperationInfoHistoryRepository extends ShopOperationInfoHistoryRepository,
    JpaRepository<ShopOperationInfoHistoryEntity, Long> {

}
