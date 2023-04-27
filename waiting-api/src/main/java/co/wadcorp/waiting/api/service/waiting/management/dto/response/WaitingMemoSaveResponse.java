package co.wadcorp.waiting.api.service.waiting.management.dto.response;

import co.wadcorp.waiting.data.domain.memo.WaitingMemoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WaitingMemoSaveResponse {

  public static WaitingMemoSaveResponse of(WaitingMemoEntity save) {
    return null;
  }
}
