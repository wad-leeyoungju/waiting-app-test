package co.wadcorp.waiting.data.service.stock.dto;

import lombok.Getter;

@Getter
public class StockMenuDto {

  private final String id;
  private final String name;
  private final int additionalQuantity;
  private final boolean isOutOfStock;

  public StockMenuDto(String id, String name, int additionalQuantity, boolean isOutOfStock) {
    this.id = id;
    this.name = name;
    this.additionalQuantity = additionalQuantity;
    this.isOutOfStock = isOutOfStock;
  }
}
