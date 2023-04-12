package co.wadcorp.waiting.api.model.waiting.request;

import co.wadcorp.waiting.api.model.waiting.vo.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeShopOperationStatusRequest {

  private OperationStatus operationStatus;
  private String pauseReasonId;
  private Integer pausePeriod;
}
