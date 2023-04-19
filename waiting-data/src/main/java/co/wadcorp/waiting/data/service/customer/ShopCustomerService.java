package co.wadcorp.waiting.data.service.customer;

import co.wadcorp.waiting.data.domain.customer.ShopCustomerEntity;
import co.wadcorp.waiting.data.domain.customer.ShopCustomerId;
import co.wadcorp.waiting.data.domain.customer.ShopCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopCustomerService {

    private final ShopCustomerRepository shopCustomerRepository;

    public ShopCustomerEntity saveShopCustomer(ShopCustomerEntity shopCustomerEntity) {
        return shopCustomerRepository.save(shopCustomerEntity);
    }

    public ShopCustomerEntity getShopCustomerById(String shopId, Long customerSeq) {
        ShopCustomerId shopCustomerId = createShopCustomerId(shopId, customerSeq);

        return shopCustomerRepository.findById(shopCustomerId)
                .orElseGet(() -> ShopCustomerEntity.ofDefault(shopId, customerSeq));
    }

    public ShopCustomerEntity getShopCustomerById(String shopId, Long customerSeq, String customerName) {
        ShopCustomerId shopCustomerId = createShopCustomerId(shopId, customerSeq);

        return shopCustomerRepository.findById(shopCustomerId)
                .orElseGet(() -> ShopCustomerEntity.ofDefault(shopId, customerSeq, customerName));
    }

    private ShopCustomerId createShopCustomerId(String shopId, Long customerSeq) {
        return ShopCustomerId.builder()
                .shopId(shopId)
                .customerSeq(customerSeq)
                .build();
    }

    public void sitting(String shopId, Long customerSeq) {
        ShopCustomerEntity customer = getShopCustomerById(shopId, customerSeq);
        customer.increaseSittingCount();
    }

    public void cancel(String shopId, Long customerSeq) {
        ShopCustomerEntity customer = getShopCustomerById(shopId, customerSeq);
        customer.increaseCancelCount();
    }

    public void noShow(String shopId, Long customerSeq) {
        ShopCustomerEntity customer = getShopCustomerById(shopId, customerSeq);
        customer.increaseNoShowCount();
        customer.increaseCancelCount();
    }
}
