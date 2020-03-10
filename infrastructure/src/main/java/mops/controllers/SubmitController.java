package mops.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.AllArgsConstructor;
import mops.services.SubmitService;

@RequestMapping("/feedback/details/submit")
@AllArgsConstructor
@Controller
public class SubmitController {
  private final transient SubmitService service;

  @PostMapping("/{bogennr}")
  public String submitFeedback(@PathVariable long bogennr, HttpServletRequest req) {
    service.saveAntworten(req, bogennr);
    return "redirect:/feedback/";
  }
}
