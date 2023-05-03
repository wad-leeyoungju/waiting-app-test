package co.wadcorp.waiting.api.internal.controller.waiting.dto.request;

import co.wadcorp.waiting.api.internal.service.waiting.dto.request.RemoteWaitingRegisterServiceRequest;
import co.wadcorp.waiting.api.internal.service.waiting.dto.request.RemoteWaitingRegisterServiceRequest.PersonOptionVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoteWaitingRegisterRequest {

  @NotBlank(message = "테이블 id는 필수입니다.")
  private String tableId;

  @NotNull(message = "총 인원은 필수입니다.")
  private Integer totalPersonCount;

  private List<PersonOptionVO> personOptions;
  private String phoneNumber;
  private Object extra;

  @Builder
  private RemoteWaitingRegisterRequest(String tableId, Integer totalPersonCount,
      List<PersonOptionVO> personOptions, String phoneNumber, Object extra) {
    this.tableId = tableId;
    this.totalPersonCount = totalPersonCount;
    this.personOptions = personOptions;
    this.phoneNumber = phoneNumber;
    this.extra = extra;
  }

  public RemoteWaitingRegisterServiceRequest toServiceRequest() {
    return RemoteWaitingRegisterServiceRequest.builder()
        .tableId(tableId)
        .totalPersonCount(totalPersonCount)
        .personOptions(personOptions)
        .phoneNumber(phoneNumber)
        .extra(extra)
        .build();
  }

}
