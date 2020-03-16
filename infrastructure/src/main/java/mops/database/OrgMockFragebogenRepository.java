package mops.database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import mops.Einheit;
import mops.Fragebogen;
import mops.controllers.FragebogenRepository;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Org")
@SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.LooseCoupling"})
public class OrgMockFragebogenRepository implements FragebogenRepository {
  private final transient List<String> frage = new ArrayList<>(
      Arrays.asList("Was geht?", "Wie zufrieden sind sie mit dem Angebot?", "Random Question?"));

  private final transient List<String> professor = new ArrayList<>(
      Arrays.asList("Jens Bendisposto", "Christian Meter", "Jan Roßbach", "Luke Skywalker"));

  private final transient List<String> vorlesung =
      new ArrayList<>(Arrays.asList("Professioneller Softwareentwicklung im Team",
          "Lineare Algebra I", "Analysis II", "Theoretische Informatik", "Machine Learning"));


  private final transient List<String> uebung = new ArrayList<>(Arrays.asList("Uebung zur Linearen"
      + " Algebra", "Uebung zur Analysis", "Uebung zur Theoretischen Informatik", "Uebung zu "
      + "Machine Learning"));
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
  private transient HashMap<Long, Fragebogen> frageboegen;
  //simuliert Daten Bank Frage id
  private transient Long frageId = 0L;

  public OrgMockFragebogenRepository() {
    frageboegen = new HashMap<>();
    List<Fragebogen> fragebogenList = generateTenFragebogen();
    Long index = 1L;
    for (Fragebogen fragebogen : fragebogenList) {
      frageboegen.put(index, fragebogen);
      index++;
    }
  }

  public void deleteFrageByIdAndFrageId(Long formId, Long frageId) {
    Fragebogen fragebogen = frageboegen.get(formId);
    List<Frage> fragen = fragebogen.getFragen();
    fragen.removeIf(frage1 -> frage1.getId().equals(frageId));
    fragebogen.setFragen(fragen);
  }

  @Override
  public Fragebogen getFragebogenById(Long id) {
    return frageboegen.get(id);
  }

  private Fragebogen getRandomFragebogen() {
    List<Frage> fragenliste = new ArrayList<>();
    Frage frage1 = generateMultipleChoice();
    Frage frage2 = generateMultipleChoice();
    Frage frage3 = new TextFrage(3L, getRandomFrage());
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
    fragebogen = fragebogen
        .startdatum(LocalDateTime.now())
        .enddatum(LocalDateTime.now().plusHours(24))
        .fragen(fragenliste)
        .professorenname(getRandomProfessor())
        .veranstaltungsname(name)
        .type(einheit)
        .bogennr(1L);
    return fragebogen.build();
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
    return List.copyOf(frageboegen.values());
  }

  private List<Fragebogen> generateTenFragebogen() {
    List<Fragebogen> fragenliste = new ArrayList<>();
    for (long i = 1L; i < 10L; i++) {
      fragenliste.add(getRandomFragebogen());
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

  @Override
  public void newFragebogen(Fragebogen fragebogen) {

  }

  public void changeDateById(Long formId, LocalDateTime startDate, LocalDateTime endDate) {
    Fragebogen fragebogen = getFragebogenById(formId);
    fragebogen.setStartdatum(startDate);
    fragebogen.setEnddatum(endDate);
  }

  public void addTextFrage(Long id, TextFrage frage) {
    frageId++;
    frage.setId(frageId);
    Fragebogen fragebogen = getFragebogenById(id);
    List<Frage> fragen = fragebogen.getFragen();
    fragen.add(frage);
    fragebogen.setFragen(fragen);
  }

  public void addSkalarFrage(Long id, SingleResponseFrage frage) {
    frageId++;
    frage.setId(frageId);
    Fragebogen fragebogen = getFragebogenById(id);
    List<Frage> fragen = fragebogen.getFragen();
    fragen.add(frage);
    fragebogen.setFragen(fragen);
  }
}
