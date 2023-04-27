package co.wadcorp.waiting.api.service.waiting.management;

import static co.wadcorp.libs.stream.StreamUtils.convert;
import static co.wadcorp.libs.stream.StreamUtils.convertToMap;

import co.wadcorp.waiting.api.controller.waiting.management.dto.request.ManagementStockUpdateRequest;
import co.wadcorp.waiting.api.service.waiting.management.dto.request.ManagementStockUpdateServiceRequest;
import co.wadcorp.waiting.api.service.waiting.management.dto.response.ManagementStockListResponse;
import co.wadcorp.waiting.data.domain.menu.MenuEntity;
import co.wadcorp.waiting.data.domain.stock.CategoryEntity;
import co.wadcorp.waiting.data.domain.stock.CategoryQueryRepository;
import co.wadcorp.waiting.data.domain.stock.MenuQueryRepository;
import co.wadcorp.waiting.data.domain.stock.StockEntity;
import co.wadcorp.waiting.data.service.stock.StockService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagementStockApiService {

  private final CategoryQueryRepository categoryQueryRepository;
  private final MenuQueryRepository menuQueryRepository;
  private final StockService stockService;

  public ManagementStockListResponse getStocks(String shopId, LocalDate operationDate) {
    List<CategoryEntity> categories = categoryQueryRepository.findAllBy(shopId);
    Map<String, Set<String>> categoryMenuGroupingMap = createCategoryMenuMap(categories);

    List<MenuEntity> menus = menuQueryRepository.findAllBy(shopId);
    Map<String, MenuEntity> menuMap = convertToMap(menus, MenuEntity::getMenuId);
    Map<String, StockEntity> stockMap = createStockMap(menus, operationDate);

    return ManagementStockListResponse.of(categories, categoryMenuGroupingMap, menuMap, stockMap);
  }

  private Map<String, StockEntity> createStockMap(List<MenuEntity> menus, LocalDate operationDate) {
    return null;
  }

  @Transactional
  public ManagementStockListResponse updateStocks(String shopId,
      ManagementStockUpdateServiceRequest request, LocalDate operationDate) {
    stockService.addDailyStockAndUpdateOutOfStock(request.getMenuDtos(), operationDate);

    return getStocks(shopId, operationDate);
  }

  private Map<String, Set<String>> createCategoryMenuMap(List<CategoryEntity> list) {
    return null;
  }
}
