package co.wadcorp.waiting.data.domain.order;

public enum OrderType {

  SHOP("매장"),
  TAKE_OUT("포장");

  private final String text;

  OrderType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

}
