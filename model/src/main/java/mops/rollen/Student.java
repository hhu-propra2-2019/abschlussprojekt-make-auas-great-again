package mops.rollen;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(of = "uuid")
public class Student {

  private UUID uuid;
  private String vorname;
  private String nachname;
}
