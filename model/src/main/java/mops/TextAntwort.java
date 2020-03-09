package mops;

import lombok.Getter;

@Getter
public class TextAntwort extends Antwort {

  String antworttext;

  public TextAntwort(Long id,String antworttext) {
    super(id);
    this.antworttext = antworttext;
  }
}
