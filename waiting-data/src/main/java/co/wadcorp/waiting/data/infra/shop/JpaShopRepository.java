package co.wadcorp.waiting.data.infra.shop;

import co.wadcorp.waiting.data.domain.shop.ShopEntity;
import co.wadcorp.waiting.data.domain.shop.ShopRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaShopRepository extends ShopRepository, JpaRepository<ShopEntity, Long> {

    Optional<ShopEntity> findByShopId(String shopId);


    List<ShopEntity> findAllByShopIdIn(List<String> shopIds);

}
