package mops.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.database.dto.VeranstaltungDto;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;

@Repository
public class VeranstaltungsRepository implements mops.controllers.VeranstaltungsRepository {
  private transient VeranstaltungJdbcRepository veranstaltungenRepo;
  private transient Translator translator;

  public VeranstaltungsRepository(Translator translator,
                                  VeranstaltungJdbcRepository veranstaltungenRepo) {
    this.veranstaltungenRepo = veranstaltungenRepo;
    this.translator = translator;
  }

  @Override
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public List<Veranstaltung> getAll() {
    Iterable<VeranstaltungDto> veranstaltungenDtos = veranstaltungenRepo.findAll();
    List<Veranstaltung> veranstaltungen = new ArrayList<>();
    for (VeranstaltungDto dto : veranstaltungenDtos) {
      veranstaltungen.add(translator.loadVeranstaltung(dto));
    }
    return veranstaltungen;
  }

  @Override
  public List<Veranstaltung> getAllContaining(String search) {
    return getAll().stream()
        .filter(veranstaltung -> veranstaltung.contains(search))
        .collect(Collectors.toList());
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
    VeranstaltungDto veranstaltungDto = translator.createVeranstaltungDto(veranstaltung);
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
    // TODO Replace with SQL QUERY
    return getAll().stream()
        .filter(ver -> ver.hasStudent(student))
        .collect(Collectors.toList());
  }

  @Override
  public List<Veranstaltung> getAllFromStudentContaining(Student student, String search) {
    // TODO Replace with SQL QUERY
    return getAll().stream()
        .filter(ver -> ver.hasStudent(student))
        .filter(ver -> ver.contains(search))
        .collect(Collectors.toList());
  }

  @Override
  public List<Veranstaltung> getAllFromDozent(Dozent dozent) {
    // TODO Replace with SQL QUERY
    return getAll().stream()
        .filter(ver -> ver.hasDozent(dozent))
        .collect(Collectors.toList());
  }

  @Override
  public List<Veranstaltung> getAllFromDozentContaining(Dozent dozent, String suche) {
    // TODO Replace with SQL QUERY
    return getAll().stream()
        .filter(ver -> ver.hasDozent(dozent))
        .filter(ver -> ver.contains(suche))
        .collect(Collectors.toList());
  }

  @Override
  public Fragebogen getFragebogenFromDozentById(Long fragebogen, Dozent dozent) {
    // TODO SQL QUERY
    return null;
  }

  @Override
  public Fragebogen getFragebogenByIdFromVeranstaltung(Long fragebogen, Long veranstaltung) {
    // TODO SQL QUERY
    return null;
  }
}
