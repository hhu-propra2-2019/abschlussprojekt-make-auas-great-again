package mops.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webController {
  @GetMapping("/feedback")
  public String init(Model model) {
    return "index";
  }
}