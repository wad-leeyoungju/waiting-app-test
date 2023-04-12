package co.wadcorp.waiting.infra.pos;

import co.wadcorp.waiting.infra.pos.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange(contentType = MediaType.APPLICATION_JSON_VALUE)
interface CatchtablePosShopHttpClient {

    @GetExchange(url = "/catchpos/api/v1/shops")
    PosApiResponse<PosShopsResponse> getShops(@RequestHeader Map<String, Object> header);

    @GetExchange(url = "/catchpos/api/v1/shops/{shopId}")
    PosApiResponse<PosShopResponse> getShop(@PathVariable("shopId") String shopId,
                                            @RequestHeader Map<String, Object> header);

    @GetExchange(url = "/internal/catchpos/api/v1/shops/{shopId}")
    PosApiResponse<PosShopResponse> getShopForInternal(@PathVariable("shopId") String shopId);

    @PostExchange(url = "/internal/catchpos/api/v1/shops/search")
    PosApiResponse<PosSearchShopsResponse> searchShops(@RequestBody PosShopSearchRequest posShopSearchRequest);

}
