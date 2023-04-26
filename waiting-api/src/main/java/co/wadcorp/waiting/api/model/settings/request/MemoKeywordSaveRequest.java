package co.wadcorp.waiting.api.model.settings.request;

import co.wadcorp.waiting.data.domain.memo.MemoKeywordEntity;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoKeywordSaveRequest {
  private List<String> keywords;

  @Builder
  public MemoKeywordSaveRequest(List<String> keywords) {
    this.keywords = keywords;
  }

  public List<MemoKeywordEntity> toEntities(String shopId) {
    return IntStream.range(0, keywords.size())
        .mapToObj(i -> MemoKeywordEntity.builder()
            .keyword(keywords.get(i))
            .ordering(i+1)
            .shopId(shopId)
            .build())
        .toList();
  }
}
