package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage"})
public class DateTimeServiceTest {
  private final DateTimeService service = new DateTimeService();

  @Test
  public void neuesLocalDateObjekt() {
    String datum = "2020-03-23";
    String uhrzeit = "11:34";
    LocalDateTime gewuenscht = LocalDateTime.of(2020, 3, 23, 11, 34);

    LocalDateTime bekommen = service.getLocalDateTimeFromString(datum, uhrzeit);

    assertEquals(gewuenscht, bekommen);
  }
}
