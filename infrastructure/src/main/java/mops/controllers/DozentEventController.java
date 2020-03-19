package mops.controllers;

import javax.annotation.security.RolesAllowed;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.DateTimeService;
import mops.Veranstaltung;
import mops.database.MockVeranstaltungsRepository;
import mops.rollen.Dozent;
import mops.security.Account;

@Controller
@RequestMapping("/feedback/dozenten")
public class DozentEventController {
  private static final String ORGA_ROLE = "ROLE_orga";
  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient DateTimeService datetime = new DateTimeService();

  public DozentEventController() {
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

  @GetMapping("/event/{veranstaltung}")
  @RolesAllowed(ORGA_ROLE)
  public String getVeranstaltungsDetails(KeycloakAuthenticationToken token, Model model,
      @PathVariable Long veranstaltung) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    model.addAttribute("datetime", datetime);
    model.addAttribute("veranstaltung", veranstaltungen.getVeranstaltungById(veranstaltung));
    return "dozenten/veranstaltung";
  }

  @PostMapping("/event/new")
  @RolesAllowed(ORGA_ROLE)
  public String erstelleNeueVeranstaltung(KeycloakAuthenticationToken token, Model model,
      String veranstaltungsname, String semester) {
    veranstaltungen
        .save(new Veranstaltung(veranstaltungsname, semester, createDozentFromToken(token)));
    return "redirect:/feedback/dozenten";
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
