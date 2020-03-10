package mops.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mops.Frage;
import mops.Fragebogen;
import mops.controllers.FragebogenRepository;

@Service
@AllArgsConstructor
public class SubmitService {
  private final transient FragebogenRepository frageboegen;

  public void saveAntworten(HttpServletRequest req, long bogennr) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    List<Frage> fragen = fragebogen.getFragen();
    for (Frage frage : fragen) {
      String antwort = req.getParameter("answer-" + frage.getId());
      frage.addAntwort(antwort);
    }
  }
}
