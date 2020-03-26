package mops.fragen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class FrageTest {

  /**
   * All the classes
   * MultipleChoiceFrage
   * MultipleResponseFrage
   * SingleResponseFrage
   * TextFrage
   * Should be tested seperately since Frage is an abstract class.
   */

  @Test
  public void aendereOeffentlichkeitsStatusMultipleChoiceFrageTest() {
    Frage multiplechoicefrage = new MultipleChoiceFrage("x");
    boolean before = multiplechoicefrage.isOeffentlich();
    multiplechoicefrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, multiplechoicefrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusMultipleResponseFrageTest() {
    Frage multipleresponsefrage = new MultipleResponseFrage(0L, "x");
    boolean before = multipleresponsefrage.isOeffentlich();
    multipleresponsefrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, multipleresponsefrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusSingleResponseFrageTest() {
    Frage singleresponsefrage = new SingleResponseFrage(1L, "y", false);
    boolean before = singleresponsefrage.isOeffentlich();
    singleresponsefrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, singleresponsefrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusTextFrageTest() {
    Frage textfrage = new TextFrage(0L, "x");
    boolean before = textfrage.isOeffentlich();
    textfrage.aendereOeffentlichkeitsStatus();
    Assertions.assertNotEquals(before, textfrage.isOeffentlich());
  }

}
