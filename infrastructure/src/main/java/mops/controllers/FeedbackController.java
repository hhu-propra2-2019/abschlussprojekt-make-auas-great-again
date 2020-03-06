package mops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class FeedbackController {

  @GetMapping("/")
  public String uebersicht(Model model) {
    return "index";
  }

  @GetMapping("/details")
  public String fragebogen(Model model) {
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