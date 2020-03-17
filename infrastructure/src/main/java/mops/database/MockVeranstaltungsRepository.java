package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mops.Veranstaltung;
import mops.VeranstaltungsService;
import mops.controllers.VeranstaltungsRepository;
import mops.rollen.Student;
import org.springframework.stereotype.Repository;

@Repository
public class MockVeranstaltungsRepository implements VeranstaltungsRepository {
  private static transient Map<Long, Veranstaltung> veranstaltungen = new HashMap<>();
  private static transient VeranstaltungsService veranstaltungsService = new VeranstaltungsService();

  public MockVeranstaltungsRepository() {
    List<Veranstaltung> veranstaltungList = veranstaltungsService.randomVeranstaltungen();
    Long index = 0L;
    for (Veranstaltung veranstaltung : veranstaltungList) {
      veranstaltungen.put(index++, veranstaltung);
    }
  }

  @Override
  public List<Veranstaltung> getAll() {
    return new ArrayList<>(veranstaltungen.values());
  }

  @Override
  public List<Veranstaltung> getAllContaining(String search) {
    return getAll().stream()
        .filter(veranstaltung -> veranstaltung.contains(search))
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
}
