package mops;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mops.antworten.TextAntwort;
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

  @BeforeEach
  public void setUp() {
    mockFragebogen = mock(Fragebogen.class);
    textfrage = new TextFrage(Long.valueOf(1), "Beispielfrage");
    when(mockFragebogen.getFragen()).thenReturn(List.of(textfrage));
  }

  @Test
  public void valideAntwortWirdGespeichert() {
    Map<Long, String> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), "beispielantwort");

    service.saveAntworten(mockFragebogen, antwort);

    Set<TextAntwort> antworten = textfrage.getAntworten();
    assertFalse(antworten.isEmpty());
  }

  @Test
  public void leereAntwortWirdNichtGespeichert() {
    Map<Long, String> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), "");

    service.saveAntworten(mockFragebogen, antwort);

    Set<TextAntwort> antworten = textfrage.getAntworten();
    assertTrue(antworten.isEmpty());
  }

  @Test
  public void nullStringWirdIgnoriert() {
    Map<Long, String> antwort = new HashMap<>();
    antwort.put(Long.valueOf(1), null);

    service.saveAntworten(mockFragebogen, antwort);

    Set<TextAntwort> antworten = textfrage.getAntworten();
    assertTrue(antworten.isEmpty());
  }

  @Test
  @DisplayName("beantwortete Frageboegen Werden für den Student nicht mehr gezeigt")
  public void getNotSubmittedFrageboegen(){
    Student student = new Student("studentin");
    Fragebogen fragebogen = mock(Fragebogen.class);
    List<Fragebogen> fragebogenList = new ArrayList<>();
    List<Fragebogen> notSubmittedFrageboegen;
    fragebogenList.add(mockFragebogen);
    fragebogenList.add(fragebogen);
    when(mockFragebogen.getAbgegebeneStudierende())
        .thenReturn(new ArrayList<>(Collections.singletonList(student)));

    service.addStudentAsSubmitted(mockFragebogen, student);
    notSubmittedFrageboegen = service.notSubmittedFrageboegen(fragebogenList, student);

    assertFalse(notSubmittedFrageboegen.contains(mockFragebogen));

  }
}
