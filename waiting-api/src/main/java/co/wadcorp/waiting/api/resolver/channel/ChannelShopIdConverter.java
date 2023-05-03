package co.wadcorp.waiting.api.resolver.channel;

import co.wadcorp.waiting.shared.enums.ServiceChannelId;
import java.util.List;

public interface ChannelShopIdConverter {

  boolean isSupport(ServiceChannelId serviceChannelId);

  ChannelShopIdMapping getShopIds(List<String> channelShopId);

}
