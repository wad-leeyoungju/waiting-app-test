package co.wadcorp.waiting.handler.event;

import co.wadcorp.waiting.data.domain.waiting.WaitingHistoryService;
import co.wadcorp.waiting.data.service.shop.ShopService;
import co.wadcorp.waiting.handler.support.WaitingWebProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WaitingReadyToEnterEventHandler {

  private final WaitingHistoryService waitingHistoryService;
  private final ShopService shopService;
//  private final CustomerService customerService;
//  private final MessageTemplateService messageTemplateService;
//  private final MessageSendHistoryService messageSendHistoryService;
//
//  private final WaitingCountQueryRepository waitingCountQueryRepository;
//
//  private final MessageSendClient messageSendClient;
  private final WaitingWebProperties webProperties;


}
