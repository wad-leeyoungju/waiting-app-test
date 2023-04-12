package co.wadcorp.waiting.data.enums;

import co.wadcorp.waiting.data.exception.AppException;
import java.util.Arrays;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

@Getter
public enum OperationDay {

  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY;

  OperationDay() {
  }

  public static String find(String day) {
    return Arrays.stream(values())
        .filter(e -> StringUtils.equals(e.toString(), day))
        .findAny()
        .orElseThrow(() -> new AppException(
            HttpStatus.BAD_REQUEST, String.format("Illegal args for day : [%s]", day)))
        .toString();
  }

  public boolean isSameDay(String day) {
    return this.name().equals(day);
  }

}
