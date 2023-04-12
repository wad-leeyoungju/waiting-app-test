package co.wadcorp.waiting.data.domain.shop;

import java.util.List;
import java.util.Optional;

public interface ShopRepository {

    Optional<ShopEntity> findByShopId(String shopId);

    List<ShopEntity> findAllByShopIdIn(List<String> shopIds);
}
