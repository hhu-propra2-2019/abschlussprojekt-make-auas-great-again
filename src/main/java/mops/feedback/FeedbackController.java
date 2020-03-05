package mops.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}