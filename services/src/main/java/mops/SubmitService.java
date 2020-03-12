package mops;

import java.util.Map;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class SubmitService {
  /**
   * Speichern der Antworten einer Umfrage in die Frage.
   *
   * @param fragebogen
   * @param antworten
   */
  public void saveAntworten(Fragebogen fragebogen, Map<Long, String> antworten) {
    for (mops.Frage frage : fragebogen.getFragen()) {
      String antwort = antworten.get(frage.getId());
      addAntwortIfValid(frage, antwort);
    }
  }

  private void addAntwortIfValid(mops.Frage frage, String antwort) {
    if ((antwort != null) && !antwort.equals("")) {
      frage.addAntwort(antwort);
    }
  }
}
