package mops;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;
import org.junit.jupiter.api.Test;

@SuppressWarnings( {"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage"})
public class TypeCheckerTest {

  @Test
  public void isTextFrageTest() {
    Frage textFrage = new TextFrage(Long.valueOf(1), "Wie geht's?");
    assertTrue(TypeChecker.isTextFrage(textFrage));
  }

  @Test
  public void isMultipleChoiceTest() {
    Frage frage = new MultipleChoiceFrage(Long.valueOf(1), "Wie geht's?");
    assertTrue(TypeChecker.isMultipleChoice(frage));
  }

  @Test
  public void isVorlesungTest() {
    Fragebogen mockBogen = mock(Fragebogen.class);
    when(mockBogen.getType()).thenReturn(Einheit.VORLESUNG);
    assertTrue(TypeChecker.isVorlesung(mockBogen));
  }

  @Test
  public void isUebungTest() {
    Fragebogen mockBogen = mock(Fragebogen.class);
    when(mockBogen.getType()).thenReturn(Einheit.UEBUNG);
    assertTrue(TypeChecker.isUebung(mockBogen));
  }
}
