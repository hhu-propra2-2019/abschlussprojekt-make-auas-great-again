package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.VeranstaltungsService;
import mops.controllers.VeranstaltungsRepository;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.springframework.stereotype.Repository;

@Repository
public class MockVeranstaltungsRepository implements VeranstaltungsRepository {
  private static transient Map<Long, Veranstaltung> veranstaltungen = new HashMap<>();
  private static transient VeranstaltungsService veranstaltungsService =
      new VeranstaltungsService();

  public MockVeranstaltungsRepository() {
    List<Veranstaltung> veranstaltungList = veranstaltungsService.randomVeranstaltungen();
    for (Veranstaltung veranstaltung : veranstaltungList) {
      veranstaltungen.put(veranstaltung.getVeranstaltungsNr(), veranstaltung);
    }
  }

  @Override
  public void save(Veranstaltung veranstaltung) {
    veranstaltungen.put(veranstaltung.getVeranstaltungsNr(), veranstaltung);
  }

  @Override
  public List<Veranstaltung> getAll() {
    return new ArrayList<>(veranstaltungen.values());
  }

  @Override
  public List<Veranstaltung> getAllContaining(String search) {
    return getAll().stream().filter(veranstaltung -> veranstaltung.contains(search))
        .collect(Collectors.toList());
  }

  @Override
  public Veranstaltung getVeranstaltungById(Long id) {
    return veranstaltungen.get(id);
  }

  @Override
  public void addStudentToVeranstaltungById(Student student, Long verId) {
    Veranstaltung veranstaltung = getVeranstaltungById(verId);
    veranstaltung.addStudent(student);
  }

  @Override
  public List<Veranstaltung> getAllFromStudent(Student student) {
    return veranstaltungen.values().stream()
        .filter(veranstaltung -> veranstaltung.hasStudent(student)).collect(Collectors.toList());
  }

  @Override
  public List<Veranstaltung> getAllFromStudentContaining(Student student, String search) {
    return veranstaltungen.values().stream()
        .filter(veranstaltung -> veranstaltung.hasStudent(student))
        .filter(veranstaltung -> veranstaltung.contains(search)).collect(Collectors.toList());
  }

  @Override
  public List<Veranstaltung> getAllFromDozent(Dozent dozent) {
    return veranstaltungen.values().stream().filter(v -> v.getDozent().equals(dozent))
        .collect(Collectors.toList());
  }

  @Override
  public List<Veranstaltung> getAllFromDozentContaining(Dozent dozent, String suche) {
    return veranstaltungen.values().stream().filter(v -> v.getDozent().equals(dozent))
        .filter(v -> v.contains(suche)).collect(Collectors.toList());
  }

  @Override
  public Fragebogen getFragebogenFromDozentById(Long id, Dozent dozent) {
    List<Fragebogen> frageboegen = new ArrayList<>();
    getAllFromDozent(dozent).stream().forEach(x -> frageboegen.addAll(x.getFrageboegen()));
    return frageboegen.stream().filter(x -> x.getBogennr().equals(id)).findFirst().get();
  }

  @Override
  public Fragebogen getFragebogenByIdFromVeranstaltung(Long fragebogen, Long veranstaltung) {
    return veranstaltungen.get(veranstaltung).getFrageboegen().stream()
        .filter(bogen -> bogen.getBogennr().equals(fragebogen)).findFirst().get();
  }
}
