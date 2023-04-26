package co.wadcorp.waiting.data.infra.memo;

import co.wadcorp.waiting.data.domain.memo.MemoKeywordEntity;
import co.wadcorp.waiting.data.domain.memo.MemoKeywordRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemoKeywordRepository extends JpaRepository<MemoKeywordEntity, Long>,
    MemoKeywordRepository {

  @Query("""
            select m 
             from MemoKeywordEntity m 
            where m.shopId = :shopId 
              and m.isDeleted = false 
            order by m.ordering 
            limit 20
            """)
  List<MemoKeywordEntity> findAllByShopId(String shopId);
}
