package mops.database.repos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mops.Einheit;
import mops.Fragebogen;
import mops.FragebogenTemplate;
import mops.Veranstaltung;
import mops.antworten.Antwort;
import mops.antworten.MultipleChoiceAntwort;
import mops.antworten.TextAntwort;
import mops.database.dto.AntwortDto;
import mops.database.dto.AuswahlDto;
import mops.database.dto.DozentDto;
import mops.database.dto.DozentOrganisiertVeranstaltungDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.FragebogenTemplateDto;
import mops.database.dto.StudentBeantwortetFragebogenDto;
import mops.database.dto.StudentBelegtVeranstaltungDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;
import mops.rollen.Dozent;
import mops.rollen.Student;

@Service
@AllArgsConstructor
class TranslationService {
  private final DozentRepository dozentrepo;
  private final StudentRepository studentrepo;

  public Dozent load(DozentDto dto) {
    String username = dto.getUsername();
    String vorname = dto.getVorname();
    String nachname = dto.getNachname();
    List<FragebogenTemplate> templates =
        dto.getTemplates().stream().map(this::load).collect(Collectors.toList());
    return new Dozent(username, vorname, nachname, templates);
  }

  public Fragebogen load(FragebogenDto dto) {
    Long bogennr = dto.getId();
    String name = dto.getName();
    List<Frage> fragen = dto.getFragen().stream().map(this::load).collect(Collectors.toList());
    LocalDateTime startdatum = parseDateTime(dto.getStartzeit());
    LocalDateTime enddatum = parseDateTime(dto.getEndzeit());
    Einheit einheit = dto.getEinheit();
    List<Student> bearbeitet = loadBeantworter(dto.getBearbeitet());
    return new Fragebogen(bogennr, name, fragen, startdatum, enddatum, einheit, bearbeitet);
  }
  
  private LocalDateTime parseDateTime(String timestamp) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    return LocalDateTime.parse(timestamp, formatter);
  }
  
  public Student load(StudentDto dto) {
    String username = dto.getUsername();
    return new Student(username);
  }

  public Veranstaltung load(VeranstaltungDto dto) {
    Long id = dto.getId();
    String name = dto.getName();
    String semester = dto.getSemester();
    List<Fragebogen> frageboegen =
        dto.getFrageboegen().stream().map(this::load).collect(Collectors.toList());
    List<Dozent> dozenten = loadOrganisatoren(dto.getOrganisiert());
    List<Student> studenten = loadBeleger(dto.getBelegt());
    return new Veranstaltung(id, name, semester, dozenten, studenten, frageboegen);
  }

  public FragebogenTemplate load(FragebogenTemplateDto dto) {
    Long id = dto.getId();
    String name = dto.getName();
    List<Frage> fragen = dto.getFragen().stream().map(this::load).collect(Collectors.toList());
    return new FragebogenTemplate(id, name, fragen);
  }
  
  public VeranstaltungDto unload(Veranstaltung obj) {
    Long id = obj.getVeranstaltungsNr();
    String name = obj.getName();
    String semester = obj.getSemester();
    Set<FragebogenDto> frageboegen = obj.getFrageboegen().stream().map(this::unload)
        .collect(Collectors.toSet());
    Set<StudentBelegtVeranstaltungDto> sbv = obj.getStudenten().stream().map(this::unloadSbV)
        .collect(Collectors.toSet());
    Set<DozentOrganisiertVeranstaltungDto> dov = obj.getDozenten().stream().map(this::unloadDoV)
        .collect(Collectors.toSet());
    return new VeranstaltungDto(id, name, semester, frageboegen, sbv, dov);
  }
  
  private StudentBelegtVeranstaltungDto unloadSbV(Student obj) {
    return new StudentBelegtVeranstaltungDto(obj.getUsername());
  }
  
  private DozentOrganisiertVeranstaltungDto unloadDoV(Dozent obj) {
    return new DozentOrganisiertVeranstaltungDto(obj.getUsername());
  }
  
  public FragebogenDto unload(Fragebogen obj) {
    Long id = obj.getBogennr();
    String name = obj.getName();
    Set<FrageDto> fragen = obj.getFragen().stream().map(this::unload).collect(Collectors.toSet());
    String startdatum = formatDateTime(obj.getStartdatum());
    String enddatum = formatDateTime(obj.getEnddatum());
    Einheit einheit = obj.getType();
    Set<StudentBeantwortetFragebogenDto> sbf = obj.getAbgegebeneStudierende().stream()
        .map(this::unloadSbF).collect(Collectors.toSet());
    return new FragebogenDto(id, name, startdatum, enddatum, einheit, fragen, sbf);
  }
  
  private String formatDateTime(LocalDateTime obj) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    return obj.format(formatter);
  }
  
  private StudentBeantwortetFragebogenDto unloadSbF(Student obj) {
    return new StudentBeantwortetFragebogenDto(obj.getUsername());
  }
  
  public DozentDto unload(Dozent obj) {
    String username = obj.getUsername();
    String vorname = obj.getVorname();
    String nachname = obj.getNachname();
    Set<FragebogenTemplateDto> templates = obj.getTemplates().stream().map(this::unload)
        .collect(Collectors.toSet());
    return new DozentDto(username, vorname, nachname, templates);
  }
  
  private FragebogenTemplateDto unload(FragebogenTemplate obj) {
    Long id = obj.getId();
    String name = obj.getName();
    Set<FrageDto> fragen = obj.getFragen().stream().map(this::unload).collect(Collectors.toSet());
    return new FragebogenTemplateDto(id, name, fragen);
  }
  
  private FrageDto unload(Frage obj) {
    Long id = obj.getId();
    String fragetext = obj.toString();
    Boolean oeffentlich = obj.isOeffentlich();
    Set<AntwortDto> antworten = obj.getAntworten().stream().map(this::unload)
        .collect(Collectors.toSet());
    if (obj instanceof MultipleChoiceFrage) {
      Set<AuswahlDto> auswahl = ((MultipleChoiceFrage) obj).getChoices().stream()
          .map(this::unload).collect(Collectors.toSet());
      if (obj instanceof SingleResponseFrage) {
        return new FrageDto(id, oeffentlich, 2L, fragetext, antworten, auswahl);
      } else {
        return new FrageDto(id, oeffentlich, 3L, fragetext, antworten, auswahl);
      }
    }
    return new FrageDto(id, oeffentlich, 1L, fragetext, antworten, new HashSet<>());
  }
  
  private AntwortDto unload(Antwort obj) {
    Long id = obj.getId();
    if (obj instanceof MultipleChoiceAntwort) {
      Set<AuswahlDto> auswahl = ((MultipleChoiceAntwort) obj).getGewaehlteAntworten().stream()
          .map(this::unload).collect(Collectors.toSet());
      return new AntwortDto(id, "", auswahl);
    }
    String text = obj.toString();
    return new AntwortDto(id, text, new HashSet<>());
  }
  
  private AuswahlDto unload(Auswahl obj) {
    Long id = obj.getId();
    String label = obj.getLabel();
    return new AuswahlDto(id, label);
  }
  
  private Antwort load(AntwortDto dto) {
    Long id = dto.getId();
    if (dto.isTextAntwort()) {
      String textantwort = dto.getTextantwort();
      return new TextAntwort(id, textantwort);
    }
    List<Auswahl> choices = dto.getAuswahlen().stream().map(this::load)
        .collect(Collectors.toList());
    return new MultipleChoiceAntwort(id, choices);
  }

  private Auswahl load(AuswahlDto dto) {
    Long id = dto.getId();
    String auswahltext = dto.getAuswahltext();
    return new Auswahl(id, auswahltext);
  }

  private Frage load(FrageDto dto) {
    Long id = dto.getId();
    String fragetext = dto.getFragetext();
    List<Antwort> antworten = dto.getAntworten().stream().map(this::load)
        .collect(Collectors.toList());
    if (dto.isTextFrage()) {
      return new TextFrage(id, fragetext, antworten);
    }
    List<Auswahl> choices = dto.getAuswahlen().stream().map(this::load)
        .collect(Collectors.toList());
    if (dto.isMultipleResponse()) {
      return new MultipleResponseFrage(id, fragetext, choices, antworten);
    }
    return new SingleResponseFrage(id, fragetext, choices, antworten);
  }

  private List<Dozent> loadOrganisatoren(Set<DozentOrganisiertVeranstaltungDto> dtos) {
    return dtos.stream().map(this::getDozentDtoFromOrganisiert).map(this::load)
        .collect(Collectors.toList());
  }

  private DozentDto getDozentDtoFromOrganisiert(DozentOrganisiertVeranstaltungDto dto) {
    return dozentrepo.findById(dto.getDozent()).get();
  }

  private List<Student> loadBeantworter(Set<StudentBeantwortetFragebogenDto> dtos) {
    return dtos.stream().map(this::getStudentDtoFromBeantwortet).map(this::load)
        .collect(Collectors.toList());
  }
  
  private StudentDto getStudentDtoFromBeantwortet(StudentBeantwortetFragebogenDto dto) {
    return studentrepo.findById(dto.getStudent()).get();
  }

  private List<Student> loadBeleger(Set<StudentBelegtVeranstaltungDto> dtos) {
    return dtos.stream().map(this::getStudentDtoFromBelegt).map(this::load)
        .collect(Collectors.toList());
  }
  
  private StudentDto getStudentDtoFromBelegt(StudentBelegtVeranstaltungDto dto) {
    return studentrepo.findById(dto.getStudent()).get();
  }
}
