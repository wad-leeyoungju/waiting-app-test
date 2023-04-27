package co.wadcorp.waiting.api.service.waiting.management.dto.request;

import co.wadcorp.waiting.data.domain.memo.WaitingMemoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaitingMemoSaveServiceRequest {

  public WaitingMemoEntity toEntity(String shopId) {
    return null;
  }
}
