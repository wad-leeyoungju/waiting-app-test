package co.wadcorp.waiting.api.service.waiting.management.dto.response;

import co.wadcorp.waiting.data.domain.menu.MenuEntity;
import co.wadcorp.waiting.data.domain.stock.CategoryEntity;
import co.wadcorp.waiting.data.domain.stock.StockEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagementStockListResponse {

  public static ManagementStockListResponse of(List<CategoryEntity> categories,
      Map<String, Set<String>> categoryMenuGroupingMap, Map<String, MenuEntity> menuMap,
      Map<String, StockEntity> stockMap) {
    return null;
  }
}
