package co.wadcorp.waiting.infra.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PosShopsResponse {

    private List<ShopInfo> shopList;

    public PosShopsResponse() {
    }

}
