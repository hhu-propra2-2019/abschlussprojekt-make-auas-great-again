package mops;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mops.antworten.Antwort;
import mops.fragen.TextFrage;
import mops.rollen.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings( {"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage"})
public class SubmitServiceTest {
  private transient SubmitService service = new SubmitService();
  private transient Fragebogen mockFragebogen;
  private transient TextFrage textfrage;
  private transient Student student;
  private transient Fragebogen fragebogen;
  private transient List<Fragebogen> fragebogenList;
  private transient List<Fragebogen> notSubmittedFrageboegen;

  @BeforeEach
  public void setUp() {
    mockFragebogen = mock(Fragebogen.class);
    textfrage = new TextFrage(Long.valueOf(1), "Beispielfrage");
    when(mockFragebogen.getFragen()).thenReturn(List.of(textfrage));
    student = new Student("studentin");
    fragebogen = new Fragebogen("Programmierung");

  }

  @Test
  public void valideAntwortWirdGespeichert() {
    Map<Long, String> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), "beispielantwort");

    service.saveAntworten(mockFragebogen, antwort);

    Set<Antwort> antworten = textfrage.getAntworten();
    assertFalse(antworten.isEmpty());
  }

  @Test
  public void leereAntwortWirdNichtGespeichert() {
    Map<Long, String> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), "");

    service.saveAntworten(mockFragebogen, antwort);

    Set<Antwort> antworten = textfrage.getAntworten();
    assertTrue(antworten.isEmpty());
  }

  @Test
  public void nullStringWirdIgnoriert() {
    Map<Long, String> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), null);

    service.saveAntworten(mockFragebogen, antwort);

    Set<Antwort> antworten = textfrage.getAntworten();
    assertTrue(antworten.isEmpty());
  }

  @Test
  @DisplayName("Student als abgegebener in dem entsprechenden Fragebogen speichern ")
  public void addStudentAsSubmitted() {

    service.addStudentAsSubmitted(fragebogen, student);

    assertTrue(fragebogen.getAbgegebeneStudierende().contains(student));
  }

  @Test
  @DisplayName("beantwortete Frageboegen Werden für den Student nicht mehr gezeigt")
  public void getNotSubmittedFrageboegen() {
    fragebogenList = new ArrayList<>();
    fragebogenList.add(mockFragebogen);
    fragebogenList.add(fragebogen);

    service.addStudentAsSubmitted(fragebogen, student);
    notSubmittedFrageboegen = service.notSubmittedFrageboegen(fragebogenList, student);

    assertFalse(notSubmittedFrageboegen.contains(fragebogen));

  }

  @Test
  @DisplayName("nicht beantwortete Frageboegen Werden für den Student gezeigt")
  public void getNotYetSubmittedFrageboegen() {
    fragebogenList = new ArrayList<>();
    fragebogenList.add(mockFragebogen);
    fragebogenList.add(fragebogen);

    service.addStudentAsSubmitted(fragebogen, student);
    notSubmittedFrageboegen = service.notSubmittedFrageboegen(fragebogenList, student);

    assertTrue(notSubmittedFrageboegen.contains(mockFragebogen));
  }
}
