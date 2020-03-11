package mops;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeService {

  /**
   * convert String input to LocalDateTime Object.
   *
   * @param date String
   * @param time String
   * @return LocalDateTime Object
   */
  public LocalDateTime getLocalDateTimeFromString(String date, String time) {
    LocalDate startDay = LocalDate.parse(date);
    LocalTime startTime = LocalTime.parse(time);
    return LocalDateTime.of(startDay, startTime);
  }

  /**
   * format a LocalDateTime Object to a German formatted String.
   *
   * @param dateTime LocalDateTime
   * @return String
   */
  public String getGermanFormat(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        .withLocale(Locale.GERMAN));
  }
}