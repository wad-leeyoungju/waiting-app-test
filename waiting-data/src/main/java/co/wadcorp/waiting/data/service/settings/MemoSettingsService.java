package co.wadcorp.waiting.data.service.settings;

import co.wadcorp.waiting.data.domain.memo.MemoKeywordEntity;
import co.wadcorp.waiting.data.domain.memo.MemoKeywordRepository;
import co.wadcorp.waiting.data.domain.memo.MemoKeywordsManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoSettingsService {

  private final MemoKeywordRepository memoKeywordRepository;

  public List<MemoKeywordEntity> findAllByShopId(String shopId) {
    return memoKeywordRepository.findAllByShopId(shopId);
  }

  public List<MemoKeywordEntity> updateMemoKeywords(String shopId,
      List<MemoKeywordEntity> updateRequestEntities) {
    List<MemoKeywordEntity> originEntities = memoKeywordRepository.findAllByShopId(shopId);
    MemoKeywordsManager memoKeywordsManager = new MemoKeywordsManager(originEntities, updateRequestEntities, memoKeywordRepository);
    memoKeywordsManager.updateKeywords();

    return null;
  }
}
