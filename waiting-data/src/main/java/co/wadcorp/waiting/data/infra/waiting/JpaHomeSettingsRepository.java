package co.wadcorp.waiting.data.infra.waiting;

import co.wadcorp.waiting.data.domain.settings.HomeSettingsEntity;
import co.wadcorp.waiting.data.domain.settings.HomeSettingsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHomeSettingsRepository extends HomeSettingsRepository, JpaRepository<HomeSettingsEntity, Long> {
}
