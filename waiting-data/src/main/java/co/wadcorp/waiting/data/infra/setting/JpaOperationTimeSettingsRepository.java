package co.wadcorp.waiting.data.infra.setting;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsEntity;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOperationTimeSettingsRepository extends OperationTimeSettingsRepository,
    JpaRepository<OperationTimeSettingsEntity, Long> {

}
