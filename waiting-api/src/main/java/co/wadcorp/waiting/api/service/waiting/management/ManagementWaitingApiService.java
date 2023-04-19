package co.wadcorp.waiting.api.service.waiting.management;

import co.wadcorp.waiting.data.domain.waiting.WaitingHistoryService;
import co.wadcorp.waiting.data.service.customer.ShopCustomerService;
import co.wadcorp.waiting.data.service.order.OrderService;
import co.wadcorp.waiting.data.service.shop.ShopService;
import co.wadcorp.waiting.data.service.waiting.WaitingManagementService;
import co.wadcorp.waiting.handler.support.WaitingWebProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementWaitingApiService {

  private final WaitingManagementService service;
  private final ShopCustomerService shopCustomerService;
  private final ApplicationEventPublisher eventPublisher;

  private final OrderService orderService;

}
