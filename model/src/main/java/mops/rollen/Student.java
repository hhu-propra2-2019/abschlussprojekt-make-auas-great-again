package mops.rollen;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "uuid")
public class Student {
  UUID uuid;
}
