package co.wadcorp.waiting.api;

import co.wadcorp.waiting.api.controller.settings.MemoSettingsController;
import co.wadcorp.waiting.api.controller.settings.OrderCategorySettingsController;
import co.wadcorp.waiting.api.controller.settings.OrderMenuMappingController;
import co.wadcorp.waiting.api.controller.settings.OrderMenuSettingsController;
import co.wadcorp.waiting.api.controller.settings.OrderSettingsController;
import co.wadcorp.waiting.api.controller.waiting.management.ManagementStockController;
import co.wadcorp.waiting.api.controller.waiting.management.ManagementWaitingMemoController;
import co.wadcorp.waiting.api.controller.waiting.register.RegisterWaitingController;
import co.wadcorp.waiting.api.internal.controller.meta.RemoteMetaController;
import co.wadcorp.waiting.api.internal.controller.person.RemotePersonOptionController;
import co.wadcorp.waiting.api.internal.controller.shop.InternalShopInfoRegisterController;
import co.wadcorp.waiting.api.internal.controller.shop.RemoteShopController;
import co.wadcorp.waiting.api.internal.controller.table.RemoteTableController;
import co.wadcorp.waiting.api.internal.controller.table.RemoteTableCurrentStatusController;
import co.wadcorp.waiting.api.internal.controller.waiting.RemoteWaitingController;
import co.wadcorp.waiting.api.internal.service.meta.RemoteMetaApiService;
import co.wadcorp.waiting.api.internal.service.person.RemotePersonOptionApiService;
import co.wadcorp.waiting.api.internal.service.shop.InternalShopInfoRegisterApiService;
import co.wadcorp.waiting.api.internal.service.shop.RemoteShopApiService;
import co.wadcorp.waiting.api.internal.service.shop.RemoteShopBulkApiService;
import co.wadcorp.waiting.api.internal.service.table.RemoteTableApiService;
import co.wadcorp.waiting.api.internal.service.table.RemoteTableCurrentStatusApiService;
import co.wadcorp.waiting.api.internal.service.waiting.RemoteWaitingApiService;
import co.wadcorp.waiting.api.internal.service.waiting.RemoteWaitingCancelApiService;
import co.wadcorp.waiting.api.internal.service.waiting.RemoteWaitingPutOffApiService;
import co.wadcorp.waiting.api.internal.service.waiting.RemoteWaitingRegisterApiService;
import co.wadcorp.waiting.api.resolver.channel.ChannelShopIdConverter;
import co.wadcorp.waiting.api.service.settings.MemoSettingsApiService;
import co.wadcorp.waiting.api.service.settings.OrderCategorySettingsApiService;
import co.wadcorp.waiting.api.service.settings.OrderMenuMappingApiService;
import co.wadcorp.waiting.api.service.settings.OrderMenuSettingsApiService;
import co.wadcorp.waiting.api.service.settings.OrderSettingsApiService;
import co.wadcorp.waiting.api.service.waiting.WaitingRegisterApiService;
import co.wadcorp.waiting.api.service.waiting.management.ManagementStockApiService;
import co.wadcorp.waiting.api.service.waiting.management.ManagementWaitingMemoApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@ActiveProfiles("test")
@WebMvcTest(controllers = {
    MemoSettingsController.class
})
public abstract class ControllerTest {


  @MockBean
  private MemoSettingsApiService memoSettingsApiService;

}
