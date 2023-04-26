package co.wadcorp.waiting.api.controller.settings;

import co.wadcorp.waiting.api.model.settings.request.MemoKeywordSaveRequest;
import co.wadcorp.waiting.api.model.settings.response.MemoKeywordResponse;
import co.wadcorp.waiting.api.service.settings.MemoSettingsApiService;
import co.wadcorp.waiting.data.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoSettingsController {

  private final MemoSettingsApiService memoSettingsApiService;

  @GetMapping("/api/v1/shops/{shopId}/settings/memo-keywords")
  public ApiResponse<MemoKeywordResponse> getMemoSettings(@PathVariable String shopId) {
    return ApiResponse.ok(memoSettingsApiService.getMemoKeywords(shopId));
  }

  @PostMapping("/api/v1/shops/{shopId}/settings/memo-keywords")
  public ApiResponse<MemoKeywordResponse> saveMemoSettings(@PathVariable String shopId,
      @Valid @RequestBody MemoKeywordSaveRequest request) {
    return ApiResponse.ok(memoSettingsApiService.saveMemoKeywords(shopId, request));
  }

}
