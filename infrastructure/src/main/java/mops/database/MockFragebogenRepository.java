package mops.database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import mops.Frage;
import mops.Fragebogen;
import mops.TextFrage;
import mops.controllers.FragebogenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockFragebogenRepository implements FragebogenRepository {
  private transient List<String> fragen = new ArrayList<>(Arrays.asList("hey", "Yo", "Foo",
      "This", "Random", "Bar", "List", "Has", "Very Random", "Words", "And", "Stuff"));

  @Override
  public Fragebogen getFragebogenById(Long id) {
    List<Frage> fragenliste = new ArrayList<>();
    Frage frage = new TextFrage(getRandonFrageText());
    fragenliste.add(frage);
    Fragebogen.FragebogenBuilder fragebogen = Fragebogen.builder();
    fragebogen = fragebogen
        .startdatum(LocalDateTime.now())
        .enddatum(LocalDateTime.now().plusHours(24))
        .fragen(fragenliste)
        .professorenname("Jens Bendisposto")
        .veranstaltungsname("Softwareentwicklung im Team")
        .bogennr(id);
    return fragebogen.build();
  }

  private String getRandonFrageText() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(fragen.size());
    return fragen.get(index);
  }

  @Override
  public List<Fragebogen> getAll() {
    List<Fragebogen> fragenliste = new ArrayList<>();
    fragenliste.add(getFragebogenById(1L));
    fragenliste.add(getFragebogenById(2L));
    fragenliste.add(getFragebogenById(3L));
    return fragenliste;
  }
}
