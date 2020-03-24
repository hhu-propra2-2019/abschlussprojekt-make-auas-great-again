package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mops.fragen.Auswahl;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage",
    "PMD.JUnitTestContainsTooManyAsserts"})
public class MultipleChoiceTest {
  private static final String MOEGLICHKEIT_4 = "Trifft gar nicht zu";
  private static final String MOEGLICHKEIT_3 = "Trifft eher nicht zu";
  private static final String MOEGLICHKEIT_2 = "Trifft zu";
  private static final String MOEGLICHKEIT_1 = "Trifft voll und ganz zu";
  private static final String MR_ANTWORT_1 = "Java";
  private static final String MR_ANTWORT_2 = "Python";
  private static final String MR_ANTWORT_3 = "C";
  private static final String MR_ANTWORT_4 = "PHP";
  private transient SingleResponseFrage singleResponseFrage;
  private transient MultipleResponseFrage multipleResponseFrage;


  @BeforeEach
  public void setUp() {
    singleResponseFrage = new SingleResponseFrage(1L, "Die Vorlesung ist strukturiert", false);
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_1));
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_2));
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_3));
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_4));

    multipleResponseFrage = new MultipleResponseFrage(2L,
        "Welche Programmiersprachen beherrscht du bereits?");
    multipleResponseFrage.addChoice(new Auswahl(MR_ANTWORT_1));
    multipleResponseFrage.addChoice(new Auswahl(MR_ANTWORT_2));
    multipleResponseFrage.addChoice(new Auswahl(MR_ANTWORT_3));
    multipleResponseFrage.addChoice(new Auswahl(MR_ANTWORT_4));
  }

  @Test
  @DisplayName("Wenn es nur eine Antwort gibt, ist die Option auf 100%?")
  public void eineAntwortBringtHundertProzent() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);

    final Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    final Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    final Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    final Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1, 100, 0.03);
    assertEquals(antwort2, 0, 0.03);
    assertEquals(antwort3, 0, 0.03);
    assertEquals(antwort4, 0, 0.03);
  }

  @Test
  @DisplayName("Wenn eine Option mehrfach gewählt wurde, ist diese als 100% ausgewertet?")
  public void zweiGleicheAntwortenBringtHundertProzent() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);

    final Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    final Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    final Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    final Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1, 100, 0.03);
    assertEquals(antwort2, 0, 0.03);
    assertEquals(antwort3, 0, 0.03);
    assertEquals(antwort4, 0, 0.03);
  }

  @Test
  @DisplayName("Sind die Prozentangaben für zwei Antworten jeweils 50%?")
  public void zweiVerschiedeneAntwortenBringtJeweilsFuenfzigProzent() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_2);

    final Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    final Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    final Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    final Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1, 50, 0.03);
    assertEquals(antwort2, 50, 0.03);
    assertEquals(antwort3, 0, 0.03);
    assertEquals(antwort4, 0, 0.03);
  }

  @Test
  @DisplayName("Wenn es keine Antworten gibt, sind die Prozente alle auf 0?")
  public void keineAntwortenBringenUeberallNullProzent() {
    final Double antwort1 = multipleResponseFrage.holeErgebnis(new Auswahl(MR_ANTWORT_1));
    final Double antwort2 = multipleResponseFrage.holeErgebnis(new Auswahl(MR_ANTWORT_2));
    final Double antwort3 = multipleResponseFrage.holeErgebnis(new Auswahl(MR_ANTWORT_3));
    final Double antwort4 = multipleResponseFrage.holeErgebnis(new Auswahl(MR_ANTWORT_4));

    assertEquals(0, antwort1, 0.03);
    assertEquals(0, antwort2, 0.03);
    assertEquals(0, antwort3, 0.03);
    assertEquals(0, antwort4, 0.03);
  }

  @Test
  @DisplayName("Sind drei Viertel der Antworten Option 3?")
  public void verhaeltnisDreiZuEins() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_3);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_3);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_3);

    final Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    final Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    final Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    final Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1, 25, 0.03);
    assertEquals(antwort2, 0, 0.03);
    assertEquals(antwort3, 75, 0.03);
    assertEquals(antwort4, 0, 0.03);
  }

  @Test
  @DisplayName("Ist die Zahl der Antwortmöglickeiten korrekt?")
  public void antwortMoeglichkeitenZahl() {
    assertEquals(4, singleResponseFrage.getNumberOfChoices());
  }

  @Test
  @DisplayName("Erhöht sich die Anzahl der Antwortmöglichkeiten nach dem Hinzufügen?")
  public void antwortMoeglichkeitHinzufuegen() {
    singleResponseFrage.addChoice(new Auswahl("Trifft überhaupt nicht zu"));

    assertEquals(5, singleResponseFrage.getNumberOfChoices());
  }

  @Test
  @DisplayName("Werden die Antworten einer MR-Frage korrekt gespeichert?")
  public void multipleResponseAntwortTest() {
    multipleResponseFrage.addAntwort(MR_ANTWORT_1);
  }
}
