package mops.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.Fragebogen;
import mops.SubmitService;
import mops.database.MockFragebogenRepository;
import mops.fragen.Frage;

@RequestMapping("/feedback/details/submit")
@Controller
@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class SubmitController {
  private final transient FragebogenRepository frageboegen;
  private final transient SubmitService service;

  public SubmitController() {
    frageboegen = new MockFragebogenRepository();
    service = new SubmitService();
  }

  @PostMapping("/{bogennr}")
  public String submitFeedback(@PathVariable long bogennr, HttpServletRequest req) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    Map<Long, String> antworten = new HashMap<>();
    for (Frage frage : fragebogen.getFragen()) {
      antworten.put(frage.getId(), req.getParameter("answer-" + frage.getId()));
    }
    service.saveAntworten(fragebogen, antworten);
    return "redirect:/feedback/";
  }
}
