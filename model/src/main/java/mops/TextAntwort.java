package mops;

import lombok.Value;

@Value
public class TextAntwort extends Antwort {

  String antworttext;

  public TextAntwort(Long id,String antworttext) {
    super(id);
    this.antworttext = antworttext;
  }
}
