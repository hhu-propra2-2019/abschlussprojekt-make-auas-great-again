package mops.rollen;

import lombok.Value;

@Value
public class Student {

  private Long id;
  private String vorname;
  private String nachname;
  private String email;
  private Integer matnr;
}
