package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  private transient SingleResponseFrage singleResponseFrage;

  @BeforeEach
  public void setUp() {
    singleResponseFrage = new SingleResponseFrage(Long.valueOf(1), "Die Vorlesung ist strukturiert", false);
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_1));
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_2));
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_3));
    singleResponseFrage.addChoice(new Auswahl(MOEGLICHKEIT_4));
  }

  @Test
  @DisplayName("Wenn es nur eine Antwort gibt, ist die Option auf 100%?")
  public void eineAntwortBringtHundertProzent() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);

    Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 100, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  @DisplayName("Wenn eine Option mehrfach gewählt wurde, ist diese als 100% ausgewertet?")
  public void zweiGleicheAntwortenBringtHundertProzent() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);

    Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 100, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  @DisplayName("Sind die Prozentangaben für zwei Antworten jeweils 50%?")
  public void zweiVerschiedeneAntwortenBringtJeweilsFuenfzigProzent() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_2);

    Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 50, 0.03);
    assertEquals(antwort2.doubleValue(), 50, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  @DisplayName("Wenn es keine Antworten gibt, sind die Prozente alle auf 0?")
  public void keineAntwortenBringenUeberallNullProzent() {
    Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 0, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  @DisplayName("Sind drei Viertel der Antworten Option 3?")
  public void verhaeltnisDreiZuEins() {
    singleResponseFrage.addAntwort(MOEGLICHKEIT_1);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_3);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_3);
    singleResponseFrage.addAntwort(MOEGLICHKEIT_3);

    Double antwort1 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = singleResponseFrage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 25, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 75, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
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
}
