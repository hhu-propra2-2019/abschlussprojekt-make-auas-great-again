package mops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.database.MockFragebogenRepository;

@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  private final transient FragebogenRepository frageboegen;

  public DozentController() {
    frageboegen = new MockFragebogenRepository();
  }

}
