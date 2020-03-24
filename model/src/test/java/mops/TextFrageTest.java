package mops;


import static org.junit.jupiter.api.Assertions.assertEquals;

import mops.fragen.TextFrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage",
    "PMD.JUnitTestContainsTooManyAsserts"})
public class TextFrageTest {
  private static final String ANTWORT_1 = "Vorlesung ist echt nice. Mehr Memes wären gut!";
  private static final String ANTWORT_2 = "Bitte mehr auf die Übungsaufgaben eingehen!";
  private transient TextFrage textFrage;

  @BeforeEach
  public void setUp() {
    textFrage = new TextFrage(Long.valueOf(1), "Weiteres Feedback:");
  }

  @Test
  @DisplayName("stimmt die anzahl der Antworten")
  public void richtigeantwortanzahl() {
    textFrage.addAntwort(ANTWORT_1);
    textFrage.addAntwort(ANTWORT_2);
    assertEquals(2, textFrage.getAntworten().size());
  }

}

