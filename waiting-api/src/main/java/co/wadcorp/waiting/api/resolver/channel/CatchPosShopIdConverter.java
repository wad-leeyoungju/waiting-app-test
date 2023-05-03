package co.wadcorp.waiting.api.resolver.channel;

import co.wadcorp.waiting.shared.enums.ServiceChannelId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatchPosShopIdConverter implements ChannelShopIdConverter {

  @Override
  public boolean isSupport(ServiceChannelId serviceChannelId) {
    return ServiceChannelId.CATCH_WAITING.equals(serviceChannelId);
  }

  @Override
  public ChannelShopIdMapping getShopIds(List<String> channelShopId) {
    ChannelShopIdMapping channelShopIdMapping = new ChannelShopIdMapping();

    channelShopId.forEach(item -> channelShopIdMapping.put(item, item));

    return channelShopIdMapping;
  }
}
