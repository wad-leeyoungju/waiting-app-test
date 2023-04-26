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

  /**
   * 메모 키워드 단건 수정
   */
  @PostMapping("/api/v1/shops/{shopId}/settings/memo/keywords/{keywordId}/update")
  public ApiResponse<?> updateMemoKeyword(@PathVariable String shopId,
      @PathVariable String keywordId) {
    return ApiResponse.ok();
  }

  /**
   * 메모 키워드 단건 삭제
   */
  @PostMapping("/api/v1/shops/{shopId}/settings/memo/keywords/{keywordId}/delete")
  public ApiResponse<?> deleteMemoKeyword(@PathVariable String shopId,
      @PathVariable String keywordId) {
//    memoSettingsApiService.delete(keywordId);
    return ApiResponse.ok();
  }

  /**
   * 메모 키워드 순서 변경
   */
  @PostMapping("/api/v1/shops/{shopId}/settings/memo/keywords/ordering")
  public ApiResponse<?> orderingMemoKeywords(
      @PathVariable String shopId) {
//    return ApiResponse.ok(
//        memoSettingsApiService.updateMemoKeywordsOrdering(shopId, request.toServiceRequest())
//    );
    return ApiResponse.ok();
  }

}
