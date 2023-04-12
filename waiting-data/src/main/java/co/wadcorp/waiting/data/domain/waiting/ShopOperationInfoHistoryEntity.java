package co.wadcorp.waiting.data.domain.waiting;

import co.wadcorp.waiting.data.support.BaseEntity;
import co.wadcorp.waiting.data.support.BaseHistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cw_shop_operation_info_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopOperationInfoHistoryEntity extends BaseHistoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @Column(name = "shop_operation_info_seq")
  private Long shopOperationInfoSeq;

  @Column(name = "shop_id")
  private String shopId;

  @Column(name = "operation_date")
  private LocalDate operationDate;

  @Column(name = "registrable_status")
  @Enumerated(EnumType.STRING)
  private RegistrableStatus registrableStatus;

  @Column(name = "operation_start_date_time")
  private ZonedDateTime operationStartDateTime;

  @Column(name = "operation_end_date_time")
  private ZonedDateTime operationEndDateTime;

  @Embedded
  private ManualPauseInfo manualPauseInfo;

  @Embedded
  private AutoPauseInfo autoPauseInfo;

  @Column(name = "closed_reason")
  @Enumerated(EnumType.STRING)
  private ClosedReason closedReason;

  public static ShopOperationInfoHistoryEntity of(ShopOperationInfoEntity savedEntity) {
    ShopOperationInfoHistoryEntity entity = new ShopOperationInfoHistoryEntity();

    entity.shopOperationInfoSeq = savedEntity.getSeq();
    entity.shopId = savedEntity.getShopId();
    entity.operationDate = savedEntity.getOperationDate();
    entity.registrableStatus = savedEntity.getRegistrableStatus();
    entity.operationStartDateTime = savedEntity.getOperationStartDateTime();
    entity.operationEndDateTime = savedEntity.getOperationEndDateTime();
    entity.manualPauseInfo = savedEntity.getManualPauseInfo();
    entity.autoPauseInfo = savedEntity.getAutoPauseInfo();
    entity.closedReason = savedEntity.getClosedReason();

    return entity;
  }

}
