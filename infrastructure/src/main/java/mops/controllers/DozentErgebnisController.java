package mops.controllers;

import javax.annotation.security.RolesAllowed;
import mops.DozentService;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.antworten.TextAntwort;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedback/dozenten/watch")
public class DozentErgebnisController {
  private static final String orgaRole = "ROLE_orga";
  private static final String account = "account";

  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient TypeChecker typechecker;
  private final transient DozentService dozentservice;

  public DozentErgebnisController(mops.database.VeranstaltungsRepository veranstaltungen) {
    this.veranstaltungen = veranstaltungen;
    typechecker = new TypeChecker();
    dozentservice = new DozentService();
  }

  @GetMapping("/{bogennr}")
  @RolesAllowed(orgaRole)
  public String getAntwortenEinesFragebogens(KeycloakAuthenticationToken token,
                                             @PathVariable long bogennr, Model model, Long veranstaltungid) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenById(bogennr);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ergebnisse";
  }

  @GetMapping("/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String bearbeiteTextAntwort(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
                                     @PathVariable Long fragennr, @PathVariable Long antwortnr, Model model,
                                     Long veranstaltungid) {
    TextAntwort antwort = dozentservice.getTextAntwort(fragennr, antwortnr,
        veranstaltungen.getFragebogenById(bogennr));
    model.addAttribute("antwort", antwort);
    model.addAttribute("bogennr", bogennr);
    model.addAttribute("fragennr", fragennr);
    model.addAttribute("antwortnr", antwortnr);
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/zensieren";
  }

  @PostMapping("/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String speichereTextAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
                                     @PathVariable Long antwortnr, String textfeld, KeycloakAuthenticationToken token,
                                     RedirectAttributes ra, Long veranstaltungid) {
    ra.addAttribute("veranstaltungid", veranstaltungid);
    dozentservice.getTextAntwort(fragennr, antwortnr,
        veranstaltungen.getFragebogenById(bogennr)).setAntworttext(textfeld);
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  @PostMapping("/publish/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String veroeffentlicheErgebnisseEinerFrage(@PathVariable Long bogennr,
                                                    @PathVariable Long fragennr, KeycloakAuthenticationToken token, Long veranstaltungid,
                                                    RedirectAttributes ra) {
    ra.addAttribute("veranstaltungid", veranstaltungid);
    dozentservice.getFrage(fragennr, veranstaltungen.getFragebogenById(bogennr))
        .aendereOeffentlichkeitsStatus();
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }
}
