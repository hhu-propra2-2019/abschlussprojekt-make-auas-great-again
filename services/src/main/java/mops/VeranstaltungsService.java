package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import mops.rollen.Dozent;
import mops.rollen.Student;

public class VeranstaltungsService {

  private static FragebogenService frageboegen = new FragebogenService();
  private static Random idGenerator = new Random();

  private Veranstaltung randomVeranstaltung() {
    Veranstaltung.VeranstaltungBuilder veranstaltung = Veranstaltung.builder();
    Dozent dozent = new Dozent(UUID.fromString("aa351f5c-b7fa-4bd9-ae76-8e5995b29889"),
        "jens", "B", null);
    veranstaltung = veranstaltung.dozent(dozent)
        .name("Programmierung")
        .semester("SOSE2019")
        .studenten(randomStudenten())
        .frageboegen(frageboegen.randomFrageboegen(10))
        .veranstaltungsNr((long) idGenerator.nextInt(1000));
    return veranstaltung.build();
  }

  private List<Student> randomStudenten() {
    List<Student> studenten = new ArrayList<>();
    studenten.add(new Student(UUID.fromString("9de596a9-4077-4a4e-84ef-13d25450a15f")));
    for (int i = 0; i < 100; i++) {
      studenten.add(randomStudent());
    }
    return studenten;
  }

  private Student randomStudent() {
    return new Student(UUID.randomUUID());
  }

  public List<Veranstaltung> randomVeranstaltungen() {
    List<Veranstaltung> veranstaltungList = new ArrayList<>();
    Veranstaltung veranstaltung1 = randomVeranstaltung();
    Veranstaltung veranstaltung2 = randomVeranstaltung();
    Veranstaltung veranstaltung3 = randomVeranstaltung();
    veranstaltung2.setName("Theoretische  Informatik");
    veranstaltung3.setName("Analysis");
    veranstaltungList.add(veranstaltung1);
    veranstaltungList.add(veranstaltung2);
    veranstaltungList.add(veranstaltung3);
    return veranstaltungList;
  }
}
