package mops.database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import mops.Einheit;
import mops.Frage;
import mops.Fragebogen;
import mops.MultipleChoiceFrage;
import mops.TextFrage;
import mops.controllers.FragebogenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockFragebogenRepository implements FragebogenRepository {
  private final transient List<String> frage = new ArrayList<>(Arrays.asList("Wie geht's?",
      "Was hattest du zum Frühstück?", "Was ist dein Geschlecht?"));

  private final transient List<String> professor = new ArrayList<>(Arrays.asList("Jens Bendisposto",
      "Christian Meter", "Jan Roßbach", "Luke Skywalker"));

  private final transient List<String> vorlesung = new ArrayList<>(Arrays.asList(
      "Professioneller Softwareentwicklung im Team", "Lineare Algebra I", "Analysis II",
      "Theoretische Informatik", "Machine Learning"));

  private final transient List<String> uebung = new ArrayList<>(Arrays.asList("Uebung zur Linearen"
      + " Algebra", "Uebung zur Analysis", "Uebung zur Theoretischen Informatik", "Uebung zu "
      + "Machine Learning"));

  @Override
  public Fragebogen getFragebogenById(Long id) {
    List<Frage> fragenliste = new ArrayList<>();
    Frage frage1 = new MultipleChoiceFrage(1L, getRandomFrage());
    Frage frage2 = new MultipleChoiceFrage(2L, getRandomFrage());
    Frage frage3 = new TextFrage(3L, getRandomFrage());
    fragenliste.add(frage1);
    fragenliste.add(frage2);
    fragenliste.add(frage3);
    Einheit einheit = Einheit.getRandomEinheit();
    String name;
    if (einheit == Einheit.VORLESUNG) {
      name = getRandomVorlesung();
    } else {
      name = getRandomUebung();
    }
    Fragebogen.FragebogenBuilder fragebogen = Fragebogen.builder();
    fragebogen = fragebogen
        .startdatum(LocalDateTime.now())
        .enddatum(LocalDateTime.now().plusHours(24))
        .fragen(fragenliste)
        .professorenname(getRandomProfessor())
        .veranstaltungsname(name)
        .type(einheit)
        .bogennr(id);
    return fragebogen.build();
  }

  private String getRandomVorlesung() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(vorlesung.size());
    return vorlesung.get(index);
  }

  private String getRandomProfessor() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(professor.size());
    return professor.get(index);
  }

  private String getRandomFrage() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(frage.size());
    return frage.get(index);
  }

  private String getRandomUebung() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(uebung.size());
    return uebung.get(index);
  }

  @Override
  public List<Fragebogen> getAll() {
    List<Fragebogen> fragenliste = new ArrayList<>();
    fragenliste.add(getFragebogenById(1L));
    fragenliste.add(getFragebogenById(2L));
    fragenliste.add(getFragebogenById(3L));
    fragenliste.add(getFragebogenById(4L));
    fragenliste.add(getFragebogenById(5L));
    fragenliste.add(getFragebogenById(6L));
    fragenliste.add(getFragebogenById(7L));
    return fragenliste;
  }
}
