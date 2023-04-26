package co.wadcorp.waiting.api.model.settings.response;

import co.wadcorp.waiting.data.domain.memo.MemoKeywordEntity;
import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoKeywordResponse {

  private List<String> keywords;

  @Builder
  public MemoKeywordResponse(List<String> keywords) {
    this.keywords = keywords;
  }

  public static MemoKeywordResponse toDto(List<MemoKeywordEntity> entities) {
    return MemoKeywordResponse.builder()
        .keywords(entities.stream()
            .sorted(Comparator.comparing(MemoKeywordEntity::getOrdering))
            .map(MemoKeywordEntity::getKeyword)
            .toList())
        .build();
  }
}
