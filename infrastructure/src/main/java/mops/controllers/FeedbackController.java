package mops.controllers;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.TypeChecker;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/feedback")
@Controller
@SuppressWarnings("checkstyle:MissingJavadocMethod")
public class FeedbackController {
  private static final String emptySearchString = "";
  private final transient String account = "account";
  private final transient Counter authenticatedAccess;
  private final transient Counter publicAccess;
  @Autowired
  @Qualifier("Faker")
  private transient FragebogenRepository frageboegen;
  private transient TypeChecker typeChecker = new TypeChecker();


  public FeedbackController(MeterRegistry registry) {
    this.authenticatedAccess = registry.counter("access.authenticated");
    this.publicAccess = registry.counter("access.public");
  }

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
      model.addAttribute(account, createAccountFromPrincipal(token));
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
    // @TODO Law of Demeter FIX !!
    // FOR LOOP TO ITERATE LIST OF FRAGEBOGEN
    long id = frageboegen.getFragebogenById(1L).getBogennr();
    LocalDateTime start = frageboegen.getFragebogenById(id).getStartdatum();
    LocalDateTime end = frageboegen.getFragebogenById(id).getEnddatum();
    model.addAttribute("startdate", formatDate(start));
    model.addAttribute("enddate", formatDate(end));
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    authenticatedAccess.increment();
    return "uebersicht";
  }


  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  String formatDate(LocalDateTime date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss");
    String formatDateTime = date.format(formatter);
    return formatDateTime;
  }

  private boolean searchNotEmpty(String search) {
    return !emptySearchString.equals(search) && search != null;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @GetMapping("/details")
  @RolesAllowed( {"ROLE_studentin", "ROLE_orga"})
  public String fragebogen(KeycloakAuthenticationToken token, Model model, @RequestParam Long
      id) {
    model.addAttribute("fragebogen", frageboegen.getFragebogenById(id));
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    authenticatedAccess.increment();
    return "details";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) throws Exception {
    request.logout();
    return "redirect:/";
  }
}
