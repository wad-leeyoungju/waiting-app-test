package co.wadcorp.waiting.api.service.waiting.management;

import co.wadcorp.waiting.api.controller.waiting.management.dto.request.ManagementStockUpdateRequest;
import co.wadcorp.waiting.api.service.waiting.management.dto.response.ManagementStockListResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagementStockApiService {

  public ManagementStockListResponse getStocks(String shopId, LocalDate operationDate) {
    return null;
  }

  public ManagementStockListResponse updateStocks(String shopId,
      ManagementStockUpdateRequest serviceRequest, LocalDate operationDate) {
    return null;
  }
}
