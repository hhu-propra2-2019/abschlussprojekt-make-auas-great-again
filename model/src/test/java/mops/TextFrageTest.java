package mops;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mops.fragen.TextFrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TextFrageTest {
  private transient TextFrage textFrage;
  private static final String ANTWORT_1 = "Vorlesung ist echt nice. Mehr Memes wären gut!";
  private static final String ANTWORT_2 = "Bitte mehr auf die Übungsaufgaben eingehen!";

  @BeforeEach
  public void setUp() {
    textFrage = new TextFrage(Long.valueOf(1), "Weiteres Feedback:");
  }
}
