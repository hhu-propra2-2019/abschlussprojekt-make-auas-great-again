package mops;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mops.antworten.Antwort;
import mops.fragen.TextFrage;
import mops.rollen.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    Map<Long, List<String>> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), List.of("beispielantwort"));

    service.saveAntworten(mockFragebogen, antwort);

    List<Antwort> antworten = textfrage.getAntworten();
    assertFalse(antworten.isEmpty());
  }

  @Test
  public void leereAntwortWirdNichtGespeichert() {
    Map<Long, List<String>> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), List.of(""));

    service.saveAntworten(mockFragebogen, antwort);

    List<Antwort> antworten = textfrage.getAntworten();
    assertTrue(antworten.isEmpty());
  }

  @Test
  public void nullStringWirdIgnoriert() {
    Map<Long, List<String>> antwort = new HashMap<>();
    List<String> antwortliste = new ArrayList<>();
    antwortliste.add(null);
    antwort.put(Long.valueOf(1), antwortliste);

    service.saveAntworten(mockFragebogen, antwort);

    List<Antwort> antworten = textfrage.getAntworten();
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
