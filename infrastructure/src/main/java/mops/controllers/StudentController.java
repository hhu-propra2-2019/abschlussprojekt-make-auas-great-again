package mops.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.Fragebogen;
import mops.SingleSubmitService;
import mops.SubmitService;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.database.MockVeranstaltungsRepository;
import mops.fragen.Frage;
import mops.rollen.Student;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedback/studenten")
public class StudentController {
  public static final String studentRole = "ROLE_studentin";
  private final transient String account = "account";
  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient SubmitService submitService = new SubmitService();
  private transient TypeChecker typeChecker = new TypeChecker();
  private final transient SingleSubmitService singleSubmitService = new SingleSubmitService();

  public StudentController(MockVeranstaltungsRepository veranstaltungen) {
    this.veranstaltungen = veranstaltungen;
  }

  @GetMapping("")
  @RolesAllowed(studentRole)
  public String uebersicht(KeycloakAuthenticationToken token, Model model, String search) {
    Student student = new Student(((KeycloakPrincipal) token.getPrincipal()).getName());
    if (searchNotEmpty(search)) {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromStudentContaining(student, search));
    } else {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromStudent(student));
    }
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/veranstaltungen";
  }

  @GetMapping("/frageboegen")
  @RolesAllowed(studentRole)
  public String fragebogen(KeycloakAuthenticationToken token,
                           Model model, String search, Long veranstaltungId) {
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungId);
    if (searchNotEmpty(search)) {
      model.addAttribute("frageboegen", veranstaltung.getFrageboegenContaining(search));
    } else {
      model.addAttribute("frageboegen", veranstaltung.getFrageboegen());
    }
    model.addAttribute("veranstaltung", veranstaltung);
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/fragebogen_uebersicht";
  }

  @GetMapping("/frageboegen/details")
  @RolesAllowed(studentRole)
  public String fragebogenDetails(KeycloakAuthenticationToken token, Model model, @RequestParam Long
      fragebogen, @RequestParam Long veranstaltung) {
    model.addAttribute("fragebogen",
        veranstaltungen.getFragebogenByIdFromVeranstaltung(fragebogen, veranstaltung));
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute("veranstaltung", veranstaltungen.getVeranstaltungById(veranstaltung));
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/fragebogen_details";
  }

  @PostMapping("/details/submit")
  @RolesAllowed(studentRole)
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public String submitFeedback(KeycloakAuthenticationToken token, @RequestParam Long bogennr,
                               HttpServletRequest req, Model model, @RequestParam Long veranstaltung) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenByIdFromVeranstaltung(bogennr, veranstaltung);
    Map<Long, String> antworten = new HashMap<>();
    for (Frage frage : fragebogen.getFragen()) {
      antworten.put(frage.getId(), req.getParameter("answer-" + frage.getId()));
    }
    submitService.saveAntworten(fragebogen, antworten);
    Student student = new Student(((KeycloakPrincipal) token.getPrincipal()).getName());
    singleSubmitService.addStudentAsSubmitted(fragebogen, student);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "redirect:/feedback/studenten";
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
