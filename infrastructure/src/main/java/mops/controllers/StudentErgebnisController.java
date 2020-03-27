package mops.controllers;

import javax.annotation.security.RolesAllowed;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.rollen.Student;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedback/studenten/ergebnis")
public class StudentErgebnisController {
  public static final String studentRole = "ROLE_studentin";
  private final transient String account = "account";
  private final transient VeranstaltungsRepository veranstaltungen;
  private transient TypeChecker typeChecker = new TypeChecker();

  public StudentErgebnisController(mops.database.VeranstaltungsRepository veranstaltungen) {
    this.veranstaltungen = veranstaltungen;
  }

  @GetMapping("")
  @RolesAllowed(studentRole)
  public String ergebnis(KeycloakAuthenticationToken token, Model model, String search) {
    Student student = new Student(((KeycloakPrincipal) token.getPrincipal()).getName());
    if (searchNotEmpty(search)) {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromStudentContaining(student, search));
    } else {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromStudent(student));
    }
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/ergebnis";
  }

  @GetMapping("/frageboegen")
  @RolesAllowed(studentRole)
  public String ergebnisBoegen(KeycloakAuthenticationToken token, Model model,
                               @RequestParam Long veranstaltungId, String search) {
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungId);
    if (searchNotEmpty(search)) {
      model.addAttribute("frageboegen",
          veranstaltung.getFrageboegenContaining(search));
    } else {
      model.addAttribute("frageboegen", veranstaltung.getFrageboegen());
    }
    model.addAttribute("veranstaltung", veranstaltung);
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/ergebnis-frageboegen";
  }


  @GetMapping("/details")
  @RolesAllowed(studentRole)
  public String ergebnisUebersicht(KeycloakAuthenticationToken token, Model model,
                                   @RequestParam Long veranstaltung,
                                   @RequestParam Long fragebogen) {
    model.addAttribute("fragebogen", veranstaltungen.getFragebogenById(fragebogen));
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute("veranstaltung", veranstaltungen.getVeranstaltungById(veranstaltung));
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "/studenten/ergebnis-details";
  }

  private boolean searchNotEmpty(String search) {
    return !"".equals(search) && search != null;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }
}
