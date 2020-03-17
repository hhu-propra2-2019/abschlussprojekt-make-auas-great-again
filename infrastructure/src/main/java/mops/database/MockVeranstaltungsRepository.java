package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import mops.Veranstaltung;
import mops.controllers.VeranstaltungsRepository;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.springframework.stereotype.Repository;

@Repository
public class MockVeranstaltungsRepository implements VeranstaltungsRepository {
  private HashMap<Long, Veranstaltung> veranstaltungen = new HashMap<>();

  public MockVeranstaltungsRepository() {
    List<Veranstaltung> veranstaltungList = getRandomVeranstaltungen();
    Long index = 1L;
    for (Veranstaltung veranstaltung : veranstaltungList) {
      veranstaltungen.put(index, veranstaltung);
      index++;
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

  private Veranstaltung getRandomVeranstaltung() {
    Veranstaltung.VeranstaltungBuilder veranstaltung = Veranstaltung.builder();
    Dozent dozent = new Dozent(UUID.fromString("aa351f5c-b7fa-4bd9-ae76-8e5995b29889"), "jens", "B", null);
    veranstaltung = veranstaltung.dozent(dozent)
        .name("Programmierung")
        .semester("SOSE2019")
        .studenten(null);
    return veranstaltung.build();
  }

  private List<Veranstaltung> getRandomVeranstaltungen() {
    List<Veranstaltung> veranstaltungList = new ArrayList<>();
    Veranstaltung veranstaltung1 = getRandomVeranstaltung();
    Veranstaltung veranstaltung2 = getRandomVeranstaltung();
    Veranstaltung veranstaltung3 = getRandomVeranstaltung();
    veranstaltung2.setName("Theoretische  Informatik");
    veranstaltung3.setName("Analysis");
    veranstaltungList.add(veranstaltung1);
    veranstaltungList.add(veranstaltung2);
    veranstaltungList.add(veranstaltung3);
    return veranstaltungList;
  }
}
