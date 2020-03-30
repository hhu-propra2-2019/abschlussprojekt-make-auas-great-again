package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import mops.antworten.TextAntwort;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;
import mops.rollen.Dozent;
import org.junit.jupiter.api.Test;

public class DozentServiceTest {
  private static final String ANALYSIS_I = "Analysis I";
  private static final String WIE_GEHTS = "Wie geht's?";
  private final transient DozentService service = new DozentService();

  @Test
  public void textFrageWirdKorrektErzeugt() {
    String fragetyp = "textfrage";
    String fragetext = WIE_GEHTS;

    Frage totest = service.createNeueFrageAnhandFragetyp(fragetyp, fragetext);

    assertTrue(totest instanceof TextFrage);
  }

  @Test
  public void singleResponseFrageWirdKorrektErzeugt() {
    String fragetyp = "multiplechoice";
    String fragetext = WIE_GEHTS;

    Frage totest = service.createNeueFrageAnhandFragetyp(fragetyp, fragetext);

    assertTrue(totest instanceof SingleResponseFrage);
  }

  @Test
  public void multipleResponseFrageWirdKorrektErzeugt() {
    String fragetyp = "multipleresponse";
    String fragetext = WIE_GEHTS;

    Frage totest = service.createNeueFrageAnhandFragetyp(fragetyp, fragetext);

    assertTrue(totest instanceof MultipleResponseFrage);
  }

  @Test
  public void korrekteFrageWirdZurueckGegeben() {
    Fragebogen fragebogen = new Fragebogen(ANALYSIS_I);
    TextFrage frage = new TextFrage(1L, WIE_GEHTS);
    TextFrage frage2 = new TextFrage(2L, "Sind sie zufrieden mit der Veranstaltung?");
    fragebogen.addFrage(frage);
    fragebogen.addFrage(frage2);

    Frage totest = service.getFrage(1L, fragebogen);

    assertEquals(totest, frage);
  }

  @Test
  public void korrekteTextAntwortWirdZurueckGegeben() {
    TextAntwort antwort = new TextAntwort(1L, "Sehr gut");
    TextAntwort antwort2 = new TextAntwort(2L, "Sehr schlecht");
    TextFrage frage = new TextFrage(1L, WIE_GEHTS);
    frage.addAntwort(antwort);
    frage.addAntwort(antwort2);
    TextFrage frage2 = new TextFrage(2L, "Sind Sie zufrieden?");
    Fragebogen fragebogen = new Fragebogen(ANALYSIS_I);
    fragebogen.addFrage(frage2);
    fragebogen.addFrage(frage);

    TextAntwort totest = service.getTextAntwort(1L, 2L, fragebogen);

    assertEquals(totest, antwort2);
  }

  @Test
  public void korrekteMultipleChoiceFrageWirdZurueckGegeben() {
    Fragebogen fragebogen = new Fragebogen(ANALYSIS_I);
    TextFrage frage = new TextFrage(1L, WIE_GEHTS);
    MultipleChoiceFrage frage2 =
        new MultipleChoiceFrage(2L, "Die Vorlesung ist strukturiert");
    MultipleChoiceFrage frage3 = new MultipleChoiceFrage(3L, "Der Dozent ist motiviert");
    fragebogen.addFrage(frage);
    fragebogen.addFrage(frage2);
    fragebogen.addFrage(frage3);

    Frage totest = service.getMultipleChoiceFrage(2L, fragebogen);

    assertEquals(totest, frage2);
  }

  @Test
  public void alleFrageboegenWerdenEingesammelt() {
    Fragebogen fragebogen = new Fragebogen(ANALYSIS_I);
    Fragebogen fragebogen2 = new Fragebogen("Tutorium zur Analysis I");
    Veranstaltung analysis = new Veranstaltung(ANALYSIS_I, "WiSe 2010", new Dozent("heinz001"));
    analysis.addFragebogen(fragebogen);
    analysis.addFragebogen(fragebogen2);
    Fragebogen fragebogen3 = new Fragebogen("Programmierung");
    Veranstaltung prog = new Veranstaltung("Programmierung", "WiSe 2010", new Dozent("heinz001"));
    prog.addFragebogen(fragebogen3);
    List<Veranstaltung> veranstaltungen = List.of(analysis, prog);
    List<Fragebogen> frageboegen = List.of(fragebogen, fragebogen2, fragebogen3);

    List<Fragebogen> totest = service.holeFrageboegenVomDozent(veranstaltungen);

    assertEquals(totest, frageboegen);
  }

  @Test
  public void multipleChoiceFrageAusTemplateWirdKorrektGeholt() {
    FragebogenTemplate template = new FragebogenTemplate(1L, "Test", new ArrayList<>());
    Dozent dozent = new Dozent("heinz001", "Heinz", "Mustermann", new ArrayList<>());
    SingleResponseFrage frage1 =
        new SingleResponseFrage(1L, "Testfrage1", false, new ArrayList<>(), new ArrayList<>());
    template.addFrage(frage1);
    template.addFrage(new MultipleResponseFrage(2L, "Testfrage2"));
    dozent.addTemplate(template);

    MultipleChoiceFrage totest = service.getMultipleChoiceFromTemplate(1L, dozent, 1L);

    assertEquals(totest, frage1);
  }
}
