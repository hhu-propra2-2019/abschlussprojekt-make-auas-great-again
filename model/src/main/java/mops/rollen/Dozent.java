package mops.rollen;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mops.Veranstaltung;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
public class Dozent {

  private UUID uuid;
  private String vorname;
  private String nachname;
  private List<Veranstaltung> veranstaltungen;
}
