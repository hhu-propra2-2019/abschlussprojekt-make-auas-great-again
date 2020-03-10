package mops.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.Fragebogen;
import mops.services.SubmitService;

@RequestMapping("/feedback/details/submit")
@Controller
public class SubmitController {
  private final transient FragebogenRepository frageboegen;
  private final transient SubmitService service;

  public SubmitController(FragebogenRepository frageboegen, SubmitService service) {
    this.frageboegen = frageboegen;
    this.service = service;
  }

  @PostMapping("/{bogennr}")
  public String submitFeedback(@PathVariable long bogennr, HttpServletRequest req) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    service.saveAntworten(req, fragebogen);
    return "redirect:/feedback/";
  }
}
