package mops.database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import mops.Auswahl;
import mops.Einheit;
import mops.Frage;
import mops.Fragebogen;
import mops.MultipleChoiceFrage;
import mops.TextFrage;
import mops.controllers.FragebogenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockFragebogenRepository implements FragebogenRepository {
  private final transient List<String> frage = new ArrayList<>(
      Arrays.asList("Was geht?", "Wie zufrieden sind sie mit dem Angebot?", "Random Question?"));

  private final transient List<String> professor = new ArrayList<>(
      Arrays.asList("Jens Bendisposto", "Christian Meter", "Jan Roßbach", "Luke Skywalker"));

  private final transient List<String> vorlesung =
      new ArrayList<>(Arrays.asList("Professioneller Softwareentwicklung im Team",
          "Lineare Algebra I", "Analysis II", "Theoretische Informatik", "Machine Learning"));

  private final transient List<String> uebung =
      new ArrayList<>(Arrays.asList("Uebung zur Linearen" + " Algebra", "Uebung zur Analysis",
          "Uebung zur Theoretischen Informatik", "Uebung Machine Learning"));

  private final transient List<String> aufgabe =
      new ArrayList<>(Arrays.asList("Aufgabe zur Linearen Algebra", "Abschlussaufgabe Datenbanken",
          "Aufgabe 13 Analysis", "Theoretische Informatik Blatt 15",
          "Machine Learning Programmieraufgabe"));

  private final transient List<String> gruppe =
      new ArrayList<>(Arrays.asList("Gruppe 15", "Gruppe 4"));

  private final transient List<String> dozent =
      new ArrayList<>(Arrays.asList("Jens Bendisposto", "Christian Meter"));

  private final transient List<String> beratung =
      new ArrayList<>(Arrays.asList("Studienberatung", "Rückmeldungen"));

  private final transient List<String> praktikum = new ArrayList<>(Arrays
      .asList("Praktikum Hardwarenahe Programmierung", "Softwarentwicklung im Team Praktikum"));

  private final transient Random idgenerator = new Random();

  // static, da beide Controller gleiche Datenbank brauchen
  private static final Map<Long, Fragebogen> altefrageboegen = new HashMap<>();

  @Override
  public Fragebogen getFragebogenById(Long id) {
    if (altefrageboegen.containsKey(id)) {
      return altefrageboegen.get(id);
    } else {
      List<Frage> fragenliste = new ArrayList<>();
      Frage frage1 = generateMultipleChoice();
      Frage frage2 = generateMultipleChoice();
      Frage frage3 = new TextFrage(Long.valueOf(idgenerator.nextInt(100)), getRandomFrage());
      fragenliste.add(frage1);
      fragenliste.add(frage2);
      fragenliste.add(frage3);
      Einheit einheit = Einheit.getRandomEinheit();
      String name;
      if (einheit == Einheit.VORLESUNG) {
        name = getRandomVorlesung();
      } else if (einheit == Einheit.UEBUNG) {
        name = getRandomUebung();
      } else if (einheit == Einheit.GRUPPE) {
        name = getRandomGruppe();
      } else if (einheit == Einheit.DOZENT) {
        name = getRandomDozent();
      } else if (einheit == Einheit.PRAKTIKUM) {
        name = getRandomPraktikum();
      } else if (einheit == Einheit.AUFGABE) {
        name = getRandomAufgabe();
      } else {
        name = getRandomBeratung();
      }
      Fragebogen.FragebogenBuilder fragebogen = Fragebogen.builder();
      fragebogen = fragebogen.startdatum(LocalDateTime.now())
          .enddatum(LocalDateTime.now().plusHours(24)).fragen(fragenliste)
          .professorenname(getRandomProfessor()).veranstaltungsname(name).type(einheit).bogennr(id);
      Fragebogen result = fragebogen.build();
      altefrageboegen.put(id, result);
      return result;
    }
  }

  private Frage generateMultipleChoice() {
    MultipleChoiceFrage frage =
        new MultipleChoiceFrage(Long.valueOf(idgenerator.nextInt(100)), getRandomFrage(), false);
    frage.addChoice(new Auswahl("1"));
    frage.addChoice(new Auswahl("2"));
    frage.addChoice(new Auswahl("3"));
    frage.addChoice(new Auswahl("4"));
    frage.addChoice(new Auswahl("5"));
    return frage;
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

  private String getRandomAufgabe() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(aufgabe.size());
    return aufgabe.get(index);
  }

  private String getRandomGruppe() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(gruppe.size());
    return gruppe.get(index);
  }

  private String getRandomDozent() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(dozent.size());
    return dozent.get(index);
  }

  private String getRandomBeratung() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(beratung.size());
    return beratung.get(index);
  }

  private String getRandomPraktikum() {
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(praktikum.size());
    return praktikum.get(index);
  }

  @Override
  public List<Fragebogen> getAll() {
    List<Fragebogen> fragenliste = new ArrayList<>();
    for (long i = 1L; i < 10L; i++) {
      fragenliste.add(getFragebogenById(i));
    }
    return fragenliste;
  }

  @Override
  public List<Fragebogen> getAllContaining(String search) {
    List<Fragebogen> fragenliste = new ArrayList<>();
    for (long i = 1L; i < 10L; i++) {
      Fragebogen fragebogen = getFragebogenById(i);
      if (fragebogen.contains(search)) {
        fragenliste.add(fragebogen);
      }
    }
    return fragenliste;
  }
}
