package mops;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Formular Methods Test")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class FragebogenTest {

  private transient Fragebogen fragebogen;

  @BeforeEach
  public void setUp() {
    LocalDateTime startdatum = LocalDateTime.of(2020, 3, 3, 6, 30);
    LocalDateTime enddatum = LocalDateTime.of(2020, 3, 20, 6, 30);
    fragebogen = new Fragebogen(1L, "Programing", "Sam",
        null, startdatum, enddatum, Einheit.VORLESUNG);
  }

  @Test
  @DisplayName("Datum im Zeitintervall")
  public void getStatusVerfuegbar1() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 5, 6, 30);

    Status status = fragebogen.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }

  @Test
  @DisplayName("heutiges Datum ist Startdatum")
  public void getStatusVerfuegbar2() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 3, 6, 30);

    Status status = fragebogen.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }

  @Test
  @DisplayName("heutiges Datum ist enddatum")

  public void getStatusVerfuegbar3() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 20, 6, 30);

    Status status = fragebogen.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }

  @Test
  @DisplayName("Datum nicht im Zeitintervall")
  public void getStatusNichtVerfuegbar() {
    LocalDateTime heute = LocalDateTime.of(2020, 3, 22, 6, 30);

    Status status = fragebogen.getStatus(heute);

    Assertions.assertEquals(status, Status.NICHTVERFUEGBAR);
  }
}
