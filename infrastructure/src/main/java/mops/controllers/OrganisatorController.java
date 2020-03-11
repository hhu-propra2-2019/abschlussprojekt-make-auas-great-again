package mops.controllers;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import mops.DateTimeService;
import mops.Fragebogen;
import mops.SkalarFrage;
import mops.TextFrage;
import mops.TypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedback")
public class OrganisatorController {
  @Autowired
  @Qualifier("Org")
  private transient FragebogenRepository frageboegen;

  private transient DateTimeService dateTimeService = new DateTimeService();
  private transient TypeChecker typeChecker = new TypeChecker();

  /**
   * zeigt die Seite fürs Erstellen von FeedbackBogen.
   * lädet alle Fragen
   *
   * @param model Model
   * @return
   */
  @GetMapping("/creatForm")
  public String creatForm(Model model, Long id) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(id);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("typechecker", typeChecker);
    model.addAttribute("bogennr", id);
    return "formCreator";
  }

  /**
   * gibt die Übersicht für die Organisatoren zurück.
   */
  @GetMapping("/admin")
  public String adminOverview(Model model) {
    // dummy Daten, bis die Repesitory und services fertig sind
    List<String> veranstaltungen = new LinkedList<>();
    veranstaltungen.add("Vorlesung");
    veranstaltungen.add("Theoretische Übung");
    veranstaltungen.add("Praktische Übung");
    model.addAttribute("veranstaltungen", veranstaltungen);
    model.addAttribute("verName", "Programmierung");
    model.addAttribute("bogennr", 1);
    return "adminOverview";
  }

  @GetMapping("/delete")
  public String deleteFrageById(Model model, Long frageId, Long formId) {
    frageboegen.deleteFrageByIdAndFrageId(formId, frageId);
    return creatForm(model, formId);
  }

  @PostMapping("/addTextFrage")
  public String addTextFrage(Model model, Long formId, TextFrage frage) {
    frageboegen.addTextFrage(formId,frage);
    return creatForm(model, formId);
  }

  @PostMapping("/addSkalarFrage")
  public String addSkalarFrage(Model model, Long formId, SkalarFrage frage) {
    frageboegen.addSkalarFrage(formId,frage);
    return creatForm(model, formId);
  }

  @GetMapping("/getFrageTyp")
  public String getFrageTyp(Model model, @RequestParam(value = "type") String type, Long id) {
    model.addAttribute("type", type);
    return creatForm(model, id);
  }

  /**
   * set the date and time of the feedback form.
   * @param formId Long formularId
   * @param startdate String startdate
   * @param enddate String enddate
   * @param start String startzeit
   * @param end String endzeit
   * @return
   */
  @GetMapping("/setDate")
  public String setDate(Long formId,String startdate,String enddate,String start,String end) {

    LocalDateTime startDate = dateTimeService.getLocalDateTimeFromString(startdate, start);
    LocalDateTime endDate = dateTimeService.getLocalDateTimeFromString(enddate, end);

    frageboegen.changeDateById(formId, startDate, endDate);

    String formatString = "redirect:/feedback/creatForm?id=%d";
    return String.format(formatString, formId);
  }

  /**
   * release Feedback form with success message.
   * @param model Model
   * @param formId Long
   * @return
   */
  @GetMapping("/releaseForm")
  public String releaseForm(Model model, @RequestParam(value = "formId") Long formId) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(formId);
    String start = dateTimeService.getGermanFormat(fragebogen.getStartdatum());
    String end = dateTimeService.getGermanFormat(fragebogen.getEnddatum());
    model.addAttribute("message", "Sie haben das Formular erfolgreich veröffentlich");
    model.addAttribute("date", "Start Zeit ist: " + start + " Endzeit ist :" + end);
    return creatForm(model, formId);
  }
}
