package mops.controllers;

import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("")
@Controller
public class FeedbackController {

  private final transient FragebogenRepository frageboegen;

  public FeedbackController() {
    this.frageboegen = new MockFragebogenRepository();
  }

  @GetMapping("/")
  public String uebersicht(Model model) {
    model.addAttribute("frageboegen", frageboegen.getAll());
    return "index";
  }

  @GetMapping("/details")
  public String fragebogen(Model model, @RequestParam Long id) {
    model.addAttribute("fragebogen", frageboegen.getFragebogenById(id));
    return "details";
  }

  @GetMapping("/kontakt")
  public String kontakt(Model model) {
    return "kontakt";
  }

  @GetMapping("/uebungen")
  public String uebungEval(Model model) {
    return "uebungen";
  }


  @PostMapping("/kontakt")
  public String postMessage(Model model) {
    model.addAttribute("post", "post");
    model.addAttribute("submit", "submit");
    return "kontakt";
  }
}