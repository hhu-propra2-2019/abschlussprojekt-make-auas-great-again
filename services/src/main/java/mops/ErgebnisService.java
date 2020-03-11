package mops;

import java.util.List;

@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class ErgebnisService {
  public Fragebogen berechneErgebnisse(Fragebogen fragebogen) {
    List<Frage> fragen = fragebogen.getFragen();
    for (Frage frage : fragen) {
      checkForMultipleChoiceQuestion(frage);
    }
    return fragebogen;
  }

  private void checkForMultipleChoiceQuestion(Frage frage) {
    if (frage instanceof MultipleChoiceFrage) {
      ((MultipleChoiceFrage) frage).berechneErgebnis();
    }
  }
}
