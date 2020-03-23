package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mops.antworten.TextAntwort;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;
import org.junit.jupiter.api.Test;

public class DozentServiceTest {
  private final DozentService service = new DozentService();

  @Test
  public void textFrageWirdKorrektErzeugt() {
    String fragetyp = "textfrage";
    String fragetext = "Wie geht's?";

    Frage totest = service.createNeueFrageAnhandFragetyp(fragetyp, fragetext);

    assertTrue(totest instanceof TextFrage);
  }

  @Test
  public void singleResponseFrageWirdKorrektErzeugt() {
    String fragetyp = "multiplechoice";
    String fragetext = "Wie geht's?";

    Frage totest = service.createNeueFrageAnhandFragetyp(fragetyp, fragetext);

    assertTrue(totest instanceof SingleResponseFrage);
  }

  @Test
  public void multipleResponseFrageWirdKorrektErzeugt() {
    String fragetyp = "multipleresponse";
    String fragetext = "Wie geht's?";

    Frage totest = service.createNeueFrageAnhandFragetyp(fragetyp, fragetext);

    assertTrue(totest instanceof MultipleResponseFrage);
  }

  @Test
  public void korrekteFrageWirdZurueckGegeben() {
    Fragebogen fragebogen = new Fragebogen("Analysis I", "Heinz Mustermann");
    TextFrage frage = new TextFrage(1L, "Wie geht's?");
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
    TextFrage frage = new TextFrage(1L, "Wie geht's?");
    frage.addAntwort(antwort);
    frage.addAntwort(antwort2);
    TextFrage frage2 = new TextFrage(2L, "Sind Sie zufrieden?");
    Fragebogen fragebogen = new Fragebogen("Analysis I", "Heinz Mustermann");
    fragebogen.addFrage(frage2);
    fragebogen.addFrage(frage);

    TextAntwort totest = service.getTextAntwort(1L, 2L, fragebogen);

    assertEquals(totest, antwort2);
  }

  @Test
  public void korrekteMultipleChoiceFrageWirdZurueckGegeben() {
    Fragebogen fragebogen = new Fragebogen("Analysis I", "Heinz Mustermann");
    TextFrage frage = new TextFrage(1L, "Wie geht's?");
    MultipleChoiceFrage frage2 =
        new MultipleChoiceFrage(2L, "Die Vorlesung ist strukturiert", false);
    MultipleChoiceFrage frage3 = new MultipleChoiceFrage(3L, "Der Dozent ist motiviert", false);
    fragebogen.addFrage(frage);
    fragebogen.addFrage(frage2);
    fragebogen.addFrage(frage3);

    Frage totest = service.getMultipleChoiceFrage(2L, fragebogen);

    assertEquals(totest, frage2);
  }
}
