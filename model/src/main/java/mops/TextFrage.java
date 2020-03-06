package mops;

import lombok.Value;

@Value
public class TextFrage implements Frage {
  String titel;
  String antwort;

  public TextFrage(String titel) {
    this.titel = titel;
    antwort = "";
  }

}
