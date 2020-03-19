package mops.controllers;

import javax.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  @GetMapping("")
  @RolesAllowed("ROLE_orga")
  public String getOrganisatorMainPage() {
    return "dozenten/dozent";
  }
}
