package mops.antworten;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultipleResponseAntwortTest {

  @Test
  void testToStringEmptyAntwort() {
    MultipleResponseAntwort antwort = new MultipleResponseAntwort(1L);
    Assertions.assertEquals(antwort.toString(), "");
  }

  @Test
  void testToString() {
    MultipleResponseAntwort antwort = new MultipleResponseAntwort(1L);
    antwort.addAntwort(1);
    antwort.addAntwort(3);
    antwort.addAntwort(5);
    Assertions.assertEquals(antwort.toString(), "1,3,5");
  }
}