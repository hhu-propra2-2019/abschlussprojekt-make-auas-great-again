package mops.antworten;

import mops.fragen.Auswahl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultipleChoiceAntwortTest {

  @Test
  void testToStringEmptyAntwort() {
    MultipleChoiceAntwort antwort = new MultipleChoiceAntwort(1L);
    Assertions.assertEquals(antwort.toString(), "");
  }

  @Test
  void testToString() {
    MultipleChoiceAntwort antwort = new MultipleChoiceAntwort(1L);
    antwort.addAntwort(new Auswahl("1"));
    antwort.addAntwort(new Auswahl("3"));
    antwort.addAntwort(new Auswahl("5"));
    Assertions.assertEquals(antwort.toString(), "1,3,5");
  }
}