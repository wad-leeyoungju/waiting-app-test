package co.wadcorp.waiting.data.domain.memo;

import java.util.List;

public interface MemoKeywordRepository {

  <S extends MemoKeywordEntity> List<S> saveAll(Iterable<S> entities);
  List<MemoKeywordEntity> findAllByShopId(String shopId);
}
