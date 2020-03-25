package mops;

import java.time.LocalDateTime;
import java.util.List;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;
import mops.rollen.Student;
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
    fragebogen = new Fragebogen("Programming");
    fragebogen.setStartdatum(startdatum);
    fragebogen.setEnddatum(enddatum);
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

  @Test
  public void getMultipleChoiceFragenTest() {
    MultipleChoiceFrage neueFrage = new MultipleChoiceFrage("Hi");
    fragebogen.addFrage(neueFrage);

    List<MultipleChoiceFrage> multipleChoiceFrageList = fragebogen.getMultipleChoiceFragen();

    Assertions.assertTrue(multipleChoiceFrageList.contains(neueFrage));
  }

  @Test
  public void getTextFragenTest() {
    TextFrage neueFrage = new TextFrage("Hi");
    fragebogen.addFrage(neueFrage);

    List<TextFrage> textFragen = fragebogen.getTextFragen();

    Assertions.assertTrue(textFragen.contains(neueFrage));
  }

  @Test
  public void loescheFragenTest() {
    TextFrage neueFrage = new TextFrage("Hi");
    fragebogen.addFrage(neueFrage);
    fragebogen.loescheFrage(neueFrage);

    List<TextFrage> textFragen = fragebogen.getTextFragen();

    Assertions.assertFalse(textFragen.contains(neueFrage));
  }

  @Test
  public void loescheFrageByIdTest() {
    TextFrage neueFrage = new TextFrage("Hi");
    fragebogen.addFrage(neueFrage);
    fragebogen.loescheFrageById(neueFrage.getId());

    List<TextFrage> textFragen = fragebogen.getTextFragen();

    Assertions.assertFalse(textFragen.contains(neueFrage));
  }


  @Test
  public void getFrageTest() {
    TextFrage neueFrage = new TextFrage("lorem");
    fragebogen.addFrage(neueFrage);
    TextFrage frage = (TextFrage) fragebogen.getFrage(neueFrage.getId());
    Assertions.assertEquals(frage, neueFrage);

  }

  @Test
  public void addFrageTest() {

    TextFrage neueFrage = new TextFrage("covid19 sucks");
    fragebogen.addFrage(neueFrage);
    List<TextFrage> textFragen = fragebogen.getTextFragen();
    Assertions.assertTrue(textFragen.contains(neueFrage));
  }

  @Test
  public void addStudentAsSubmittedTest() {
    Student student = new Student("nauth100");
    fragebogen.addStudentAsSubmitted(student);
    List<Student> abgegebeneStudierende = fragebogen.getAbgegebeneStudierende();
    Assertions.assertTrue(abgegebeneStudierende.contains(student));

  }
}
