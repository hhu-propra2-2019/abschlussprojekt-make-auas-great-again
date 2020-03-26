package mops.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.database.dto.FragebogenDto;
import mops.database.dto.VeranstaltungDto;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;

@Repository
public class VeranstaltungsRepository implements mops.controllers.VeranstaltungsRepository {
  private final transient DozentenJdbcRepository dozentenRepo;
  private final transient StudentenJdbcRepository studentenRepo;
  private transient VeranstaltungJdbcRepository veranstaltungenRepo;
  private transient FragebogenJdbcRepository fragebogenRepo;
  private transient Translator translator;

  public VeranstaltungsRepository(Translator translator,
                                  VeranstaltungJdbcRepository veranstaltungenRepo,
                                  DozentenJdbcRepository dozentenRepo,
                                  StudentenJdbcRepository studentenRepo,
                                  FragebogenJdbcRepository fragebogenRepo) {
    this.veranstaltungenRepo = veranstaltungenRepo;
    this.translator = translator;
    this.dozentenRepo = dozentenRepo;
    this.studentenRepo = studentenRepo;
    this.fragebogenRepo = fragebogenRepo;
  }

  @Override
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public List<Veranstaltung> getAll() {
    return loadVeranstaltungen(veranstaltungenRepo.findAll());
  }

  @Override
  public List<Veranstaltung> getAllContaining(String search) {
    return loadVeranstaltungen(veranstaltungenRepo.getAllContaining(search));
  }


  @Override
  public Veranstaltung getVeranstaltungById(Long id) {
    Optional<VeranstaltungDto> veranstaltungDto = veranstaltungenRepo.findById(id);
    if (veranstaltungDto.isPresent()) {
      return translator.loadVeranstaltung(veranstaltungDto.get());
    } else {
      throw new DataSourceLookupFailureException("Veranstaltung nicht gefunden");
    }
  }

  @Override
  public void save(Veranstaltung veranstaltung) {
    VeranstaltungDto veranstaltungDto;
    if (veranstaltung.getVeranstaltungsNr() == null) {
      veranstaltungDto = translator.createVeranstaltungDto(veranstaltung);
    } else {
      veranstaltungDto = translator.loadVeranstaltungDto(veranstaltung);
    }
    veranstaltungenRepo.save(veranstaltungDto);
  }

  @Override
  public void addStudentToVeranstaltungById(Student student, Long verId) {
    Veranstaltung veranstaltung = getVeranstaltungById(verId);
    veranstaltung.addStudent(student);
    save(veranstaltung);
  }

  @Override
  public List<Veranstaltung> getAllFromStudent(Student student) {
    Long id = findStudentId(student);
    return loadVeranstaltungen(veranstaltungenRepo.getAllFromStudent(id));
  }

  @Override
  public List<Veranstaltung> getAllFromStudentContaining(Student student, String search) {
    Long id = findStudentId(student);
    return loadVeranstaltungen(veranstaltungenRepo.getAllFromStudentContaining(id, search));
  }

  @Override
  public List<Veranstaltung> getAllFromDozent(Dozent dozent) {
    Long id = findDozentenId(dozent);
    return loadVeranstaltungen(veranstaltungenRepo.getAllFromDozent(id));
  }

  @Override
  public List<Veranstaltung> getAllFromDozentContaining(Dozent dozent, String suche) {
    Long id = findDozentenId(dozent);
    return loadVeranstaltungen(veranstaltungenRepo.getAllFromDozentContaining(id, suche));
  }

  @Override
  public Fragebogen getFragebogenFromDozentById(Long fragebogen, Dozent dozent) {
    Optional<FragebogenDto> fragebogenDto = fragebogenRepo.findById(fragebogen);
    return fragebogenDto.map(dto -> translator.loadFragebogen(dto)).orElse(null);
  }

  @Override
  public Fragebogen getFragebogenByIdFromVeranstaltung(Long fragebogen, Long veranstaltung) {
    Optional<FragebogenDto> fragebogenDto = fragebogenRepo.findById(fragebogen);
    return fragebogenDto.map(dto -> translator.loadFragebogen(dto)).orElse(null);
  }

  @Override
  public void saveToVeranstaltung(Fragebogen fragebogen, Long veranstaltungsId) {

    Optional<VeranstaltungDto> dto = veranstaltungenRepo.findById(veranstaltungsId);
    if (dto.isPresent()) {
      Veranstaltung veranstaltung = translator.loadVeranstaltung(dto.get());
      veranstaltung.updateFragebogen(fragebogen);
      save(veranstaltung);
    } else {
      throw new DataSourceLookupFailureException("Veranstaltung nicht gefunden");
    }
  }

  @Override
  public Dozent getDozentByUsername(String name) {
    return translator.loadDozent(dozentenRepo.getDozentByUsername(name));
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  private List<Veranstaltung> loadVeranstaltungen(Iterable<VeranstaltungDto> veranstaltungDtos) {
    List<Veranstaltung> veranstaltungen = new ArrayList<>();
    for (VeranstaltungDto dto : veranstaltungDtos) {
      veranstaltungen.add(translator.loadVeranstaltung(dto));
    }
    return veranstaltungen;
  }

  private Long findStudentId(Student student) {
    return studentenRepo.findId(student.getUsername());
  }

  private Long findDozentenId(Dozent dozent) {
    return dozentenRepo.findId(dozent.getUsername());
  }
}
