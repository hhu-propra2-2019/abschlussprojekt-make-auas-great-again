package mops;

import java.util.ArrayList;
import java.util.List;
import mops.antworten.TextAntwort;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
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
    return frage.getAntwortById(antwortnr);
  }

  public Frage getFrage(Long fragennr, Fragebogen fragebogen) {
    return fragebogen.getFrage(fragennr);
  }

  @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
  public List<Frage> getFragenlisteOhneAntworten(List<Frage> altefragen) {
    List<Frage> result = new ArrayList<>();
    for (Frage frage : altefragen) {
      if (frage instanceof TextFrage) {
        result.add(new TextFrage(frage.getFragentext()));
      } else {
        MultipleChoiceFrage neuefrage = new MultipleChoiceFrage(frage.getFragentext());
        for (Auswahl auswahl : ((MultipleChoiceFrage) frage).getChoices()) {
          neuefrage.addChoice(auswahl);
        }
        result.add(neuefrage);
      }
    }
    return result;
  }

  /**
   * Erzeugt ein passendes Fragenobjekt anhand des übergebenen Fragtyps.
   *
   * @param fragetyp Der Typ der Frage, entweder 'multiplechoice' oder 'textfrage'
   * @param fragetext Der Text der Frage
   * @return das neue Fragenobjekt
   */
  public Frage createNeueFrageAnhandFragetyp(String fragetyp, String fragetext) {
    if ("multiplechoice".equals(fragetyp)) {
      return new MultipleChoiceFrage(fragetext);
    } else {
      return new TextFrage(fragetext);
    }
  }
}
