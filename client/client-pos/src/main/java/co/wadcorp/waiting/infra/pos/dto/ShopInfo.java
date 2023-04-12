package co.wadcorp.waiting.infra.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopInfo {
    private String shopId;
    private String shopName;
    private String posMode;
    private String posTheme;
    private Boolean isRemoveProcessing;
    private Boolean isCatchPos;
    private Boolean isCashManagement;
    private Boolean isPointUse;
}
