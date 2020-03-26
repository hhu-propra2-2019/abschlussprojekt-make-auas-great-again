package mops.rollen;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mops.FragebogenTemplate;

@Getter
@Setter
@EqualsAndHashCode(of = "username")
@AllArgsConstructor
public class Dozent {
  private String username;
  private String vorname;
  private String nachname;
  private List<FragebogenTemplate> templates = new ArrayList<>();

  public Dozent(String username) {
    this.username = username;
    vorname = "jens";
    nachname = "B";
  }

  public void addTemplate(FragebogenTemplate template) {
    templates.add(template);
  }


  @Override
  public String toString() {
    return vorname + " " + nachname;
  }

  public FragebogenTemplate getTemplateById(Long id) {
    return templates.stream().filter(x -> x.getId().equals(id)).findFirst().get();
  }

  public void deleteTemplateById(Long id) {
    templates.remove(getTemplateById(id));
  }
}
