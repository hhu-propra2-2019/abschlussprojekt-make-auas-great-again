package mops.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

  @GetMapping("/kontaktend")
  public String kontaktended(Model model) {
    return "kontaktend";
  }

  @PostMapping("/kontakt")
  public String postMessage(Model model) {
    model.addAttribute("post", "post");
    return "kontakt";
  }


}