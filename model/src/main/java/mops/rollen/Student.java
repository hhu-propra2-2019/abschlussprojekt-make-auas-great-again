package mops.rollen;

import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import mops.Fragebogen;

@Builder
@EqualsAndHashCode(of = "id")
public class Student {

  private Long id;
  private String vorname;
  private String nachname;
  private String email;
  private Integer matnr;

  private Set<Fragebogen> frageboegen;

  public void addFragebogen(Fragebogen fragebogen) {
    frageboegen.add(fragebogen);
  }
}
