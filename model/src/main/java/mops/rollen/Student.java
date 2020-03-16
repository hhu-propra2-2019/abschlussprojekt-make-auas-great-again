package mops.rollen;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.Fragebogen;

@Builder
@Getter
@EqualsAndHashCode(of = "matnr")
public class Student {

  private String vorname;
  private String nachname;
  private String email;
  private Integer matnr;

  private List<Fragebogen> frageboegen;

  public void addFragebogen(Fragebogen fragebogen) {
    frageboegen.add(fragebogen);
  }

  public List<Fragebogen> getAllContaining(String search) {
    return frageboegen.stream().filter(bogen -> bogen.contains(search)).collect(Collectors.toList());
  }
}
