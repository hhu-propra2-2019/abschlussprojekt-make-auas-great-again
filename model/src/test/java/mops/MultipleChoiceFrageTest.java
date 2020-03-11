package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage",
    "PMD.JUnitTestContainsTooManyAsserts"})
public class MultipleChoiceFrageTest {
  private static final String MOEGLICHKEIT_4 = "Trifft gar nicht zu";
  private static final String MOEGLICHKEIT_3 = "Trifft eher nicht zu";
  private static final String MOEGLICHKEIT_2 = "Trifft zu";
  private static final String MOEGLICHKEIT_1 = "Trifft voll und ganz zu";
  private transient MultipleChoiceFrage frage;

  @BeforeEach
  public void setUp() {
    frage = new MultipleChoiceFrage(Long.valueOf(1), "Die Vorlesung ist strukturiert", false);
    frage.addChoice(new Auswahl(MOEGLICHKEIT_1));
    frage.addChoice(new Auswahl(MOEGLICHKEIT_2));
    frage.addChoice(new Auswahl(MOEGLICHKEIT_3));
    frage.addChoice(new Auswahl(MOEGLICHKEIT_4));
  }

  @Test
  public void eineAntwortBringtHundertProzent() {
    frage.addAntwort(MOEGLICHKEIT_1);

    Double antwort1 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 100, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  public void zweiGleicheAntwortenBringtHundertProzent() {
    frage.addAntwort(MOEGLICHKEIT_1);
    frage.addAntwort(MOEGLICHKEIT_1);

    Double antwort1 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 100, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  public void zweiVerschiedeneAntwortenBringtJeweilsFuenfzigProzent() {
    frage.addAntwort(MOEGLICHKEIT_1);
    frage.addAntwort(MOEGLICHKEIT_2);

    Double antwort1 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 50, 0.03);
    assertEquals(antwort2.doubleValue(), 50, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  public void keineAntwortenBringenUeberallNullProzent() {
    Double antwort1 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 0, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 0, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }

  @Test
  public void verhaeltnisDreiZuEins() {
    frage.addAntwort(MOEGLICHKEIT_1);
    frage.addAntwort(MOEGLICHKEIT_3);
    frage.addAntwort(MOEGLICHKEIT_3);
    frage.addAntwort(MOEGLICHKEIT_3);

    Double antwort1 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_1));
    Double antwort2 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_2));
    Double antwort3 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_3));
    Double antwort4 = frage.holeErgebnis(new Auswahl(MOEGLICHKEIT_4));

    assertEquals(antwort1.doubleValue(), 25, 0.03);
    assertEquals(antwort2.doubleValue(), 0, 0.03);
    assertEquals(antwort3.doubleValue(), 75, 0.03);
    assertEquals(antwort4.doubleValue(), 0, 0.03);
  }
}
