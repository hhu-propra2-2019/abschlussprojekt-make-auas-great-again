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
    multiplechoicefrage.aendereOeffentlichkeitsStatus();
    Assertions.assertTrue(multiplechoicefrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusMultipleResponseFrageTest() {
    Frage multipleresponsefrage = new MultipleResponseFrage(0L, "x");
    multipleresponsefrage.aendereOeffentlichkeitsStatus();
    Assertions.assertTrue(multipleresponsefrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusSingleResponseFrageTest() {
    Frage singleresponsefrage = new SingleResponseFrage(1L, "y", false);
    singleresponsefrage.aendereOeffentlichkeitsStatus();
    Assertions.assertTrue(singleresponsefrage.isOeffentlich());
  }

  @Test
  public void aendereOeffentlichkeitsStatusTextFrageTest() {
    Frage textfrage = new TextFrage(0L, "x");
    textfrage.aendereOeffentlichkeitsStatus();
    Assertions.assertTrue(textfrage.isOeffentlich());
  }

}
