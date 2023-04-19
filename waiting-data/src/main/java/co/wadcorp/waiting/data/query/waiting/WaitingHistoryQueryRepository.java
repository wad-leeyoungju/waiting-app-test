package co.wadcorp.waiting.data.query.waiting;

import static co.wadcorp.waiting.data.domain.customer.QCustomerEntity.*;
import static co.wadcorp.waiting.data.domain.customer.QShopCustomerEntity.*;
import static co.wadcorp.waiting.data.domain.waiting.QWaitingEntity.*;
import static co.wadcorp.waiting.data.domain.waiting.QWaitingHistoryEntity.waitingHistoryEntity;

import co.wadcorp.waiting.data.domain.customer.QCustomerEntity;
import co.wadcorp.waiting.data.domain.customer.QShopCustomerEntity;
import co.wadcorp.waiting.data.domain.waiting.QWaitingEntity;
import co.wadcorp.waiting.data.domain.waiting.WaitingDetailStatus;
import co.wadcorp.waiting.data.query.waiting.dto.QWaitingCalledHistoryDto;
import co.wadcorp.waiting.data.query.waiting.dto.QWaitingHistoriesDto_WaitingDto;
import co.wadcorp.waiting.data.query.waiting.dto.QWaitingHistoriesDto_WaitingHistoryDto;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingCalledHistoryDto;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingHistoriesDto;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingHistoriesDto.WaitingDto;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingHistoriesDto.WaitingHistoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class WaitingHistoryQueryRepository {

  private final JPAQueryFactory queryFactory;

  public WaitingHistoriesDto getWaitingHistories(String shopId, String waitingId) {
    WaitingDto waitingHistoryDto = queryFactory.select(
            select(waitingEntity, customerEntity, shopCustomerEntity)
        )
        .from(waitingEntity)
        .leftJoin(customerEntity).on(waitingEntity.customerSeq.eq(customerEntity.seq))
        .leftJoin(shopCustomerEntity).on(
            customerEntity.seq.eq(shopCustomerEntity.shopCustomerId.customerSeq)
                .and(shopCustomerEntity.shopCustomerId.shopId.eq(shopId))
        )
        .where(waitingEntity.shopId.eq(shopId)
            .and(waitingEntity.waitingId.eq(waitingId)))
        .fetchOne();

    List<WaitingHistoryDto> waitingHistories = queryFactory.select(
            new QWaitingHistoriesDto_WaitingHistoryDto(
                waitingHistoryEntity.waitingStatus,
                waitingHistoryEntity.waitingDetailStatus,
                waitingHistoryEntity.regDateTime
            ))
        .from(waitingHistoryEntity)
        .where(waitingHistoryEntity.shopId.eq(shopId),
            waitingHistoryEntity.waitingId.eq(waitingId))
        .fetch();

    return new WaitingHistoriesDto(waitingHistoryDto, waitingHistories);
  }

  public List<WaitingCalledHistoryDto> getWaitingCalledHistory(List<String> waitingIds) {
    return queryFactory.select(
            new QWaitingCalledHistoryDto(
                waitingHistoryEntity.waitingId,
                waitingHistoryEntity.regDateTime
            )
        )
        .from(waitingHistoryEntity)
        .where(waitingHistoryEntity.waitingId.in(waitingIds)
            .and(waitingHistoryEntity.waitingDetailStatus.eq(WaitingDetailStatus.CALL)))
        .fetch();
  }

  private static QWaitingHistoriesDto_WaitingDto select(QWaitingEntity waitingEntity,
      QCustomerEntity customerEntity, QShopCustomerEntity shopCustomerEntity) {
    return new QWaitingHistoriesDto_WaitingDto(
        waitingEntity.waitingId,
        waitingEntity.shopId,
        waitingEntity.registerChannel,
        waitingEntity.waitingStatus,
        waitingEntity.waitingDetailStatus,
        waitingEntity.operationDate,
        waitingEntity.waitingNumbers.waitingNumber,
        waitingEntity.waitingNumbers.waitingOrder,
        customerEntity.encCustomerPhone,
        waitingEntity.customerName,
        shopCustomerEntity.visitCount,
        waitingEntity.totalPersonCount,
        waitingEntity.personOptionsData,
        waitingEntity.seatOptionName,
        waitingEntity.regDateTime
    );
  }

}
