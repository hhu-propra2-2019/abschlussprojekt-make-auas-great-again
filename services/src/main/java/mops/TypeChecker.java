package mops;

import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;

public class TypeChecker {

  public static boolean isTextFrage(Frage frage) {
    return frage instanceof TextFrage;
  }

  public static boolean isMultipleChoice(Frage frage) {
    return frage instanceof MultipleChoiceFrage;
  }

  public static boolean isMultipleResponse(Frage frage) {
    return frage instanceof MultipleResponseFrage;
  }

  public static boolean isMultipleResponseOrMultipleChoice(Frage frage) {
    return TypeChecker.isMultipleChoice(frage) || TypeChecker.isMultipleResponse(frage);
  }

  public static boolean isSingleResponse(Frage frage) {
    return frage instanceof SingleResponseFrage;
  }

  public static boolean isVorlesung(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.VORLESUNG;
  }

  public static boolean isUebung(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.UEBUNG;
  }

  public static boolean isAufgabe(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.AUFGABE;
  }

  public static boolean isPraktikum(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.PRAKTIKUM;
  }

  public static boolean isGruppe(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.GRUPPE;
  }

  public static boolean isDozent(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.DOZENT;
  }

  public static boolean isBeratung(Fragebogen fragebogen) {
    return fragebogen.getType() == Einheit.BERATUNG;
  }
}
