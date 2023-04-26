package co.wadcorp.waiting.api;

import co.wadcorp.waiting.api.controller.settings.MemoSettingsController;
import co.wadcorp.waiting.api.service.settings.MemoSettingsApiService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@WithMockUser
@ActiveProfiles("test")
@WebMvcTest(controllers = {
    MemoSettingsController.class
})
public abstract class ControllerTest {


  @MockBean
  private MemoSettingsApiService memoSettingsApiService;

}
