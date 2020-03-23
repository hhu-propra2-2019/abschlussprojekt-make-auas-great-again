package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mops.antworten.TextAntwort;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;

public class DozentService {
  /**
   * Holt alle Fragebögen aus der Veranstaltungsliste.
   *
   * @param veranstaltungen Liste von Veranstaltungen.
   * @return frageboegen alle Fragebögen aus den Veranstaltungen der Liste.
   */
  public List<Fragebogen> holeFrageboegenVomDozent(List<Veranstaltung> veranstaltungen) {
    List<Fragebogen> result = new ArrayList<>();
    veranstaltungen.stream().forEach(x -> result.addAll(x.getFrageboegen()));
    return result;
  }

  public MultipleChoiceFrage getMultipleChoiceFrage(Long fragennr, Fragebogen fragebogen) {
    return (MultipleChoiceFrage) fragebogen.getFrage(fragennr);
  }

  public TextAntwort getTextAntwort(Long fragennr, Long antwortnr, Fragebogen fragebogen) {
    TextFrage frage = (TextFrage) fragebogen.getFrage(fragennr);
    return (TextAntwort) frage.getAntwortById(antwortnr);
  }

  public Frage getFrage(Long fragennr, Fragebogen fragebogen) {
    return fragebogen.getFrage(fragennr);
  }

  /**
   * Erzeugt ein passendes Fragenobjekt anhand des übergebenen Fragtyps.
   *
   * @param fragetyp Der Typ der Frage, entweder 'multiplechoice' oder 'textfrage'
   * @param fragetext Der Text der Frage
   * @return das neue Fragenobjekt
   */
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public Frage createNeueFrageAnhandFragetyp(String fragetyp, String fragetext) {
    Random idgenerator = new Random();
    if ("multiplechoice".equals(fragetyp)) {
      return new SingleResponseFrage(idgenerator.nextLong(), fragetext, false);
    } else if ("textfrage".equals(fragetyp)) {
      return new TextFrage(fragetext);
    } else {
      return new MultipleResponseFrage(idgenerator.nextLong(), fragetext);
    }
  }
}
