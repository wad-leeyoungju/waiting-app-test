package co.wadcorp.waiting.api.controller.waiting.management.dto.request;

import co.wadcorp.waiting.api.service.waiting.management.dto.request.ManagementStockUpdateServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagementStockUpdateRequest {

  public ManagementStockUpdateServiceRequest toServiceRequest() {
    return null;
  }
}
