package co.wadcorp.waiting.api.controller.waiting.management.dto.request;

import co.wadcorp.waiting.api.service.waiting.management.dto.request.WaitingMemoSaveServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WaitingMemoSaveRequest {

  public WaitingMemoSaveServiceRequest toServiceRequest() {
    return null;
  }
}
