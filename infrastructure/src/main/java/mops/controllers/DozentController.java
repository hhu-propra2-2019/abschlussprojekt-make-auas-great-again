package mops.controllers;

import javax.annotation.security.RolesAllowed;
import mops.database.MockVeranstaltungsRepository;
import mops.rollen.Dozent;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  private static final String ORGA_ROLE = "ROLE_orga";
  private final VeranstaltungsRepository veranstaltungen;

  public DozentController() {
    veranstaltungen = new MockVeranstaltungsRepository();
  }

  @GetMapping("")
  @RolesAllowed(ORGA_ROLE)
  public String getOrganisatorMainPage(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    model.addAttribute("veranstaltungen",
        veranstaltungen.getAllFromDozent(createDozentFromToken(token)));
    return "dozenten/dozent";
  }

  @GetMapping("/event/new")
  @RolesAllowed(ORGA_ROLE)
  public String getVeranstaltungsErstellerSeite(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "dozenten/neueveranstaltung";
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }

  private Dozent createDozentFromToken(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Dozent(principal.getName());
  }
}
