package mops.controllers;

import java.time.LocalDateTime;
import javax.annotation.security.RolesAllowed;
import mops.DateTimeService;
import mops.Veranstaltung;
import mops.filehandling.CsvReader;
import mops.rollen.Dozent;
import mops.rollen.Student;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedback/dozenten")
public class DozentEventController {
  private static final String ORGA_ROLE = "ROLE_orga";
  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient DateTimeService datetime = new DateTimeService();
  private transient CsvReader csvReader;

  public DozentEventController(mops.database.VeranstaltungsRepository veranstaltungen) {
    this.veranstaltungen = veranstaltungen;
  }

  @GetMapping("")
  @RolesAllowed(ORGA_ROLE)
  public String getOrganisatorMainPage(KeycloakAuthenticationToken token, Model model,
                                       String search) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    if (searchNotEmpty(search)) {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromDozentContaining(createDozentFromToken(token), search));
    } else {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromDozent(createDozentFromToken(token)));
    }
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
    model.addAttribute("currenttime", LocalDateTime.now());
    model.addAttribute("veranstaltung",
        veranstaltungen.getVeranstaltungById(veranstaltung));
    return "dozenten/veranstaltung";
  }

  @PostMapping("/event/new")
  @RolesAllowed(ORGA_ROLE)
  public String erstelleNeueVeranstaltung(KeycloakAuthenticationToken token,
                                          String veranstaltungsname, String semester) {
    Veranstaltung neu = new Veranstaltung(veranstaltungsname, semester,
        createDozentFromToken(token));
    veranstaltungen.save(neu);
    return "redirect:/feedback/dozenten";
  }

  @PostMapping("/event/addStudenten/{veranstaltungsNr}")
  @RolesAllowed(ORGA_ROLE)
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 @PathVariable Long veranstaltungsNr,
                                 RedirectAttributes redirectAttributes) {
    csvReader = new CsvReader(file, veranstaltungen.getVeranstaltungById(veranstaltungsNr));
    veranstaltungen.save(csvReader.getVeranstaltung());
    redirectAttributes.addFlashAttribute("message", csvReader.getMessage());
    redirectAttributes.addFlashAttribute("status", csvReader.getMessageStatus());
    return "redirect:/feedback/dozenten/event/{veranstaltungsNr}";
  }

  @PostMapping("/event/addStudent/{veranstaltungsNr}")
  @RolesAllowed(ORGA_ROLE)
  public String addStudent(@PathVariable Long veranstaltungsNr,
                           RedirectAttributes redirectAttributes,
                           String newStudent) {
    Student student = new Student(newStudent);
    veranstaltungen.addStudentToVeranstaltungById(student, veranstaltungsNr);
    redirectAttributes.addFlashAttribute("message", newStudent
        + " wurde erfolgreich hinzugefügt!");
    redirectAttributes.addFlashAttribute("status", "success");
    return "redirect:/feedback/dozenten/event/{veranstaltungsNr}";
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

  private boolean searchNotEmpty(String search) {
    return !"".equals(search) && (search != null);
  }
}
