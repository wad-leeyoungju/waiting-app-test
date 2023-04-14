package co.wadcorp.waiting.api.service.settings;

import co.wadcorp.waiting.api.model.settings.request.AlarmSettingsRequest;
import co.wadcorp.waiting.api.model.settings.response.AlarmSettingsResponse;
import co.wadcorp.waiting.data.domain.settings.AlarmSettingsEntity;
import co.wadcorp.waiting.data.service.settings.AlarmSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmSettingsApiService {

  private final AlarmSettingsService alarmSettingsService;

  public AlarmSettingsResponse getWaitingAlarmSettings(String shopId) {
    AlarmSettingsEntity entity = alarmSettingsService.getAlarmSettings(shopId);
    return AlarmSettingsResponse.toDto(entity);
  }

  public AlarmSettingsResponse save(String shopId, AlarmSettingsRequest request) {
    AlarmSettingsEntity entity = request.toEntity(shopId);
    return AlarmSettingsResponse.toDto(alarmSettingsService.save(entity));
  }
}
