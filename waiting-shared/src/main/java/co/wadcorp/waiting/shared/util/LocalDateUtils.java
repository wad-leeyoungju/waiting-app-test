package co.wadcorp.waiting.shared.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateUtils {

  private static final DateTimeFormatter DATE_TIME_FORMATTER
      = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static LocalDate parseToLocalDate(String localDateString) {
    return LocalDate.parse(localDateString, DATE_TIME_FORMATTER);
  }

  public static String convertToString(LocalDate localDate) {
    return DATE_TIME_FORMATTER.format(localDate);
  }

}
