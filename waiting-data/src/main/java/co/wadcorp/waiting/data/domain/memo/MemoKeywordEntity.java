package co.wadcorp.waiting.data.domain.memo;

import co.wadcorp.waiting.data.support.BaseEntity;
import co.wadcorp.waiting.data.support.BooleanYnConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "seq", callSuper = false)
@Table(name = "cw_memo_keyword")
@Entity
public class MemoKeywordEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;

  @Column(name = "shop_id")
  private String shopId;

  @Column(name = "keyword")
  private String keyword;

  @Column(name = "ordering")
  private int ordering;

  @Column(name = "deleted_yn", columnDefinition = "char")
  @Convert(converter = BooleanYnConverter.class)
  private Boolean isDeleted;

  @Builder
  public MemoKeywordEntity(String shopId, String keyword, int ordering) {
    this.shopId = shopId;
    this.keyword = keyword;
    this.ordering = ordering;
    this.isDeleted = false;
  }

  public void setOrdering(int ordering) {
    this.ordering = ordering;
  }

  public void softDelete() {
    this.isDeleted = true;
  }
}
