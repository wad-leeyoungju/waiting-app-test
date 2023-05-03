package co.wadcorp.waiting.api.service.waiting.register.dto.response;

import co.wadcorp.waiting.data.domain.stock.StockEntity;
import co.wadcorp.waiting.data.query.displaymenu.dto.DisplayMenuDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterWaitingOrderMenuResponse {

  private final List<CategoryDto> categories;

  @Builder
  public RegisterWaitingOrderMenuResponse(List<CategoryDto> categories) {
    this.categories = categories;
  }

  @Getter
  public static class CategoryDto {

    private final String id;
    private final String name;
    private final int ordering;
    private final List<MenuDto> menus;

    @Builder
    private CategoryDto(String id, String name, int ordering, List<MenuDto> menus) {
      this.id = id;
      this.name = name;
      this.ordering = ordering;
      this.menus = menus;
    }
  }

  @Getter
  public static class MenuDto {

    private final String id;
    private final String name;
    private final int ordering;
    private final BigDecimal unitPrice;
    private final Boolean isUsedMenuQuantityPerTeam;
    private final Integer menuQuantityPerTeam;
    private final Boolean isUsedDailyStock;
    private final Integer remainingQuantity;
    private final Boolean isOutOfStock;

    @Builder
    private MenuDto(String id, String name, int ordering, BigDecimal unitPrice,
        Boolean isUsedMenuQuantityPerTeam, Integer menuQuantityPerTeam, Boolean isUsedDailyStock,
        Integer remainingQuantity, Boolean isOutOfStock) {
      this.id = id;
      this.name = name;
      this.ordering = ordering;
      this.unitPrice = unitPrice;
      this.remainingQuantity = remainingQuantity;
      this.isUsedMenuQuantityPerTeam = isUsedMenuQuantityPerTeam;
      this.isUsedDailyStock = isUsedDailyStock;
      this.menuQuantityPerTeam = menuQuantityPerTeam;
      this.isOutOfStock = isOutOfStock;
    }

    public static MenuDto of(DisplayMenuDto displayMenuDto, StockEntity stock) {
      return MenuDto.builder()
          .id(displayMenuDto.getMenuId())
          .name(displayMenuDto.getMenuName())
          .ordering(displayMenuDto.getOrdering())
          .unitPrice(displayMenuDto.getUnitPrice().value())
          .isUsedMenuQuantityPerTeam(displayMenuDto.getIsUsedMenuQuantityPerTeam())
          .menuQuantityPerTeam(displayMenuDto.getMenuQuantityPerTeam())
          .isUsedDailyStock(stock != null && stock.isUsedDailyStock())
          .remainingQuantity(stock != null
              ? stock.getRemainingQuantity()
              : 0
          )
          .isOutOfStock(stock != null
              ? stock.isOutOfStock()
              : null
          )
          .build();
    }
  }

}
