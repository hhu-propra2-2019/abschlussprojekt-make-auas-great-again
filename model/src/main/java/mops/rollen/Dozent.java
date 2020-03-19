package mops.rollen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "username")
@AllArgsConstructor
public class Dozent {
  private String username;
  private String vorname;
  private String nachname;
  
  public Dozent(String username) {
    this.username = username;
    vorname = "jens";
    nachname = "B";
  }
}
