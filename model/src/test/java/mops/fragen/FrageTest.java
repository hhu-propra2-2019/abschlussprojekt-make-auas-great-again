package mops.fragen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FrageTest {

  /***
   All the classes MultipleChoiceFrage,MultipleResponseFrage,SingleResponseFrage,TextFrage
   Should be tested seperately since Frage is an abstract class.
   ***/

  @Test
  public void aendereOeffentlichkeitsStatusMultipleChoiceFrageTest() {
    Frage multipleChoiceFrage = new MultipleChoiceFrage("x");
    boolean before = multipleChoiceFrage.isOeffentlich();
    multipleChoiceFrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, multipleChoiceFrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusMultipleResponseFrageTest() {
    Frage MultipleResponseFrage = new MultipleResponseFrage(0L, "x");
    boolean before = MultipleResponseFrage.isOeffentlich();
    MultipleResponseFrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, MultipleResponseFrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusSingleResponseFrageTest() {
    Frage SingleResponseFrage = new SingleResponseFrage(1L, "y", false);
    boolean before = SingleResponseFrage.isOeffentlich();
    SingleResponseFrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, SingleResponseFrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusTextFrageTest() {
    Frage TextFrage = new TextFrage(0L, "x");
    boolean before = TextFrage.isOeffentlich();
    TextFrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, TextFrage.isOeffentlich());
  }

}
