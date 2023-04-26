package co.wadcorp.waiting.api.service.settings;

import co.wadcorp.waiting.api.model.settings.request.MemoKeywordSaveRequest;
import co.wadcorp.waiting.api.model.settings.response.MemoKeywordResponse;
import co.wadcorp.waiting.data.service.settings.MemoSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoSettingsApiService {

  private final MemoSettingsService memoSettingsService;

  public MemoKeywordResponse getMemoKeywords(String shopId) {
    return MemoKeywordResponse.toDto(
        memoSettingsService.findAllByShopId(shopId));
  }

  public MemoKeywordResponse saveMemoKeywords(String shopId, MemoKeywordSaveRequest request) {
    return MemoKeywordResponse.toDto(
        memoSettingsService.updateMemoKeywords(shopId, request.toEntities(shopId)));
  }
}
