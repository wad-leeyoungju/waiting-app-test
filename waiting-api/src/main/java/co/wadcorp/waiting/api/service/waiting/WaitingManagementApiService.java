package co.wadcorp.waiting.api.service.waiting;

import co.wadcorp.waiting.api.model.waiting.vo.CancelReason;
import co.wadcorp.waiting.data.domain.waiting.WaitingHistoryEntity;
import co.wadcorp.waiting.data.event.*;
import co.wadcorp.waiting.data.service.customer.ShopCustomerService;
import co.wadcorp.waiting.data.service.waiting.WaitingManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingManagementApiService {

    private final WaitingManagementService service;
    private final ShopCustomerService shopCustomerService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void call(String shopId, String waitingId, LocalDate operationDate,
                     LocalDateTime currentDateTime) {
        WaitingHistoryEntity waitingHistory = service.call(shopId, waitingId, operationDate);

        eventPublisher.publishEvent(
                new CalledEvent(shopId, waitingHistory.getSeq(), currentDateTime)
        );
    }

    @Transactional
    public void sitting(String shopId, String waitingId, LocalDate operationDate) {
        WaitingHistoryEntity waitingHistory = service.sitting(shopId, waitingId, operationDate);
        shopCustomerService.sitting(shopId, waitingHistory.getCustomerSeq());

        eventPublisher.publishEvent(new SeatedEvent(shopId, waitingHistory.getSeq(), operationDate));
    }

    @Transactional
    public void cancel(String shopId, String waitingId, CancelReason cancelReason, LocalDate operationDate) {
        if(cancelReason == CancelReason.CUSTOMER_REASON) {
            WaitingHistoryEntity waitingHistory =  service.cancelByCustomer(shopId, waitingId);
            shopCustomerService.cancel(shopId, waitingHistory.getCustomerSeq());
            eventPublisher.publishEvent(new CanceledByCustomerEvent(shopId, waitingHistory.getSeq(),
                    operationDate));
        }

        if(cancelReason == CancelReason.SHOP_REASON) {
            WaitingHistoryEntity waitingHistory = service.cancelByShop(shopId, waitingId);
            shopCustomerService.cancel(shopId, waitingHistory.getCustomerSeq());
            eventPublisher.publishEvent(new CanceledByShopEvent(shopId, waitingHistory.getSeq(),
                    operationDate));
        }

        if(cancelReason == CancelReason.NO_SHOW) {
            WaitingHistoryEntity waitingHistory = service.noShow(shopId, waitingId);
            shopCustomerService.noShow(shopId, waitingHistory.getCustomerSeq());
            eventPublisher.publishEvent(
                    new NoShowedEvent(shopId, waitingHistory.getSeq(), operationDate));
        }
    }
}
