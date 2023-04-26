package co.wadcorp.waiting.data.domain.memo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class MemoKeywordsManager {

  private List<MemoKeywordEntity> originEntities;
  private List<MemoKeywordEntity> updateEntities;
  private Set<String> originKeywordsSet;
  private Set<String> updateKeywordsSet;
  private Map<String, MemoKeywordEntity> originKeywordsMap = new HashMap<>();
  private MemoKeywordRepository repository;

  public MemoKeywordsManager(List<MemoKeywordEntity> originEntities, List<MemoKeywordEntity> updateEntities, MemoKeywordRepository repository) {
    this.originEntities = originEntities;
    this.updateEntities = updateEntities;
    this.originKeywordsSet = new HashSet<>(originEntities.stream()
        .map(MemoKeywordEntity::getKeyword)
        .toList());
    this.updateKeywordsSet = new HashSet<>(updateEntities.stream()
        .map(MemoKeywordEntity::getKeyword)
        .toList());
    for(MemoKeywordEntity originEntity : originEntities) {
      this.originKeywordsMap.put(originEntity.getKeyword(), originEntity);
    }
    this.repository = repository;
  }

  public boolean isInOriginKeywords(String keyword) {
    return originKeywordsSet.contains(keyword);
  }
  public boolean isInUpdateKeywords(String keyword) {
    return updateKeywordsSet.contains(keyword);
  }

  /**
   * 사용자가 저장 요청한 메모 키워드 중 새로 추가되는 키워드들만 조회
   */
  public List<MemoKeywordEntity> getBrandNewKeywords() {
    return updateEntities.stream()
        .filter(updateEntity -> !isInOriginKeywords(updateEntity.getKeyword()))
        .toList();
  }

  /**
   * 사용자가 저장 요청한 메모 키워드 중 기존에 존재하는 키워드들만 조회
   */
  public List<MemoKeywordEntity> getNotChangedKeywords() {
    return updateEntities.stream()
        .filter(updateEntity -> isInOriginKeywords(updateEntity.getKeyword()))
        .toList();
  }

  public List<MemoKeywordEntity> getDeleteTargetKeywords() {
    return originEntities.stream()
        .filter(originEntity -> !isInUpdateKeywords(originEntity.getKeyword()))
        .toList();
  }

  public void updateOriginKeywordOrdering() {
    List<MemoKeywordEntity> notChangedKeywords = getNotChangedKeywords();
    notChangedKeywords.stream().forEach(entity ->
        originKeywordsMap.get(entity.getKeyword())
            .setOrdering(entity.getOrdering()));
  }

  public void deleteNoMoreUseKeywords() {
    List<MemoKeywordEntity> deleteTargetKeywords = getDeleteTargetKeywords();
    deleteTargetKeywords.stream().forEach(entity -> entity.softDelete());
  }

  public void updateKeywords() {
    // 새로 등록되는 키워드는 신규 저장
    List<MemoKeywordEntity> brandNewEntities = getBrandNewKeywords();
    repository.saveAll(brandNewEntities);

    // 기존에 저장되어 있던 키워드는 ordering 값만 변경
    updateOriginKeywordOrdering();

    // 기존에 저장되어 있었는데 새로 등록되는 키워드 목록에 없는 경우 삭제
    deleteNoMoreUseKeywords();
  }
}
