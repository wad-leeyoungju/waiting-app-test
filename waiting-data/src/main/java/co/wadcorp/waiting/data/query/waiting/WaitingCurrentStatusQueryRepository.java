package co.wadcorp.waiting.data.query.waiting;

import static co.wadcorp.libs.stream.StreamUtils.groupingBySet;
import static co.wadcorp.waiting.data.domain.settings.QHomeSettingsEntity.*;
import static co.wadcorp.waiting.data.domain.waiting.QWaitingEntity.*;
import static co.wadcorp.waiting.data.query.waiting.dto.WaitingCurrentStatusDto.*;

import co.wadcorp.waiting.data.domain.settings.DefaultHomeSettingDataFactory;
import co.wadcorp.waiting.data.domain.settings.HomeSettingsEntity;
import co.wadcorp.waiting.data.domain.settings.SeatOptions;
import co.wadcorp.waiting.data.domain.waiting.QWaitingEntity;
import co.wadcorp.waiting.data.domain.waiting.WaitingEntity;
import co.wadcorp.waiting.data.domain.waiting.WaitingStatus;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingCurrentStatusCountDto;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingCurrentStatusDto;
import co.wadcorp.waiting.data.query.waiting.dto.WaitingCurrentStatusDto.SeatOption;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class WaitingCurrentStatusQueryRepository {

  private final JPAQueryFactory queryFactory;

  // TODO: 2023/03/09 쿼리는 WaitingQueryRepository로 이동, 가공 로직은 서비스로 분리 및 테스트 필요
  public WaitingCurrentStatusDto selectDefaultCurrentStatus(String shopId,
      LocalDate operationDate) {
    HomeSettingsEntity homeSettings = selectHomeSettings(shopId);

    List<WaitingCurrentStatusCountDto> waitingCurrentStatusCountDtos = queryFactory
        .select(
            Projections.fields(
                WaitingCurrentStatusCountDto.class,
                waitingEntity.seq,
                waitingEntity.totalPersonCount,
                waitingEntity.seatOptionName
            )
        )
        .from(waitingEntity)
        .where(waitingEntity.shopId.eq(shopId),
            waitingEntity.operationDate.eq(operationDate),
            waitingEntity.waitingStatus.eq(WaitingStatus.WAITING)
        )
        .fetch();

    int peopleCount = waitingCurrentStatusCountDtos.stream()
        .mapToInt(WaitingCurrentStatusCountDto::getTotalPersonCount)
        .sum();

    Map<String, Set<WaitingCurrentStatusCountDto>> tableModeSeatsGroup = groupingBySet(
        waitingCurrentStatusCountDtos, WaitingCurrentStatusCountDto::getSeatOptionName);

    List<SeatOptions> tableModeSettings = homeSettings.getTableModeSettings();
    List<CurrentStatus> currentStatuses = tableModeSettings.stream()
        .map(item -> {
          Set<WaitingCurrentStatusCountDto> counts = tableModeSeatsGroup.getOrDefault(
              item.getName(),
              Set.of()
          );

          int tablePeopleCount = counts.stream()
              .mapToInt(WaitingCurrentStatusCountDto::getTotalPersonCount)
              .sum();
          Integer expectedWaitingTime = getExpectedWaitingTime(item, counts.size());
          SeatOption seatOption = new SeatOption(
              item.getMinSeatCount(), item.getMaxSeatCount(),
              item.getIsTakeOut()
          );

          return new CurrentStatus(item.getId(), item.getName(), seatOption, counts.size(),
              tablePeopleCount,
              expectedWaitingTime, item.getIsUsedExpectedWaitingPeriod());
        })
        .toList();

    return new WaitingCurrentStatusDto(
        waitingCurrentStatusCountDtos.size(),
        peopleCount,
        currentStatuses
    );
  }

  // TODO: 2023/03/09 쿼리는 WaitingQueryRepository로 이동, 가공 로직은 서비스로 분리 및 테스트 필요
  public WaitingCurrentStatusDto selectTableCurrentStatus(String shopId, LocalDate operationDate) {
    HomeSettingsEntity homeSettings = selectHomeSettings(shopId);

    List<WaitingCurrentStatusCountDto> waitingCurrentStatusCountDtos = queryFactory.select(
            Projections.fields(
                WaitingCurrentStatusCountDto.class,
                waitingEntity.seq,
                waitingEntity.totalPersonCount,
                waitingEntity.seatOptionName
            )).from(waitingEntity)
        .where(waitingEntity.shopId.eq(shopId)
            .and(waitingEntity.operationDate.eq(operationDate))
            .and(waitingEntity.waitingStatus.eq(WaitingStatus.WAITING)))
        .fetch();

    // 총 인원 계산
    int peopleCount = waitingCurrentStatusCountDtos.stream()
        .mapToInt(WaitingCurrentStatusCountDto::getTotalPersonCount)
        .sum();

    // 테이블 별 그룹화
    Map<String, Set<WaitingCurrentStatusCountDto>> tableModeSeatsGroup = groupingBySet(
        waitingCurrentStatusCountDtos, WaitingCurrentStatusCountDto::getSeatOptionName);

    // 테이블 별 팀 수, 인원 수, 예상 대기시간 계산
    List<SeatOptions> tableModeSettings = homeSettings.getTableModeSettings();
    List<CurrentStatus> currentStatuses = tableModeSettings.stream()
        .map(item -> {
          Set<WaitingCurrentStatusCountDto> counts = tableModeSeatsGroup.getOrDefault(
              item.getName(),
              Set.of()
          );

          int tablePeopleCount = counts.stream()
              .mapToInt(WaitingCurrentStatusCountDto::getTotalPersonCount)
              .sum();
          Integer expectedWaitingTime = getExpectedWaitingTime(item, counts.size());
          SeatOption seatOption = new SeatOption(
              item.getMinSeatCount(), item.getMaxSeatCount(),
              item.getIsTakeOut()
          );

          return new CurrentStatus(item.getId(), item.getName(), seatOption, counts.size(),
              tablePeopleCount,
              expectedWaitingTime, item.getIsUsedExpectedWaitingPeriod());
        })
        .toList();

    return new WaitingCurrentStatusDto(
        waitingCurrentStatusCountDtos.size(),
        peopleCount,
        currentStatuses
    );
  }

  private Integer getExpectedWaitingTime(SeatOptions defaultModeSettings, int teamCount) {
    if (defaultModeSettings.isNotUseExpectedWaitingPeriod()) {
      return null;
    }

    return defaultModeSettings.calculateExpectedWaitingPeriod(teamCount + 1);
  }

  private HomeSettingsEntity selectHomeSettings(String shopId) {
    return queryFactory.selectFrom(homeSettingsEntity)
        .where(homeSettingsEntity.shopId.eq(shopId).and(homeSettingsEntity.isPublished.isTrue()))
        .stream()
        .findFirst()
        .orElseGet(() -> new HomeSettingsEntity(shopId, DefaultHomeSettingDataFactory.create()));
  }

}
