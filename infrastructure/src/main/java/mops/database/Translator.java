package mops.database;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import mops.Fragebogen;
import mops.FragebogenTemplate;
import mops.Veranstaltung;
import mops.antworten.Antwort;
import mops.antworten.MultipleChoiceAntwort;
import mops.antworten.TextAntwort;
import mops.database.dto.AntwortDto;
import mops.database.dto.AuswahlDto;
import mops.database.dto.DOrganisiertVDto;
import mops.database.dto.DozentDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.FragebogenTemplateDto;
import mops.database.dto.SBeantwortetFDto;
import mops.database.dto.SBelegtVDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Translator {

  private final transient StudentenJdbcRepository studentenRepo;
  private final transient DozentenJdbcRepository dozentenRepo;

  public Translator(StudentenJdbcRepository studentenRepo, DozentenJdbcRepository dozentenRepo) {
    this.studentenRepo = studentenRepo;
    this.dozentenRepo = dozentenRepo;
  }

  public Veranstaltung loadVeranstaltung(VeranstaltungDto dto) {
    Veranstaltung.VeranstaltungBuilder veranstaltung = Veranstaltung.builder();
    veranstaltung = veranstaltung
        .veranstaltungsNr(dto.getId())
        .frageboegen(loadFrageboegen(dto.getFrageboegen()))
        .name(dto.getName())
        .dozenten(loadDozenten(dto.getDozenten()))
        .semester(dto.getSemester())
        .studenten(loadStudentenList(dto.getStudenten()));
    return veranstaltung.build();
  }

  private List<Dozent> loadDozenten(Set<DOrganisiertVDto> dozenten) {
    return dozenten.stream().map(this::loadDozentFromOrganisiert).collect(Collectors.toList());
  }

  private Dozent loadDozentFromOrganisiert(DOrganisiertVDto dOrganisiertVDto) {
    Optional<DozentDto> dozentDto = dozentenRepo.findById(dOrganisiertVDto.getDozent());
    if (dozentDto.isPresent()) {
      return loadDozent(dozentDto.get());
    }
    throw new DataSourceLookupFailureException("Dozent nicht gefunden");
  }

  private List<Fragebogen> loadFrageboegen(Set<FragebogenDto> frageboegen) {
    return frageboegen.stream()
        .map(this::loadFragebogen)
        .collect(Collectors.toList());
  }

  Fragebogen loadFragebogen(FragebogenDto fragebogenDto) {
    Fragebogen.FragebogenBuilder fragebogen = Fragebogen.builder();
    return fragebogen
        .type(fragebogenDto.getEinheit())
        .startdatum(fragebogenDto.getStartzeit())
        .enddatum(fragebogenDto.getEndzeit())
        .abgegebeneStudierende(loadStudenten(fragebogenDto.getBearbeitetVon()))
        .bogennr(fragebogenDto.getId())
        .name(fragebogenDto.getName())
        .fragen(loadFragen(fragebogenDto.getFragen()))
        .build();
  }

  private List<Student> loadStudentenList(Set<SBelegtVDto> studenten) {
    return studenten.stream()
        .map(x -> studentenRepo.findById(x.getStudent()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(this::loadStudent)
        .collect(Collectors.toList());
  }

  private List<Student> loadStudenten(Set<SBeantwortetFDto> sbeantwortetFDtos) {
    return sbeantwortetFDtos.stream()
        .map(x -> studentenRepo.findById(x.getStudent()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(this::loadStudent)
        .collect(Collectors.toList());
  }

  private Student loadStudent(StudentDto studentDto) {
    return new Student(studentDto.getUsername());
  }

  private List<Frage> loadFragen(Set<FrageDto> fragen) {
    return fragen.stream()
        .map(frage -> loadFrage(frage))
        .collect(Collectors.toList());
  }

  private Frage loadFrage(FrageDto frage) {
    if (frage.isMultipleResponseFrage() || frage.isSingleResponseFrage()) {
      return loadMultipleChoiceFrage(frage);
    }
    return loadTextFrage(frage);
  }


  private TextFrage loadTextFrage(FrageDto frage) {
    TextFrage neueFrage = new TextFrage(frage.getId(), frage.getFragetext());
    for (AntwortDto dto : frage.getAntworten()) {
      neueFrage.addAntwort(loadTextAntwort(dto));
    }
    return neueFrage;
  }

  private TextAntwort loadTextAntwort(AntwortDto dto) {
    return new TextAntwort(dto.getId(), dto.getTextantwort());
  }

  private MultipleChoiceFrage loadMultipleChoiceFrage(FrageDto frage) {
    MultipleChoiceFrage neueFrage = new MultipleChoiceFrage(frage.getId(),
        frage.getFragetext());
    for (AntwortDto antwortDto : frage.getAntworten()) {
      neueFrage.addAntwort(loadMultipleChoiceAntwort(antwortDto));
    }
    for (AuswahlDto auswahlDto : frage.getAuswaehlbar()) {
      neueFrage.addChoice(loadAuswahl(auswahlDto));
    }
    return neueFrage;
  }


  private Auswahl loadAuswahl(AuswahlDto auswahlDto) {
    return new Auswahl(auswahlDto.getId(), auswahlDto.getAuswahltext());
  }

  private MultipleChoiceAntwort loadMultipleChoiceAntwort(AntwortDto antwortDto) {
    MultipleChoiceAntwort antwort = new MultipleChoiceAntwort(antwortDto.getId());
    antwort.addAntworten(loadAusgewählt(antwortDto.getAusgewaehlt()));
    return antwort;
  }

  private Set<Auswahl> loadAusgewählt(Set<AuswahlDto> ausgewaehlt) {
    return ausgewaehlt.stream().map(this::loadAuswahl).collect(Collectors.toSet());
  }

  public VeranstaltungDto createVeranstaltungDto(Veranstaltung veranstaltung) {
    VeranstaltungDto veranstaltungDto = VeranstaltungDto.create(veranstaltung.getName(),
        veranstaltung.getSemester());
    veranstaltungDto.setDozenten(veranstaltung.getDozenten().stream()
        .map(this::createDOrganisiertVDto)
        .collect(Collectors.toSet()));
    veranstaltungDto.setStudenten(veranstaltung.getStudenten().stream()
        .map(this::createSBelegtVDto)
        .collect(Collectors.toSet()));
    veranstaltungDto.setFrageboegen(veranstaltung.getFrageboegen().stream()
        .map(this::createFragebogenDto)
        .collect(Collectors.toSet()));
    return veranstaltungDto;
  }

  private DOrganisiertVDto createDOrganisiertVDto(Dozent dozent) {
    return new DOrganisiertVDto(dozentenRepo.findId(dozent.getUsername()));
  }

  private FragebogenDto createFragebogenDto(Fragebogen fragebogen) {
    FragebogenDto fragebogenDto = FragebogenDto.create(fragebogen.getName(), fragebogen.getType(),
        fragebogen.getStartdatum().toString(),
        fragebogen.getEnddatum().toString());
    for (Student student : fragebogen.getAbgegebeneStudierende()) {
      fragebogenDto.addBeantwortetVonStudent(createStudentDto(student));
    }
    for (Frage frage : fragebogen.getFragen()) {
      fragebogenDto.addFrage(createFrageDto(frage));
    }
    return fragebogenDto;
  }

  private FrageDto createFrageDto(Frage frage) {
    if (frage instanceof TextFrage) {
      return createTextFrageDto((TextFrage) frage);
    }
    return null;
  }

  private StudentDto createStudentDto(Student student) {
    return StudentDto.create(student.getUsername());
  }

  private SBelegtVDto createSBelegtVDto(Student student) {
    return new SBelegtVDto(studentenRepo.findId(student.getUsername()));
  }

  private FrageDto createTextFrageDto(TextFrage frage) {
    FrageDto frageDto = FrageDto.createTextfrage(frage.toString());
    for (Antwort antwort : frage.getAntworten()) {
      frageDto.addAnwort(createTextAntwortDto((TextAntwort) antwort));
    }
    return frageDto;
  }

  private AntwortDto createTextAntwortDto(TextAntwort antwort) {
    // TODO
    return null;
  }

  Dozent loadDozent(DozentDto dozentDto) {
    List<FragebogenTemplate> templates = loadTemplates(dozentDto.getFragebogenTemplates());
    Dozent dozent = new Dozent(dozentDto.getUsername(), dozentDto.getNachname(),
        dozentDto.getVorname(), templates);
    return dozent;
  }

  private List<FragebogenTemplate> loadTemplates
      (Set<FragebogenTemplateDto> fragebogenTemplates) {
    return fragebogenTemplates.stream().map(this::loadTemplate).collect(Collectors.toList());
  }

  private FragebogenTemplate loadTemplate(FragebogenTemplateDto fragebogenTemplateDto) {
    return new FragebogenTemplate(fragebogenTemplateDto.getId(), fragebogenTemplateDto.getName(),
        loadFragen(fragebogenTemplateDto.getFragen()));
  }
}