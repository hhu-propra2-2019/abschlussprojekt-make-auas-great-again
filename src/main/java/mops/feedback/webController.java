package mops.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class webController {

  @GetMapping("")
  public String uebersicht(Model model) {
    return "index";
  }
  @GetMapping("/details")
  public String fragebogen(Model model) {
    return "details";
  }
}