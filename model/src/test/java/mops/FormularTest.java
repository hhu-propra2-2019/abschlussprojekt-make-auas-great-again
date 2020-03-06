package mops;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Formular Methods Test")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class FormularTest {

  private transient Formular formular;

  @BeforeEach
  public void setUp() {
    LocalDateTime startdatum = LocalDateTime.of(2020, 3, 3, 6, 30);
    LocalDateTime enddatum = LocalDateTime.of(2020, 3, 20, 6, 30);
    formular = new Formular("Programing", "Sam", null, startdatum, enddatum);
  }

  @Test
  @DisplayName("Datum im Zeitintervall")
  public void getStatusVerfuegbar1() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 5, 6, 30);

    Status status = formular.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }

  @Test
  @DisplayName("heutiges Datum ist Startdatum")
  public void getStatusVerfuegbar2() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 3, 6, 30);

    Status status = formular.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }

  @Test
  @DisplayName("heutiges Datum ist enddatum")

  public void getStatusVerfuegbar3() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 20, 6, 30);

    Status status = formular.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }

  @Test
  @DisplayName("Datum nicht im Zeitintervall")
  public void getStatusNichtVerfuegbar() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 22, 6, 30);

    Status status = formular.getStatus(heute);

    Assertions.assertEquals(status, Status.NICHTVERFUEGBAR);
  }
}
