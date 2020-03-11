package mops.controllers;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/feedback")
@Controller
public class FeedbackController {

  private static final String emptySearchString = "";
  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typeChecker;

  private final Counter authenticatedAccess;
  private final Counter publicAccess;


  public FeedbackController(MeterRegistry registry) {
    this.frageboegen = new MockFragebogenRepository();
    this.typeChecker = new TypeChecker();
    this.authenticatedAccess = registry.counter("access.authenticated");
    this.publicAccess = registry.counter("access.public");
  }

  /**
   * Nimmt das Authentifizierungstoken von Keycloak und erzeugt ein AccountDTO für die Views.
   *
   * @param token
   * @return neuen Account der im Template verwendet wird
   */
  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }

  @GetMapping("/")
  public String index(KeycloakAuthenticationToken token, Model model) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));
    }
    publicAccess.increment();
    return "index";
  }

  @GetMapping("/uebersicht")
  @RolesAllowed( {"ROLE_studentin", "ROLE_orga"})
  public String uebersicht(KeycloakAuthenticationToken token, Model model, String search) {
    if (searchNotEmpty(search)) {
      model.addAttribute("frageboegen", frageboegen.getAllContaining(search));
    } else {
      model.addAttribute("frageboegen", frageboegen.getAll());
    }
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute("account", createAccountFromPrincipal(token));
    authenticatedAccess.increment();
    return "uebersicht";
  }

  private boolean searchNotEmpty(String search) {
    return !emptySearchString.equals(search) && search != null;
  }

  @GetMapping("/details")
  @RolesAllowed( {"ROLE_studentin", "ROLE_orga"})
  public String fragebogen(KeycloakAuthenticationToken token, Model model, @RequestParam Long id) {
    model.addAttribute("fragebogen", frageboegen.getFragebogenById(id));
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute("account", createAccountFromPrincipal(token));
    authenticatedAccess.increment();
    return "details";
  }

  @GetMapping("/kontakt")
  @RolesAllowed("ROLE_studentin")
  public String kontakt(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    model.addAttribute("username", principal.getName());
    model.addAttribute("email", principal.getKeycloakSecurityContext().getIdToken().getEmail());
    authenticatedAccess.increment();
    return "kontakt";
  }

  @PostMapping("/kontakt")
  public String postMessage(Model model) {
    model.addAttribute("post", "post");
    model.addAttribute("submit", "submit");
    return "kontakt";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) throws Exception {
    request.logout();
    return "redirect:/";
  }
}
