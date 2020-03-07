package mops;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TypeChecker {


  public static boolean isTextFrage(Frage frage) {
    return frage instanceof TextFrage;
  }

  public static boolean isMultipleChoice(Frage frage) {
    return frage instanceof MultipleChoiceFrage;
  }
}
