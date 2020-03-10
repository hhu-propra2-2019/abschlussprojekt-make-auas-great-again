package mops;


public class TypeChecker {

  public static boolean isTextFrage(Frage frage) {
    return frage instanceof TextFrage;
  }

  public static boolean isSkalarFrage(Frage frage) {
    return frage instanceof SkalarFrage;
  }

  public static boolean isMultipleChoice(Frage frage) {
    return frage instanceof MultipleChoiceFrage;
  }

  public static boolean isVorlesung(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.VORLESUNG;
  }

  public static boolean isUebung(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.UEBUNG;
  }
}
