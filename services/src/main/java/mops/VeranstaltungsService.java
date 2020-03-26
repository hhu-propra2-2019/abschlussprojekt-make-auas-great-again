package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mops.rollen.Dozent;
import mops.rollen.Student;

public class VeranstaltungsService {

  private static FragebogenService frageboegen = new FragebogenService();
  private static Random idGenerator = new Random();

  private Veranstaltung randomVeranstaltung() {
    Veranstaltung.VeranstaltungBuilder veranstaltung = Veranstaltung.builder();
    Dozent dozent = new Dozent("orga1", "jens", "Bendisposto");
    veranstaltung =
        veranstaltung.dozenten(List.of(dozent)).name("Programmierung").semester("SOSE2019")
        .studenten(randomStudenten()).frageboegen(frageboegen.randomFrageboegen(10))
        .veranstaltungsNr((long) idGenerator.nextInt(1000));
    return veranstaltung.build();
  }

  private List<Student> randomStudenten() {
    List<Student> studenten = new ArrayList<>();
    studenten.add(new Student("studentin"));
    for (int i = 0; i < 100; i++) {
      studenten.add(randomStudent());
    }
    return studenten;
  }

  private Student randomStudent() {
    return new Student("abldskajf");
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
