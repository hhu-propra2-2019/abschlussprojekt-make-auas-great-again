package mops.controllers;

import javax.annotation.security.RolesAllowed;
import mops.DozentService;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.antworten.TextAntwort;
import mops.database.MockVeranstaltungsRepository;
import mops.rollen.Dozent;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback/dozenten/watch")
public class DozentErgebnisController {
  private static final String orgaRole = "ROLE_orga";
  private static final String account = "account";

  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient TypeChecker typechecker;
  private final transient DozentService dozentservice;

  public DozentErgebnisController() {
    veranstaltungen = new MockVeranstaltungsRepository();
    typechecker = new TypeChecker();
    dozentservice = new DozentService();
  }

  @GetMapping("")
  @RolesAllowed(orgaRole)
  public String getFragebogenUebersicht(KeycloakAuthenticationToken token, Model model) {
    Dozent dozent = createDozentFromToken(token);
    model.addAttribute("frageboegen",
        dozentservice.holeFrageboegenVomDozent(veranstaltungen.getAllFromDozent(dozent)));
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/frageboegen";
  }

  @GetMapping("/{bogennr}")
  @RolesAllowed(orgaRole)
  public String getAntwortenEinesFragebogens(KeycloakAuthenticationToken token,
      @PathVariable long bogennr, Model model) {
    Dozent dozent = createDozentFromToken(token);
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ergebnisse";
  }

  @GetMapping("/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String bearbeiteTextAntwort(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, Model model) {
    Dozent dozent = createDozentFromToken(token);
    TextAntwort antwort = dozentservice.getTextAntwort(fragennr, antwortnr,
        veranstaltungen.getFragebogenFromDozentById(bogennr, dozent));
    model.addAttribute("antwort", antwort);
    model.addAttribute("bogennr", bogennr);
    model.addAttribute("fragennr", fragennr);
    model.addAttribute("antwortnr", antwortnr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/zensieren";
  }

  @PostMapping("/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String speichereTextAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      @PathVariable Long antwortnr, String textfeld, KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    dozentservice.getTextAntwort(fragennr, antwortnr,
        veranstaltungen.getFragebogenFromDozentById(bogennr, dozent)).setAntworttext(textfeld);
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  @PostMapping("/publish/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String veroeffentlicheErgebnisseEinerFrage(@PathVariable Long bogennr,
      @PathVariable Long fragennr, KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    dozentservice.getFrage(fragennr, veranstaltungen.getFragebogenFromDozentById(bogennr, dozent))
        .aendereOeffentlichkeitsStatus();
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }

  private Dozent createDozentFromToken(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Dozent(principal.getKeycloakSecurityContext().getIdToken().getId());
  }
}
