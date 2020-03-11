package mops;

import lombok.Value;

@Value
public class Dozent {

  private Long id;
  private String vorname;
  private String nachname;
  private String email;
}
