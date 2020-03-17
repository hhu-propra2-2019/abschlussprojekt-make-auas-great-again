package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mops.rollen.Dozent;

public class VeranstaltungsService {

  private FragebogenService frageboegen;

  public VeranstaltungsService() {
    frageboegen = new FragebogenService();
  }

  private Veranstaltung randomVeranstaltung() {
    Veranstaltung.VeranstaltungBuilder veranstaltung = Veranstaltung.builder();
    Dozent dozent = new Dozent(UUID.fromString("aa351f5c-b7fa-4bd9-ae76-8e5995b29889"), "jens", "B", null);
    veranstaltung = veranstaltung.dozent(dozent)
        .name("Programmierung")
        .semester("SOSE2019")
        .studenten(null)
        .frageboegen(frageboegen.randomFrageboegen(10));
    return veranstaltung.build();
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
